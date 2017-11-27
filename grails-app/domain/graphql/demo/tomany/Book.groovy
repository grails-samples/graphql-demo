package graphql.demo.tomany

class Book {

    String title

    static belongsTo = [author: Author]

    static constraints = {
    }
}
