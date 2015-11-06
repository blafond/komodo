/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.workspace;

import java.util.ResourceBundle;
import org.komodo.spi.constants.StringConstants;

/**
 * Localized messages for the WorkspaceCommandMessages
 */
@SuppressWarnings( "javadoc" )
public class WorkspaceCommandMessages implements StringConstants {

    private static final String BUNDLE_NAME = ( WorkspaceCommandMessages.class.getPackage().getName() + DOT + WorkspaceCommandMessages.class.getSimpleName().toLowerCase() );

    /**
     * The resource bundle for localized messages.
     */
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( BUNDLE_NAME );

    public enum General {
        ERROR_DDL_EMPTY,
        ERROR_WRITING_FILE,
        INVALID_BOOLEAN_PROPERTY_VALUE,
        INVALID_INTEGER_PROPERTY_VALUE,
        INVALID_NULLABLE_PROPERTY_VALUE,
        INVALID_OBJECT_TYPE,
        INVALID_PROPERTY_NAME,
        MISSING_OUTPUT_FILE_NAME,
        MISSING_INPUT_FILE_NAME,
        MISSING_PROPERTY_NAME_VALUE,
        MISSING_VDB_NAME,
        MISSING_TEIID_NAME,
        MISSING_SCHEMA_NAME,
        OUTPUT_FILE_ERROR,
        INPUT_FILE_ERROR,
        SET_PROPERTY_SUCCESS,
        UNSET_MISSING_PROPERTY_NAME,
        UNSET_PROPERTY_SUCCESS;

        @Override
        public String toString() {
            return getEnumName(this) + DOT + name();
        }
    }

    public enum CreateVdbCommand {
        VDB_CREATED;

        @Override
        public String toString() {
            return getEnumName(this) + DOT + name();
        }
    }

    public enum ImportVdbCommand {
        VdbImportInProgressMsg,
        VdbImportSuccessMsg,
        ImportFailedMsg,
        DeleteTempVdbFailedMsg,
        cannotImport_wouldCreateDuplicate;

        @Override
        public String toString() {
            return getEnumName(this) + DOT + name();
        }
    }

    public enum DeleteVdbCommand {
        DELETE_VDB_ERROR,
        VDB_NOT_FOUND,
        VDB_DELETED;

        @Override
        public String toString() {
            return getEnumName(this) + DOT + name();
        }
    }

    public enum CreateTeiidCommand {
        TEIID_CREATED;

        @Override
        public String toString() {
            return getEnumName(this) + DOT + name();
        }
    }

    public enum DeleteTeiidCommand {
        DELETE_TEIID_ERROR,
        TEIID_NOT_FOUND,
        TEIID_DELETED;

        @Override
        public String toString() {
            return getEnumName(this) + DOT + name();
        }
    }

    public enum CreateSchemaCommand {
        SCHEMA_CREATED;

        @Override
        public String toString() {
            return getEnumName(this) + DOT + name();
        }
    }

    public enum DeleteSchemaCommand {
        DELETE_SCHEMA_ERROR,
        SCHEMA_NOT_FOUND,
        SCHEMA_DELETED;

        @Override
        public String toString() {
            return getEnumName(this) + DOT + name();
        }
    }

    public enum UploadVdbCommand {

        MISSING_INPUT_VDB_FILE_PATH,
        MISSING_VDB_NAME,
        VDB_INPUT_FILE_IS_EMPTY,
        OVERWRITE_ARG_INVALID,
        VDB_OVERWRITE_DISABLED,
        VDB_UPLOADED;

        /**
         * {@inheritDoc}
         *
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return getEnumName( this ) + DOT + name();
        }

    }

    private static String getEnumName(Enum<?> enumValue) {
        String className = enumValue.getClass().getName();
        String[] components = className.split("\\$"); //$NON-NLS-1$
        return components[components.length - 1];
    }

}
