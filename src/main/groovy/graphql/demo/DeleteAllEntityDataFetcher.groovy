package graphql.demo

import grails.gorm.DetachedCriteria
import graphql.schema.DataFetchingEnvironment
import groovy.transform.CompileStatic
import org.grails.datastore.mapping.model.PersistentEntity
import org.grails.gorm.graphql.fetcher.impl.DeleteEntityDataFetcher

@CompileStatic
class DeleteAllEntityDataFetcher<T> extends DeleteEntityDataFetcher<T> {

    private final String idProperty

    DeleteAllEntityDataFetcher(PersistentEntity entity) {
        this(entity, null)
    }

    DeleteAllEntityDataFetcher(PersistentEntity entity, String projectionName) {
        super(entity, projectionName)
        if (entity.identity) {
            idProperty = entity.identity.name
        } else if (entity.compositeIdentity) {
            idProperty = entity.compositeIdentity[0].name
        }
    }

    /**
     * Deletes all records for the given entity
     */
    void delete(DataFetchingEnvironment environment) {
        Number count = null
        withTransaction(false) {
            count = new DetachedCriteria<T>(entity.javaClass).build {
                isNotNull(idProperty)
            }.deleteAll()
        }
        //Store the count on the context which will be retrieved
        //by the DeleteAllResponseHandler
        ((Map) environment.context).put("deletedCount", count)
    }
}
