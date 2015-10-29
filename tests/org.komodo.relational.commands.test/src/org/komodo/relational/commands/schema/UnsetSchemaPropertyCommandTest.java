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
package org.komodo.relational.commands.schema;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.komodo.relational.commands.AbstractCommandTest;
import org.komodo.relational.model.Schema;
import org.komodo.relational.workspace.WorkspaceManager;
import org.komodo.shell.api.CommandResult;
import org.komodo.utils.StringUtils;

/**
 * Test Class to test UnsetSchemaPropertyCommand
 *
 */
@SuppressWarnings( {"javadoc", "nls"} )
public class UnsetSchemaPropertyCommandTest extends AbstractCommandTest {

    @Test
    public void testUnsetProperty1() throws Exception {
        final String[] commands = { 
            "workspace",
            "create-schema testSchema",
            "cd testSchema",
            "set-property rendition \"CREATE FOREIGN TABLE G1 (e1 integer) OPTIONS (ANNOTATION 'test', CARDINALITY '12');\"",
            "unset-property rendition" };

        setup( commands );

        CommandResult result = execute();
        assertCommandResultOk(result);

        WorkspaceManager wkspMgr = WorkspaceManager.getInstance(_repo);
        Schema[] schemas = wkspMgr.findSchemas(getTransaction());

        assertEquals(1, schemas.length);
        assertEquals("testSchema", schemas[0].getName(getTransaction())); //$NON-NLS-1$

        String rendition = schemas[0].getRendition(getTransaction());
        assertEquals(true, StringUtils.isEmpty(rendition));
    }

}