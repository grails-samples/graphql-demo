package graphql.demo.inheritance

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CheckPaymentSpec extends Specification implements DomainUnitTest<CheckPayment> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
