/*
* JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.komodo.rest.relational;

import java.net.URI;
import java.util.Properties;
import org.komodo.relational.vdb.Vdb;
import org.komodo.rest.KomodoService;
import org.komodo.rest.RestBasicEntity;
import org.komodo.rest.RestLink;
import org.komodo.rest.RestLink.LinkType;
import org.komodo.rest.relational.KomodoRestUriBuilder.SettingNames;
import org.komodo.spi.KException;
import org.komodo.spi.repository.Repository.UnitOfWork;
import org.modeshape.sequencer.teiid.lexicon.VdbLexicon;

/**
 * A VDB that can be used by GSON to build a JSON document representation.
 */
public final class RestVdb extends RestBasicEntity {

    /**
     * Label used to describe name
     */
    public static final String NAME_LABEL = KomodoService.encode(VdbLexicon.Vdb.NAME);

    /**
     * Label used to describe description
     */
    public static final String DESCRIPTION_LABEL = KomodoService.encode(VdbLexicon.Vdb.DESCRIPTION);

    /**
     * Label used to describe original file path
     */
    public static final String FILE_PATH_LABEL = KomodoService.encode(VdbLexicon.Vdb.ORIGINAL_FILE);

    /**
     * Label used to describe original file path
     */
    public static final String PREVIEW_LABEL = KomodoService.encode(VdbLexicon.Vdb.PREVIEW);

    /**
     * Label used to describe original file path
     */
    public static final String CONNECTION_TYPE_LABEL = KomodoService.encode(VdbLexicon.Vdb.CONNECTION_TYPE);

    /**
     * Label used to describe original file path
     */
    public static final String VERSION_LABEL = KomodoService.encode(VdbLexicon.Vdb.VERSION);

    /**
     * Constructor for use when deserializing
     */
    public RestVdb() {
        super();
    }

    /**
     * Constructor for use when serializing.
     * @param baseUri the base uri of the vdb
     * @param vdb the vdb
     * @param exportXml whether xml should be exported
     * @param uow the transaction
     *
     * @throws KException if error occurs
     */
    public RestVdb(URI baseUri, Vdb vdb, boolean exportXml, UnitOfWork uow) throws KException {
        super(baseUri, vdb, uow);

        setName(vdb.getName(uow));
        setDescription(vdb.getDescription(uow));
        setOriginalFilePath(vdb.getOriginalFilePath(uow));

        setPreview(vdb.isPreview(uow));
        setConnectionType(vdb.getConnectionType(uow));
        setVersion(vdb.getVersion(uow));

        addExecutionProperties(uow, vdb);

        if (exportXml) {
            String xml = vdb.export(uow, new Properties());
            setXml(xml);
        }

        Properties settings = getUriBuilder().createSettings(SettingNames.VDB_NAME, getId());

        addLink(new RestLink(LinkType.SELF, getUriBuilder().buildVdbUri(LinkType.SELF, settings)));
        addLink(new RestLink(LinkType.PARENT, getUriBuilder().buildVdbUri(LinkType.PARENT, settings)));
        addLink(new RestLink(LinkType.IMPORTS, getUriBuilder().buildVdbUri(LinkType.IMPORTS, settings)));
        addLink(new RestLink(LinkType.MODELS, getUriBuilder().buildVdbUri(LinkType.MODELS, settings)));
        addLink(new RestLink(LinkType.TRANSLATORS, getUriBuilder().buildVdbUri(LinkType.TRANSLATORS, settings)));
        addLink(new RestLink(LinkType.DATA_ROLES, getUriBuilder().buildVdbUri(LinkType.DATA_ROLES, settings)));
    }

    /**
     * @return the name
     */
    public String getName() {
        Object name = tuples.get(NAME_LABEL);
        return name != null ? name.toString() : null;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        tuples.put(NAME_LABEL, name);
    }

    /**
     * @return the VDB description (can be empty)
     */
    public String getDescription() {
        Object description = tuples.get(DESCRIPTION_LABEL);
        return description != null ? description.toString() : null;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        tuples.put(DESCRIPTION_LABEL, description);
    }

    /**
     * @return the originalFilePath
     */
    public String getOriginalFilePath() {
        Object path = tuples.get(FILE_PATH_LABEL);
        return path != null ? path.toString() : null;
    }

    /**
     * @param originalFilePath the originalFilePath to set
     */
    public void setOriginalFilePath(String originalFilePath) {
        tuples.put(FILE_PATH_LABEL, originalFilePath);
    }

    /**
     * @return the preview
     */
    public boolean isPreview() {
        Object preview = tuples.get(PREVIEW_LABEL);
        return preview != null ? Boolean.parseBoolean(preview.toString()) : null;
    }

    /**
     * @param preview the preview to set
     */
    public void setPreview(boolean preview) {
        tuples.put(PREVIEW_LABEL, preview);
    }

    /**
     * @return the connectionType
     */
    public String getConnectionType() {
        Object connectionType = tuples.get(CONNECTION_TYPE_LABEL);
        return connectionType != null ? connectionType.toString() : null;
    }

    /**
     * @param connectionType the connectionType to set
     */
    public void setConnectionType(String connectionType) {
        tuples.put(CONNECTION_TYPE_LABEL, connectionType);
    }

    /**
     * @return the version
     */
    public int getVersion() {
        Object version = tuples.get(VERSION_LABEL);
        return version != null ? Integer.parseInt(version.toString()) : null;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(int version) {
        tuples.put(VERSION_LABEL, version);
    }
}
