/*
* JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.komodo.rest;

import static org.komodo.rest.Messages.Error.COMMIT_TIMEOUT;
import static org.komodo.rest.Messages.Error.RESOURCE_NOT_FOUND;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Variant;
import javax.ws.rs.core.Variant.VariantListBuilder;
import org.komodo.core.KEngine;
import org.komodo.repository.SynchronousCallback;
import org.komodo.rest.KomodoRestEntity.ResourceNotFound;
import org.komodo.rest.json.JsonConstants;
import org.komodo.rest.relational.json.KomodoJsonMarshaller;
import org.komodo.spi.KException;
import org.komodo.spi.repository.Repository;
import org.komodo.spi.repository.Repository.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Komodo service implementation.
 */
public abstract class KomodoService implements JsonConstants {

    private static final Logger LOGGER = LoggerFactory.getLogger( KomodoService.class );

    private static final int TIMEOUT = 30;
    private static final TimeUnit UNIT = TimeUnit.SECONDS;

    protected final Repository repo;

    /**
     * Constructs a Komodo service.
     *
     * @param engine
     *        the Komodo Engine (cannot be <code>null</code> and must be started)
     */
    protected KomodoService( final KEngine engine ) {
        this.repo = engine.getDefaultRepository();
    }

    /**
     * @param value the value
     * @return the value encoded for json
     */
    public static String encode(String value) {
        if (value == null)
            return null;

        value = value.replaceAll(COLON, PREFIX_SEPARATOR);
        return value;
    }

    /**
     * @param value the value
     * @return the value decoded from json transit
     */
    public static String decode(String value) {
        if (value == null)
            return null;

        value = value.replaceAll(PREFIX_SEPARATOR, COLON);
        return value;
    }

    private Object createErrorResponse(List<MediaType> acceptableMediaTypes, String errorMessage) {
        Object responseEntity = null;

        if (acceptableMediaTypes.contains(MediaType.APPLICATION_JSON_TYPE))
            responseEntity = OPEN_BRACE + "Error: " + errorMessage + CLOSE_BRACE; //$NON-NLS-1$
        else if (acceptableMediaTypes.contains(MediaType.APPLICATION_XML_TYPE))
            responseEntity = "<error message=\"" + errorMessage + "\"></error>"; //$NON-NLS-1$ //$NON-NLS-2$
        else
            responseEntity = errorMessage;

        return responseEntity;
    }

    private ResponseBuilder notAcceptableMediaTypesBuilder() {
        List<Variant> variants = VariantListBuilder.newInstance()
                                                                   .mediaTypes(MediaType.APPLICATION_XML_TYPE,
                                                                                       MediaType.APPLICATION_JSON_TYPE)
                                                                   .build();

        return Response.notAcceptable(variants);
    }

    private boolean isAcceptable(List<MediaType> acceptableTypes, MediaType candidate) {
        if (acceptableTypes == null || acceptableTypes.isEmpty())
            return false;

        if (candidate == null)
            return false;

        for (MediaType acceptableType : acceptableTypes) {
            if (candidate.isCompatible(acceptableType))
                return true;
        }

        return false;
    }

    protected Response commit( final UnitOfWork transaction, List<MediaType> acceptableMediaTypes,
                               final KomodoRestEntity entity ) throws Exception {
        assert( transaction.getCallback() instanceof SynchronousCallback );
        final int timeout = TIMEOUT;
        final TimeUnit unit = UNIT;

        final SynchronousCallback callback = ( SynchronousCallback )transaction.getCallback();
        transaction.commit();

        if ( callback.await( timeout, unit ) ) {
            Throwable error = callback.error();

            if ( error == null ) {
                LOGGER.debug( "commit: successfully committed '{0}', rollbackOnly = '{1}'", //$NON-NLS-1$
                              transaction.getName(),
                              transaction.isRollbackOnly() );
                ResponseBuilder builder = null;

                if ( entity == KomodoRestEntity.NO_CONTENT ) {
                    builder = Response.noContent();
                } else if ( entity instanceof ResourceNotFound ) {
                    final ResourceNotFound resourceNotFound = ( ResourceNotFound )entity;

                    String notFoundMsg = Messages.getString( RESOURCE_NOT_FOUND,
                                                             resourceNotFound.getResourceName(),
                                                             resourceNotFound.getOperationName() );
                    Object responseEntity = createErrorResponse(acceptableMediaTypes, notFoundMsg);
                    builder = Response.status( Status.NOT_FOUND ).entity(responseEntity);

                } else {

                    //
                    // Json will always be preferred over XML if both or the wildcard are present in the header
                    //
                    if (isAcceptable(acceptableMediaTypes, MediaType.APPLICATION_JSON_TYPE))
                        builder = Response.ok( KomodoJsonMarshaller.marshall( entity ), MediaType.APPLICATION_JSON );
                    else if (isAcceptable(acceptableMediaTypes, MediaType.APPLICATION_XML_TYPE))
                        builder = Response.ok( entity.getXml(), MediaType.APPLICATION_XML );
                    else {
                        builder = notAcceptableMediaTypesBuilder();
                    }
                }

                return builder.build();
            }

            // callback was called because of an error condition
            Object responseEntity = createErrorResponse(acceptableMediaTypes, error.getLocalizedMessage());
            return Response.status( Status.INTERNAL_SERVER_ERROR )
                           .entity(responseEntity)
                           .build();
        }

        // callback timeout occurred
        String errorMessage = Messages.getString( COMMIT_TIMEOUT, transaction.getName(), timeout, unit );
        Object responseEntity = createErrorResponse(acceptableMediaTypes, errorMessage);
        return Response.status( Status.GATEWAY_TIMEOUT )
                       .entity(responseEntity)
                       .build();
    }

    protected Response commit( final UnitOfWork transaction, List<MediaType> acceptableMediaTypes,
                               final List<? extends KomodoRestEntity> entities ) throws Exception {
        assert( transaction.getCallback() instanceof SynchronousCallback );
        final int timeout = TIMEOUT;
        final TimeUnit unit = UNIT;

        final SynchronousCallback callback = ( SynchronousCallback )transaction.getCallback();
        transaction.commit();

        if ( callback.await( timeout, unit ) ) {
            final Throwable error = callback.error();

            if ( error == null ) {
                LOGGER.debug( "commit: successfully committed '{0}', rollbackOnly = '{1}'", //$NON-NLS-1$
                              transaction.getName(),
                              transaction.isRollbackOnly() );
                ResponseBuilder builder = null;

                KomodoRestEntity entity;
                if ( entities == null || entities.isEmpty()) {
                    builder = Response.noContent();
                } else if ( (entity = entities.iterator().next()) instanceof ResourceNotFound ) {
                    final ResourceNotFound resourceNotFound = ( ResourceNotFound )entity;

                    String notFoundMessage = Messages.getString( RESOURCE_NOT_FOUND,
                                                                 resourceNotFound.getResourceName(),
                                                                 resourceNotFound.getOperationName() );
                    Object responseEntity = createErrorResponse(acceptableMediaTypes, notFoundMessage);
                    builder = Response.status( Status.NOT_FOUND ).entity(responseEntity);
                } else {

                    if (isAcceptable(acceptableMediaTypes, MediaType.APPLICATION_JSON_TYPE))
                        builder = Response.ok( KomodoJsonMarshaller.marshall( entity ), MediaType.APPLICATION_JSON );
                    else {
                        builder = notAcceptableMediaTypesBuilder();
                    }

                    builder = Response.ok( KomodoJsonMarshaller.marshallArray(entities.toArray(new KomodoRestEntity[0]), true), MediaType.APPLICATION_JSON );
                }

                return builder.build();
            }

            // callback was called because of an error condition
            Object responseEntity = createErrorResponse(acceptableMediaTypes, error.getLocalizedMessage());
            return Response.status( Status.INTERNAL_SERVER_ERROR )
                           .entity(responseEntity)
                           .build();
        }

        // callback timeout occurred
        String errorMessage = Messages.getString( COMMIT_TIMEOUT, transaction.getName(), timeout, unit );
        Object responseEntity = createErrorResponse(acceptableMediaTypes, errorMessage);
        return Response.status( Status.GATEWAY_TIMEOUT )
                       .type( MediaType.TEXT_PLAIN )
                       .entity(responseEntity)
                       .build();
    }

    /**
     * @param name
     *        the name of the transaction (cannot be empty)
     * @param rollbackOnly
     *        <code>true</code> if transaction must be rolled back
     * @return the new transaction (never <code>null</code>)
     * @throws KException
     *         if there is an error creating the transaction
     */
    protected UnitOfWork createTransaction( final String name,
                                            final boolean rollbackOnly ) throws KException {
        final SynchronousCallback callback = new SynchronousCallback();
        final UnitOfWork result = this.repo.createTransaction( ( getClass().getSimpleName() + ':' + name + ':'
                                                                 + System.currentTimeMillis() ),
                                                               rollbackOnly, callback );
        LOGGER.debug( "createTransaction:created '{0}', rollbackOnly = '{1}'", result.getName(), result.isRollbackOnly() ); //$NON-NLS-1$
        return result;
    }
}