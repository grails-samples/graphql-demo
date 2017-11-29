package graphql.demo.inheritance

class CreditPayment extends Payment {

    Long cardNumber
    Short verificationCode

    static constraints = {
    }

    static graphql = true
}
