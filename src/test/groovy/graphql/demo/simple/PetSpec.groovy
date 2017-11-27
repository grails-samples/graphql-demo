package graphql.demo.simple

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class PetSpec extends Specification implements DomainUnitTest<Pet> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
