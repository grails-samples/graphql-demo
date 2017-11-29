package graphql.demo.inheritance

import java.time.LocalDate

class CheckPayment extends Payment {

    Integer checkNumber
    LocalDate checkDate

    static constraints = {
    }

    static graphql = true
}
