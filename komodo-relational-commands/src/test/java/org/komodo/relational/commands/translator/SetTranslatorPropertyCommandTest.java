/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.komodo.relational.commands.translator;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Test;
import org.komodo.relational.commands.AbstractCommandTest;
import org.komodo.relational.vdb.Translator;
import org.komodo.relational.vdb.Vdb;
import org.komodo.relational.workspace.WorkspaceManager;
import org.komodo.shell.api.CommandResult;

/**
 * Test Class to test {@link SetTranslatorPropertyCommand}.
 */
@SuppressWarnings( {"javadoc", "nls"} )
public final class SetTranslatorPropertyCommandTest extends AbstractCommandTest {

    @Test
    public void testSetProperty1() throws Exception {
        final String[] commands = {
            "create-vdb myVdb vdbPath",
            "cd myVdb",
            "add-translator myTranslator tType",
            "cd myTranslator",
            "set-property description myDescription" };
        final CommandResult result = execute( commands );
        assertCommandResultOk(result);

        WorkspaceManager wkspMgr = WorkspaceManager.getInstance(_repo, getTransaction());
        Vdb[] vdbs = wkspMgr.findVdbs(getTransaction());

        assertEquals(1, vdbs.length);

        Translator[] translators = vdbs[0].getTranslators(getTransaction());
        assertEquals(1, translators.length);
        assertEquals("myTranslator", translators[0].getName(getTransaction())); //$NON-NLS-1$

        assertEquals("myDescription", translators[0].getDescription(getTransaction())); //$NON-NLS-1$
    }

    @Test
    public void testTabCompleter()throws Exception{
    	ArrayList<CharSequence> candidates=new ArrayList<>();
    	setup("commandFiles","addTranslators.cmd");
    	final String[] commands = { "cd myTranslator1" };
    	final CommandResult result = execute( commands );
        assertCommandResultOk(result);

    	candidates.add(TranslatorShellCommand.TYPE);

    	assertTabCompletion("set-property typ", candidates);
    	assertTabCompletion("set-property TY", candidates);
    }
}
