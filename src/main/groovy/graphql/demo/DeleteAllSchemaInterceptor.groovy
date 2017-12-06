package graphql.demo

import graphql.schema.DataFetcher
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLType
import graphql.schema.GraphQLTypeReference
import groovy.transform.CompileStatic
import org.grails.datastore.mapping.model.PersistentEntity
import org.grails.gorm.graphql.GraphQLServiceManager
import org.grails.gorm.graphql.fetcher.DeletingGormDataFetcher
import org.grails.gorm.graphql.fetcher.GraphQLDataFetcherType
import org.grails.gorm.graphql.fetcher.interceptor.InterceptingDataFetcher
import org.grails.gorm.graphql.fetcher.interceptor.InterceptorInvoker
import org.grails.gorm.graphql.fetcher.interceptor.MutationInterceptorInvoker
import org.grails.gorm.graphql.interceptor.GraphQLSchemaInterceptor
import org.grails.gorm.graphql.response.delete.GraphQLDeleteResponseHandler
import org.grails.gorm.graphql.types.GraphQLTypeManager

@CompileStatic
class DeleteAllSchemaInterceptor implements GraphQLSchemaInterceptor {

    InterceptorInvoker interceptorInvoker
    GraphQLDeleteResponseHandler deleteResponseHandler
    GraphQLServiceManager serviceManager

    DeleteAllSchemaInterceptor(GraphQLServiceManager serviceManager) {
        this.serviceManager = serviceManager
        this.interceptorInvoker = new MutationInterceptorInvoker()
        this.deleteResponseHandler = new DeleteAllResponseHandler()
    }

    @Override
    void interceptEntity(PersistentEntity entity, List<GraphQLFieldDefinition.Builder> queryFields, List<GraphQLFieldDefinition.Builder> mutationFields) {

        //A new data fetcher needs to be created per entity since it uses entity
        //data to execute the query
        DeletingGormDataFetcher deleteFetcher = new DeleteAllEntityDataFetcher(entity)
        //Assign our custom response handler
        deleteFetcher.responseHandler = deleteResponseHandler

        //Start with a fresh field definition
        GraphQLFieldDefinition.Builder deleteAll = GraphQLFieldDefinition.newFieldDefinition()
                //Give a name to the new operation
                .name(entity.decapitalizedName + "DeleteAll")
                //Pass a reference to the return type which will be created in the
                //interceptSchema method
                .type(new GraphQLTypeReference(DeleteAllResponseHandler.NAME))
                //Supply a data fetcher for the operation
                .dataFetcher(
                //Wrapping the data fetcher with an intercepting data fetcher
                //will ensure the registered data fetcher interceptors get invoked
                new InterceptingDataFetcher(
                        entity,
                        serviceManager,
                        //which interceptor invoker gets passed in determines which
                        //data fetcher interceptor method gets called
                        //Use CustomMutationInterceptorInvoker if the
                        //onCustomMutation method should be called
                        interceptorInvoker,
                        GraphQLDataFetcherType.DELETE,
                        deleteFetcher))

        mutationFields.add(deleteAll)
    }

    @Override
    void interceptSchema(GraphQLObjectType.Builder queryType, GraphQLObjectType.Builder mutationType, Set<GraphQLType> additionalTypes) {
        //Get access to the type manager
        GraphQLTypeManager typeManager = serviceManager.getService(GraphQLTypeManager)
        //Add the new type to the schema
        additionalTypes.add(deleteResponseHandler.getObjectType(typeManager))
    }
}
