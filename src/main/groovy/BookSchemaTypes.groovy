import graphql.Scalars
import graphql.schema.GraphQLTypeReference

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition
import static graphql.schema.GraphQLInputObjectField.newInputObjectField
import static graphql.schema.GraphQLInputObjectType.newInputObject
import static graphql.schema.GraphQLList.list
import static graphql.schema.GraphQLNonNull.nonNull
import static graphql.schema.GraphQLObjectType.newObject

class BookSchemaTypes {

    void create() {


        newInputObject()
            .name('BookCreate')
            .field(newInputObjectField()
                .name("title")
                .type(nonNull(Scalars.GraphQLString)))
            .field(newInputObjectField()
                .name("pages")
                .type(list(newInputObject()
                    .name("PageCreateNested")
                    .field(newInputObjectField()
                        .name("id")
                        .type(Scalars.GraphQLLong))
                    .field(newInputObjectField()
                        .name("number")
                        .type(Scalars.GraphQLInt))
                    .field(newInputObjectField()
                        .name("text")
                        .type(Scalars.GraphQLString)).build())))

        newInputObject()
            .name('BookUpdate')
            .field(newInputObjectField()
                .name("version")
                .type(Scalars.GraphQLLong))
            .field(newInputObjectField()
                .name("title")
                .type(Scalars.GraphQLString))
            .field(newInputObjectField()
                .name("pages")
                .type(list(newInputObject()
                    .name("PageUpdateNested")
                    .field(newInputObjectField()
                        .name("id")
                        .type(Scalars.GraphQLLong))
                    .field(newInputObjectField()
                        .name("number")
                        .type(Scalars.GraphQLInt))
                    .field(newInputObjectField()
                        .name("text")
                        .type(Scalars.GraphQLString)).build())))


        newObject()
            .name("DeleteResult")
            .field(newFieldDefinition()
                .name("success")
                .type(nonNull(Scalars.GraphQLBoolean)))
            .field(newFieldDefinition()
                .name("error")
                .type(Scalars.GraphQLString))


        newObject()
            .name('Book')
            .field(newFieldDefinition()
                .name("id")
                .type(Scalars.GraphQLLong))
            .field(newFieldDefinition()
                .name("version")
                .type(Scalars.GraphQLLong))
            .field(newFieldDefinition()
                .name("title")
                .type(Scalars.GraphQLString))
            //Custom type needs created instead of using String
            .field(newFieldDefinition()
                .name("dateCreated")
                .type(Scalars.GraphQLString))
            //Custom type needs created instead of using String
            .field(newFieldDefinition()
                .name("lastUpdated")
                .type(Scalars.GraphQLString))
            .field(newFieldDefinition()
                .name("pages")
                .type(list(new GraphQLTypeReference("Page"))))

        newObject()
            .name("Page")
            .field(newFieldDefinition()
                .name("id")
                .type(Scalars.GraphQLLong))
            .field(newFieldDefinition()
                .name("version")
                .type(Scalars.GraphQLLong))
            .field(newFieldDefinition()
                .name("number")
                .type(Scalars.GraphQLInt))
            .field(newFieldDefinition()
                .name("text")
                .type(Scalars.GraphQLString))

    }
}
