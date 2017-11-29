package graphql.demo.simple

import org.grails.gorm.graphql.entity.dsl.GraphQLMapping

class Pet {

    String name
    PetType type

    static constraints = {
    }


    static graphql = GraphQLMapping.build {


    }

}
