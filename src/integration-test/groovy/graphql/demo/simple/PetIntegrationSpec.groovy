package graphql.demo.simple

import grails.testing.mixin.integration.Integration
import org.grails.gorm.graphql.plugin.testing.GraphQLSpec
import spock.lang.Specification

@Integration
class PetIntegrationSpec extends Specification implements GraphQLSpec {

    void "test calling graphql"() {
        when:
        def resp = graphQL.graphql("""
            mutation {
                petCreate(pet: {
                    name: "Butch",
                    type: DOG
                }) {
                    id
                }
            }
        """)

        def result = resp.json

        then:
        result.data.petCreate.id == 1
    }
}
