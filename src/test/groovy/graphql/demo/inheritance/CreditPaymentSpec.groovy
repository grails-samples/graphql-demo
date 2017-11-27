package graphql.demo.inheritance

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CreditPaymentSpec extends Specification implements DomainUnitTest<CreditPayment> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
