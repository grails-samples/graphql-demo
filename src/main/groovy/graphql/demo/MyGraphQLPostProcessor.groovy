package graphql.demo

import org.grails.gorm.graphql.GraphQLServiceManager
import org.grails.gorm.graphql.interceptor.manager.GraphQLInterceptorManager
import org.grails.gorm.graphql.plugin.GraphQLPostProcessor
import org.springframework.beans.factory.annotation.Autowired

class MyGraphQLPostProcessor extends GraphQLPostProcessor {

    @Autowired
    GraphQLServiceManager graphQLServiceManager

    @Override
    void doWith(GraphQLInterceptorManager interceptorManager) {
        interceptorManager.registerInterceptor(new DeleteAllSchemaInterceptor(graphQLServiceManager))
    }
}
