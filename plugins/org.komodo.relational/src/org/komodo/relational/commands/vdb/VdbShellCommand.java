/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.vdb;

import org.komodo.relational.Messages;
import org.komodo.relational.commands.RelationalShellCommand;
import org.komodo.relational.vdb.Vdb;
import org.komodo.relational.vdb.internal.VdbImpl;
import org.komodo.shell.api.WorkspaceStatus;

/**
 * A base class for @{link {@link Vdb VDB}-related shell commands.
 */
abstract class VdbShellCommand extends RelationalShellCommand {

    protected VdbShellCommand( final String name,
                               final boolean shouldCommit,
                               final WorkspaceStatus status ) {
        super( name, shouldCommit, status );
    }

    protected Vdb getVdb() throws Exception {
        return new VdbImpl( getTransaction(), getRepository(), getPath() );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommand#isValidForCurrentContext()
     */
    @Override
    public final boolean isValidForCurrentContext() {
        return isCurrentTypeValid( Vdb.TYPE_ID );
    }

    /**
     * @see org.komodo.shell.api.ShellCommand#printHelp(int indent)
     */
    @Override
    public void printHelp( final int indent ) {
        print( indent, Messages.getString( VdbCommandMessages.RESOURCE_BUNDLE, getClass().getSimpleName() + ".help" ) ); //$NON-NLS-1$
    }

    /**
     * @see org.komodo.shell.api.ShellCommand#printUsage(int indent)
     */
    @Override
    public void printUsage( final int indent ) {
        print( indent, Messages.getString( VdbCommandMessages.RESOURCE_BUNDLE, getClass().getSimpleName() + ".usage" ) ); //$NON-NLS-1$
    }

}
