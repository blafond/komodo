/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package org.komodo.relational.workspace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.komodo.core.KomodoLexicon;
import org.komodo.relational.Messages;
import org.komodo.relational.Messages.Relational;
import org.komodo.relational.RelationalProperties;
import org.komodo.relational.RelationalProperty;
import org.komodo.relational.internal.AdapterFactory;
import org.komodo.relational.internal.RelationalObjectImpl;
import org.komodo.relational.internal.TypeResolver;
import org.komodo.relational.internal.TypeResolverRegistry;
import org.komodo.relational.model.Model;
import org.komodo.relational.model.Schema;
import org.komodo.relational.model.internal.ModelImpl;
import org.komodo.relational.model.internal.SchemaImpl;
import org.komodo.relational.teiid.Teiid;
import org.komodo.relational.teiid.internal.TeiidImpl;
import org.komodo.relational.vdb.Vdb;
import org.komodo.relational.vdb.internal.VdbImpl;
import org.komodo.repository.LocalRepository;
import org.komodo.repository.RepositoryImpl;
import org.komodo.spi.KException;
import org.komodo.spi.constants.StringConstants;
import org.komodo.spi.repository.KomodoObject;
import org.komodo.spi.repository.KomodoType;
import org.komodo.spi.repository.Repository;
import org.komodo.spi.repository.Repository.Id;
import org.komodo.spi.repository.Repository.State;
import org.komodo.spi.repository.Repository.UnitOfWork;
import org.komodo.spi.repository.RepositoryObserver;
import org.komodo.spi.utils.KeyInValueHashMap;
import org.komodo.spi.utils.KeyInValueHashMap.KeyFromValueAdapter;
import org.komodo.utils.ArgCheck;
import org.modeshape.jcr.api.JcrConstants;
import org.modeshape.sequencer.teiid.lexicon.VdbLexicon;

/**
 *
 */
public class WorkspaceManager extends RelationalObjectImpl {

    private static final String FIND_QUERY_PATTERN = "SELECT [jcr:path] FROM [%s] WHERE ISDESCENDANTNODE('" //$NON-NLS-1$
                                                     + RepositoryImpl.WORKSPACE_ROOT + "') ORDER BY [jcr:name] ASC"; //$NON-NLS-1$

    private static class WskpMgrAdapter implements KeyFromValueAdapter< Repository.Id, WorkspaceManager > {

        @Override
        public Id getKey( WorkspaceManager value ) {
            Repository repository = value.getRepository();
            return repository.getId();
        }
    }

    private static KeyFromValueAdapter< Repository.Id, WorkspaceManager > adapter = new WskpMgrAdapter();

    private static KeyInValueHashMap< Repository.Id, WorkspaceManager > instances = new KeyInValueHashMap< Repository.Id, WorkspaceManager >(
                                                                                                                                             adapter);

    /**
     * @param uow the transaction
     * @param repository
     *        the repository
     * @return singleton instance for the given repository
     * @throws KException if there is an error obtaining the workspace manager
     */
    public static WorkspaceManager getInstance( UnitOfWork uow, Repository repository ) throws KException {
        WorkspaceManager instance = instances.get(repository.getId());
        
        //UnitOfWork uow = repository.createTransaction( "createWorkspaceManager", true, null ); //$NON-NLS-1$
        instance = new WorkspaceManager(uow, repository);
        //uow.commit();
        instances.add(instance);
        
        return instance;
    }

    @Override
    public KomodoType getTypeIdentifier(UnitOfWork uow) {
        return KomodoType.WORKSPACE;
    }

    /**
     * Primarily used in tests to remove the workspace manager instance
     * from the instances cache.
     *
     * @param repository remove instance with given repository
     */
    public static void uncacheInstance(LocalRepository repository) {
        if (repository == null)
            return;

        instances.remove(repository.getId());
    }

    private WorkspaceManager(UnitOfWork uow, Repository repository ) throws KException {
        super(uow, repository, RepositoryImpl.WORKSPACE_ROOT);  
        repository.komodoWorkspace(uow);
        repository.addObserver(new RepositoryObserver() {

            @Override
            public void eventOccurred() {
                // Disposal observer
                if (getRepository() == null || State.NOT_REACHABLE == getRepository().getState() || !(getRepository().ping())) {
                    instances.remove(WorkspaceManager.this);
                }
            }
        });
    }

    /**
     * @param uow
     *        the transaction (cannot be <code>null</code> or have a state that is not
     *        {@link org.komodo.spi.repository.Repository.UnitOfWork.State#NOT_STARTED})
     * @param parent
     *        the parent of the model object being created (can be <code>null</code> if creating at workspace root)
     * @param modelName
     *        the name of the model to create (cannot be empty)
     * @return the model object (never <code>null</code>)
     * @throws KException
     *         if an error occurs
     */
    public Model createModel( UnitOfWork uow,
                              KomodoObject parent,
                              String modelName ) throws KException {
        KomodoObject kobject = create(uow, parent, modelName, KomodoType.MODEL);
        if (! (kobject instanceof Model))
           return null;

        return (Model) kobject;
    }

    /**
     * @param uow
     *        the transaction (cannot be <code>null</code> or have a state that is not
     *        {@link org.komodo.spi.repository.Repository.UnitOfWork.State#NOT_STARTED})
     * @param parent
     *        the parent of the schema object being created (cannot be <code>null</code>)
     * @param schemaName
     *        the name of the schema to create (cannot be empty)
     * @return the schema object (never <code>null</code>)
     * @throws KException
     *         if an error occurs
     */
    public Schema createSchema( UnitOfWork uow,
                                KomodoObject parent,
                                String schemaName ) throws KException {
        if (parent == null)
            parent = getRepository().komodoWorkspace(uow);

        KomodoObject kobject = create(uow, parent, schemaName, KomodoType.SCHEMA);
        if (! (kobject instanceof Schema))
           return null;

        return (Schema) kobject;
    }

    /**
     * @param uow
     *        the transaction (cannot be <code>null</code> or have a state that is not
     *        {@link org.komodo.spi.repository.Repository.UnitOfWork.State#NOT_STARTED})
     * @param parent
     *        the parent of the teiid object being created (cannot be <code>null</code>)
     * @param id
     *        the id of the teiid instance (cannot be empty)
     * @return the teiid object (never <code>null</code>)
     * @throws KException
     *         if an error occurs
     */
    public Teiid createTeiid( UnitOfWork uow,
                              KomodoObject parent,
                              String id ) throws KException {
        if (parent == null)
            parent = getRepository().komodoWorkspace(uow);

        KomodoObject kobject = create(uow, parent, id, KomodoType.TEIID);
        if (! (kobject instanceof Teiid))
           return null;

        return (Teiid) kobject;
    }

    /**
     * @param uow
     *        the transaction (cannot be <code>null</code> or have a state that is not
     *        {@link org.komodo.spi.repository.Repository.UnitOfWork.State#NOT_STARTED})
     * @param parent
     *        the parent of the model object being created (can be <code>null</code> if VDB should be created at the workspace
     *        root)
     * @param vdbName
     *        the name of the VDB to create (cannot be empty)
     * @param externalFilePath
     *        the VDB file path on the local file system (cannot be empty)
     * @return the VDB (never <code>null</code>)
     * @throws KException
     *         if an error occurs
     */
    public Vdb createVdb( final UnitOfWork uow,
                          KomodoObject parent,
                          final String vdbName,
                          final String externalFilePath ) throws KException {
        ArgCheck.isNotNull(externalFilePath, "externalFilePath"); //$NON-NLS-1$

        RelationalProperty filePathProperty = new RelationalProperty( VdbLexicon.Vdb.ORIGINAL_FILE, externalFilePath );

        if (parent == null)
            parent = getRepository().komodoWorkspace(uow);

        KomodoObject kobject = create(uow, parent, vdbName, KomodoType.VDB, filePathProperty);
        if (! (kobject instanceof Vdb))
           return null;

        return (Vdb) kobject;
    }

    /**
     * @param transaction
     *        the transaction (cannot be <code>null</code> or have a state that is not
     *        {@link org.komodo.spi.repository.Repository.UnitOfWork.State#NOT_STARTED})
     * @param parent
     *        the parent of the new object (cannot be <code>null</code>)
     * @param id
     *        the identifier of the object (cannot be <code>null</code>)
     * @param type
     *        the type of the object (cannot be <code>null</code>)
     * @param properties
     *        any additional properties required for construction
     *
     * @return new object
     * @throws KException if an error occurs
     */
    public KomodoObject create( UnitOfWork transaction,
                                KomodoObject parent,
                                String id,
                                KomodoType type,
                                RelationalProperty... properties ) throws KException {

        ArgCheck.isNotNull( transaction, "transaction" ); //$NON-NLS-1$
        ArgCheck.isTrue( ( transaction.getState() == org.komodo.spi.repository.Repository.UnitOfWork.State.NOT_STARTED ),
                         "transaction state is not NOT_STARTED" ); //$NON-NLS-1$
        ArgCheck.isNotEmpty( id, "id" ); //$NON-NLS-1$
        ArgCheck.isNotNull( type );

        RelationalProperties relProperties = new RelationalProperties();
        if ( ( properties != null ) && ( properties.length != 0 ) ) {
            for ( RelationalProperty property : properties ) {
                relProperties.add( property );
            }
        }

        TypeResolverRegistry registry = TypeResolverRegistry.getInstance();
        TypeResolver< ? > resolver = registry.getResolver( type );
        if ( resolver == null ) {
            if ( parent == null ) {
                return getRepository().komodoWorkspace( transaction ).addChild( transaction, id, JcrConstants.NT_UNSTRUCTURED );
            }

            return parent.addChild( transaction, id, JcrConstants.NT_UNSTRUCTURED );
        }

        return resolver.create( transaction, getRepository(), parent, id, relProperties );
    }

    /**
     * @param transaction
     *        the transaction (cannot be <code>null</code> or have a state that is not
     *        {@link org.komodo.spi.repository.Repository.UnitOfWork.State#NOT_STARTED})
     * @param kobjects
     *        the object(s) being deleted (cannot be <code>null</code>, empty, or have a <code>null</code> element)
     * @throws KException
     *         if an error occurs or if an object does not exist
     */
    public void delete( final UnitOfWork transaction,
                        final KomodoObject... kobjects ) throws KException {
        ArgCheck.isNotNull( transaction, "transaction" ); //$NON-NLS-1$
        ArgCheck.isTrue( ( transaction.getState() == org.komodo.spi.repository.Repository.UnitOfWork.State.NOT_STARTED ),
                         "transaction state is not NOT_STARTED" ); //$NON-NLS-1$
        ArgCheck.isNotEmpty( kobjects, "kobjects" ); //$NON-NLS-1$

        for ( final KomodoObject kobject : kobjects ) {
            ArgCheck.isNotNull( kobject, "kobject" ); //$NON-NLS-1$
            validateWorkspaceMember( transaction, kobject );
            kobject.remove( transaction );
        }
    }

    private String[] findByType( final UnitOfWork transaction,
                                 final String type ) throws KException {
        String[] result = null;

        try {
            final String queryText = String.format(FIND_QUERY_PATTERN, type);
            List<KomodoObject> results = getRepository().query(transaction, queryText);
            int numPaths = results.size();

            if (numPaths == 0) {
                result = StringConstants.EMPTY_ARRAY;
            } else {
                result = new String[numPaths];
                int i = 0;

                for (KomodoObject kObject : results) {
                    result[i++] = kObject.getAbsolutePath();
                }
            }

            return result;
        } catch (final Exception e) {
            throw handleError( e );
        }
    }

    /**
     * @param transaction
     *        the transaction (cannot be <code>null</code> or have a state that is not
     *        {@link org.komodo.spi.repository.Repository.UnitOfWork.State#NOT_STARTED})
     * @return all {@link Model}s in the workspace (never <code>null</code> but can be empty)
     * @throws KException
     *         if an error occurs
     */
    public Model[] findModels( final UnitOfWork transaction ) throws KException {
        ArgCheck.isNotNull( transaction, "transaction" ); //$NON-NLS-1$
        ArgCheck.isTrue( ( transaction.getState() == org.komodo.spi.repository.Repository.UnitOfWork.State.NOT_STARTED ),
                         "transaction state is not NOT_STARTED" ); //$NON-NLS-1$

        final String[] paths = findByType(transaction, VdbLexicon.Vdb.DECLARATIVE_MODEL);
        Model[] result = null;

        if (paths.length == 0) {
            result = Model.NO_MODELS;
        } else {
            result = new Model[paths.length];
            int i = 0;

            for (final String path : paths) {
                result[i++] = new ModelImpl(transaction, getRepository(), path);
            }
        }

        return result;
    }

    /**
     * @param transaction
     *        the transaction (cannot be <code>null</code> or have a state that is not
     *        {@link org.komodo.spi.repository.Repository.UnitOfWork.State#NOT_STARTED})
     * @return all {@link Model}s in the workspace (never <code>null</code> but can be empty)
     * @throws KException
     *         if an error occurs
     */
    public Schema[] findSchemas( final UnitOfWork transaction ) throws KException {
        ArgCheck.isNotNull( transaction, "transaction" ); //$NON-NLS-1$
        ArgCheck.isTrue( ( transaction.getState() == org.komodo.spi.repository.Repository.UnitOfWork.State.NOT_STARTED ),
                         "transaction state is not NOT_STARTED" ); //$NON-NLS-1$

        final String[] paths = findByType(transaction, KomodoLexicon.Schema.NODE_TYPE);
        Schema[] result = null;

        if (paths.length == 0) {
            result = Schema.NO_SCHEMAS;
        } else {
            result = new Schema[paths.length];
            int i = 0;

            for (final String path : paths) {
                result[i++] = new SchemaImpl(transaction, getRepository(), path);
            }
        }

        return result;
    }

    /**
     * @param transaction
     *        the transaction (cannot be <code>null</code> or have a state that is not
     *        {@link org.komodo.spi.repository.Repository.UnitOfWork.State#NOT_STARTED})
     * @return all {@link Teiid}s in the workspace
     * @throws KException
     *         if an error occurs
     */
    public List< Teiid > findTeiids( UnitOfWork transaction ) throws KException {
        ArgCheck.isNotNull( transaction, "transaction" ); //$NON-NLS-1$
        ArgCheck.isTrue( ( transaction.getState() == org.komodo.spi.repository.Repository.UnitOfWork.State.NOT_STARTED ),
                         "transaction state is not NOT_STARTED" ); //$NON-NLS-1$

        final String[] paths = findByType(transaction, KomodoLexicon.Teiid.NODE_TYPE);
        List< Teiid > result = null;

        if (paths.length == 0) {
            result = Collections.emptyList();
        } else {
            result = new ArrayList<>(paths.length);

            for (final String path : paths) {
                result.add(new TeiidImpl(transaction, getRepository(), path));
            }
        }

        return result;
    }

    /**
     * @param transaction
     *        the transaction (cannot be <code>null</code> or have a state that is not
     *        {@link org.komodo.spi.repository.Repository.UnitOfWork.State#NOT_STARTED})
     * @return all {@link Vdb}s in the workspace (never <code>null</code> but can be empty)
     * @throws KException
     *         if an error occurs
     */
    public Vdb[] findVdbs( final UnitOfWork transaction ) throws KException {
        ArgCheck.isNotNull( transaction, "transaction" ); //$NON-NLS-1$
        ArgCheck.isTrue( ( transaction.getState() == org.komodo.spi.repository.Repository.UnitOfWork.State.NOT_STARTED ),
                         "transaction state is not NOT_STARTED" ); //$NON-NLS-1$

        final String[] paths = findByType(transaction, VdbLexicon.Vdb.VIRTUAL_DATABASE);
        Vdb[] result = null;

        if (paths.length == 0) {
            result = Vdb.NO_VDBS;
        } else {
            result = new Vdb[paths.length];
            int i = 0;

            for (final String path : paths) {
                result[i++] = new VdbImpl(transaction, getRepository(), path);
            }
        }

        return result;
    }

    /**
     * Attempts to adapt the given object to a relational model typed class.
     * If the object is not an instance of {@link KomodoObject} then null is
     * returned.
     *
     * The type id of the {@link KomodoObject} is extracted and the correct
     * relational model object created. If the latter is not assignable from the
     * given adapted class then it is concluded the adaption should fail and
     * null is returned, otherwise the new object is returned.
     *
     * @param <T>
     *        the desired outcome class
     * @param transaction
     *        the transaction (cannot be <code>null</code> or have a state that is not
     *        {@link org.komodo.spi.repository.Repository.UnitOfWork.State#NOT_STARTED})
     * @param object
     *        the object being resolved
     * @param resolvedClass
     *        the class the object should be resolved to (cannot be <code>null</code>)
     * @return the strong typed object of the desired type
     * @throws KException
     *         if a resolver could not be found, if the object was not resolvable, or if an error occurred
     */
    public < T extends KomodoObject > T resolve( final UnitOfWork transaction,
                                                 final Object object,
                                                 final Class< T > resolvedClass ) throws KException {
        ArgCheck.isNotNull( transaction, "transaction" ); //$NON-NLS-1$
        ArgCheck.isTrue( ( transaction.getState() == org.komodo.spi.repository.Repository.UnitOfWork.State.NOT_STARTED ),
                         "transaction state is not NOT_STARTED" ); //$NON-NLS-1$

        AdapterFactory adapter = new AdapterFactory(getRepository());
        T kobject = adapter.adapt(transaction, object, resolvedClass);
        return kobject;
    }

    private void validateWorkspaceMember( final UnitOfWork uow,
                                          final KomodoObject kobject ) throws KException {
        if (!getRepository().equals(kobject.getRepository())) {
            throw new KException(Messages.getString(Relational.OBJECT_BEING_DELETED_HAS_WRONG_REPOSITORY,
                                                    kobject.getAbsolutePath(),
                                                    kobject.getRepository().getId().getUrl(),
                                                    getRepository().getId().getUrl()));
        }

        if (!kobject.getAbsolutePath().startsWith(getRepository().komodoWorkspace(uow).getAbsolutePath())) {
            throw new KException(Messages.getString(Relational.OBJECT_BEING_DELETED_HAS_NULL_PARENT, kobject.getAbsolutePath()));
        }
    }

}
