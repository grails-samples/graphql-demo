package graphql.demo.simple

import grails.testing.mixin.integration.Integration
import org.grails.gorm.graphql.plugin.testing.GraphQLSpec
import spock.lang.Specification
import spock.lang.Stepwise

@Integration
@Stepwise
class PetIntegrationSpec extends Specification implements GraphQLSpec {

    void "test creating pets"() {
        when:
        def resp = graphQL.graphql("""
            mutation {
              pet1: petCreate(pet: {name: "Bark Wahlberg", type: DOG}) {
                id
                name
                type
                errors {
                  field
                  message
                }
              }
              
              pet2: petCreate(pet: {name: "Mr. Bigglesworth", type: CAT}) {
                id
                name
                type
                errors {
                  field
                  message
                }
              }
              
              pet3: petCreate(pet: {name: "Monty the Python", type: SNAKE}) {
                id
                name
                type
                errors {
                  field
                  message
                }
              }
              
              pet4: petCreate(pet: {name: "Swedish", type: FISH}) {
                id
                name
                type
                errors {
                  field
                  message
                }
              }
            }
        """)

        def result = resp.json

        then:
        result.data.pet1.id == 1
        result.data.pet2.id == 2
        result.data.pet3.id == 3
        result.data.pet4.id == 4
    }

    void "test updating a pet"() {
        when:
        def resp = graphQL.graphql("""
            mutation {
              petUpdate(id: 2, pet: {type: EXOTIC}) {
                id
                type
                name
                errors {
                  field
                  message
                }
              }
            }
        """)
        def result = resp.json

        then:
        result.data.petUpdate.type == "EXOTIC"
    }

    void "test listing pets"() {
        when:
        def resp = graphQL.graphql("""
            {
              petList {
                id
                type
                name
              }
            }
        """)
        def result = resp.json

        then:
        result.data.petList.size() == 4
    }

    void "test retrieving a single pet"() {
        when:
        def resp = graphQL.graphql("""
            {
              pet(id: 4) {
                name
              }
            }
        """)
        def result = resp.json

        then:
        result.data.pet.name == 'Swedish'
    }

    void "test getting a pet count"() {
        when:
        def resp = graphQL.graphql("""
            {
              petCount
            }
        """)
        def result = resp.json

        then:
        result.data.petCount == 4
    }

    void "test deleting a pet"() {
        when:
        def resp = graphQL.graphql("""
            mutation {
              petDelete(id: 4) {
                success
                error
              }
            }
        """)
        def result = resp.json

        then:
        result.data.petDelete.success == true
    }

    void "test deleting all pets"() {
        when:
        def resp = graphQL.graphql("""
            mutation {
              petDeleteAll {
                deletedCount
                error
              }
            }
        """)
        def result = resp.json

        then:
        result.data.petDeleteAll.deletedCount == 3
    }

}
