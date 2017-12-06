package graphql.demo.inheritance

import org.grails.gorm.graphql.entity.dsl.GraphQLMapping

import static grails.gorm.hibernate.mapping.MappingBuilder.orm

abstract class Payment {

    BigDecimal amount

    static constraints = {
    }

    static mapping = orm {
        tablePerHierarchy(false)
    }

    static graphql = GraphQLMapping.build {
        operations.list.paginate(true)
    }
}
