package graphql.demo

import graphql.schema.DataFetchingEnvironment
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLOutputType
import groovy.transform.CompileStatic
import org.grails.gorm.graphql.response.delete.GraphQLDeleteResponseHandler
import org.grails.gorm.graphql.types.GraphQLTypeManager

import static graphql.schema.GraphQLObjectType.newObject
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition

@CompileStatic
class DeleteAllResponseHandler implements GraphQLDeleteResponseHandler {

    public static final String NAME = "DeleteAllResponse"

    @Override
    GraphQLObjectType getObjectType(GraphQLTypeManager typeManager) {
        newObject()
            .description("Delete all records response")
            .name("DeleteAllResponse")
            .field(newFieldDefinition()
                .name("deletedCount")
                .type((GraphQLOutputType)typeManager.getType(Long, false))
                .build())
            .field(newFieldDefinition()
                .name("error")
                .type((GraphQLOutputType)typeManager.getType(String))
                .build())
            .build()
    }

    @Override
    Object createResponse(DataFetchingEnvironment environment, boolean success, Exception exception) {
        Number count = 0
        if (success) {
            count = (Number) ((Map) environment.context).deletedCount
        }
        [deletedCount: count.longValue(), error: exception?.message]
    }
}
