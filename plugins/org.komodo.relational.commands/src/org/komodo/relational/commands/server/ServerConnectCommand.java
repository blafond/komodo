/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.server;

import static org.komodo.relational.commands.server.ServerCommandMessages.Common.NoTeiidDefined;
import static org.komodo.relational.commands.server.ServerCommandMessages.ServerConnectCommand.AttemptingToConnect;
import static org.komodo.relational.commands.server.ServerCommandMessages.ServerConnectCommand.Connected;
import static org.komodo.relational.commands.server.ServerCommandMessages.ServerConnectCommand.ConnectionError;
import static org.komodo.relational.commands.server.ServerCommandMessages.ServerConnectCommand.NotConnected;
import static org.komodo.relational.commands.server.ServerCommandMessages.ServerConnectCommand.TeiidStatus;
import org.komodo.relational.teiid.Teiid;
import org.komodo.shell.CommandResultImpl;
import org.komodo.shell.CompletionConstants;
import org.komodo.shell.Messages;
import org.komodo.shell.api.CommandResult;
import org.komodo.shell.api.WorkspaceStatus;
import org.komodo.spi.runtime.TeiidInstance;

/**
 * A shell command to connect to the default server
 */
public final class ServerConnectCommand extends ServerShellCommand {

    static final String NAME = "server-connect"; //$NON-NLS-1$

    /**
     * @param status
     *        the shell's workspace status (cannot be <code>null</code>)
     */
    public ServerConnectCommand( final WorkspaceStatus status ) {
        super( NAME, status );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#doExecute()
     */
    @Override
    protected CommandResult doExecute() {
        if ( !hasWorkspaceServer() ) {
            return new CommandResultImpl( false, getMessage( NoTeiidDefined ), null );
        }

        CommandResult result = null;

        try {
            Teiid teiid = getWorkspaceServer();
            print( CompletionConstants.MESSAGE_INDENT, getMessage( AttemptingToConnect, teiid.getName( getTransaction() ) ) );

            TeiidInstance teiidInstance = teiid.getTeiidInstance( getTransaction() );

            try {
                teiidInstance.connect();
                boolean connected = teiidInstance.isConnected();
                String connectStatus = connected ? getMessage( Connected ) : Messages.getString( NotConnected );

                // Updates available commands upon connecting
                getWorkspaceStatus().updateAvailableCommands();
                
                result = new CommandResultImpl( getMessage( TeiidStatus, teiid.getName( getTransaction() ), connectStatus ) );
            } catch ( Exception ex ) {
                result = new CommandResultImpl( false, getMessage( ConnectionError, ex.getLocalizedMessage() ), ex );
            }
        } catch ( final Exception e ) {
            result = new CommandResultImpl( false, getMessage( ConnectionError, e.getLocalizedMessage() ), e );
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#getMaxArgCount()
     */
    @Override
    protected int getMaxArgCount() {
        return 0;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommand#isValidForCurrentContext()
     */
    @Override
    public final boolean isValidForCurrentContext() {
        return hasWorkspaceServer();
    }

}
