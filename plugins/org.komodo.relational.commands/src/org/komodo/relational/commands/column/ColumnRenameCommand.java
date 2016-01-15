/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.column;

import java.util.List;
import org.komodo.relational.model.Column;
import org.komodo.shell.api.TabCompletionModifier;
import org.komodo.shell.api.WorkspaceStatus;
import org.komodo.shell.commands.RenameCommand;

/**
 * Since {@link Column column} has no children, this command overrides the default {@link RenameCommand} to not allow children to
 * be renamed.
 */
public final class ColumnRenameCommand extends RenameCommand {

    /**
     * @param status
     *        the shell's workspace status (cannot be <code>null</code>)
     */
    public ColumnRenameCommand( final WorkspaceStatus status ) {
        super( status );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.commands.RenameCommand#getMaxArgCount()
     */
    @Override
    protected int getMaxArgCount() {
        return 1;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommand#isValidForCurrentContext()
     */
    @Override
    public boolean isValidForCurrentContext() {
        try {
            return Column.RESOLVER.resolvable( getTransaction(), getContext() );
        } catch ( final Exception e ) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.commands.RenameCommand#tabCompletion(java.lang.String, java.util.List)
     */
    @Override
    public TabCompletionModifier tabCompletion( String lastArgument,
                                                List< CharSequence > candidates ) throws Exception {
        return TabCompletionModifier.NO_AUTOCOMPLETION;
    }

}
