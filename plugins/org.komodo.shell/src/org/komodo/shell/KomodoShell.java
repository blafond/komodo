/*************************************************************************************
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership. Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 ************************************************************************************/
package org.komodo.shell;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Properties;
import org.komodo.shell.Messages.SHELL;
import org.komodo.shell.api.InvalidCommandArgumentException;
import org.komodo.shell.api.ShellCommand;
import org.komodo.shell.api.WorkspaceStatus;
import org.komodo.spi.constants.StringConstants;


/**
 * An interactive shell for working with komodo.
 * 
 * This class adapted from https://github.com/Governance/s-ramp/blob/master/s-ramp-shell
 * - altered to use WorkspaceStatus
 * - altered display message
 * - utilizing different Messages class
 * 
 */
public class KomodoShell {

    private static final String LOCALE_PROPERTY = "komodo.shell.locale"; //$NON-NLS-1$
    private static final String STARTUP_PROPERTIES_FILEPATH = "./komodoShell.properties"; //$NON-NLS-1$
    private String msgIndentStr = StringConstants.EMPTY_STRING;

	/**
	 * Main entry point.
	 * @param args the arguments
	 */
	public static void main(String [] args) {
	    String locale_str = System.getProperty(LOCALE_PROPERTY);
	    if (locale_str != null) {
	        String lang = null;
	        String region = null;
	        String [] lsplit = locale_str.split("_"); //$NON-NLS-1$
	        if (lsplit.length > 0) {
	            lang = lsplit[0];
	        }
	        if (lsplit.length > 1) {
	            region = lsplit[1];
	        }
	        if (lang != null && region != null) {
	            Locale.setDefault(new Locale(lang, region));
	        } else if (lang != null) {
                Locale.setDefault(new Locale(lang));
	        }
	    }

		final KomodoShell shell = new KomodoShell(System.in, System.out);
		Thread shutdownHook = new Thread(new Runnable() {
			@Override
			public void run() {
				shell.shutdown();
			}
		});
		Runtime.getRuntime().addShutdownHook(shutdownHook);
		try {
			shell.run(args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    private final WorkspaceStatus wsStatus;
    private final ShellCommandFactory factory;
    private ShellCommandReader reader;
    private boolean shutdown = false;

	/**
	 * Constructor.
	 * @param inStream
	 * @param outStream
	 */
	public KomodoShell(InputStream inStream, PrintStream outStream) {
	    wsStatus = new WorkspaceStatusImpl(inStream, outStream);
	    factory = new ShellCommandFactory(wsStatus);
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<CompletionConstants.MESSAGE_INDENT; i++) {
			sb.append(StringConstants.SPACE);
		}
		msgIndentStr = sb.toString();
	}

	/**
	 * Runs the shell.
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public void run(String[] args) throws Exception {
		File startupPropertiesFile = new File(STARTUP_PROPERTIES_FILEPATH);
		if(startupPropertiesFile.exists() && startupPropertiesFile.isFile() && startupPropertiesFile.canRead()) {
			Properties props = new Properties();

			try {
				props.load(new FileInputStream(startupPropertiesFile));
			} catch (Exception e) {
			}
			wsStatus.setProperties(props);
		}
		
        reader = ShellCommandReaderFactory.createCommandReader(args, factory, wsStatus);
        reader.open();
		displayWelcomeMessage();
		boolean done = false;
		while (!done && !shutdown) {
			ShellCommand command = null;
			try {
			    if (shutdown)
			        break;

			    command = reader.read();
				if (command == null) {
					done = true;
				} else {
					boolean success = command.execute();
					if (!success && reader.isBatch()) {
					    shutdown();
					}
				}
			} catch (InvalidCommandArgumentException e) {
			    wsStatus.getOutputStream().println(msgIndentStr+Messages.getString(SHELL.INVALID_ARG, e.getMessage())); 
				if (command != null) {
				    wsStatus.getOutputStream().println(msgIndentStr+Messages.getString(SHELL.USAGE)); 
    				command.printUsage(CompletionConstants.MESSAGE_INDENT);
				}
				if (reader.isBatch())
				    shutdown();
			} catch (Exception e) {
			    String msg = "Exception Occurred: " + (e.getLocalizedMessage() == null ? e.getClass().getSimpleName() : e.getLocalizedMessage()); //$NON-NLS-1$
			    wsStatus.getOutputStream().println(msgIndentStr + msg);
				if (reader.isBatch())
				    shutdown();
			}
		}
	}

	/**
	 * Shuts down the shell.
	 */
	public void shutdown() {
        wsStatus.getOutputStream().print(msgIndentStr + Messages.getString(SHELL.SHUTTING_DOWN));
        try {
            shutdown  = true;
            this.reader.close();
        } catch (IOException e) {
        }
        wsStatus.getOutputStream().println(msgIndentStr+Messages.getString(SHELL.DONE));
	}

	/**
	 * Displays a welcome message to the user.
	 */
	private void displayWelcomeMessage() {
	    wsStatus.getOutputStream().println(
				"**********************************************************************\n" + //$NON-NLS-1$
				"  Welcome to Komodo Shell\n" + //$NON-NLS-1$
//				"  Locale: " + Locale.getDefault().toString().trim() + "\n" + //$NON-NLS-1$ //$NON-NLS-2$
				"**********************************************************************" //$NON-NLS-1$
				);
	}
}
