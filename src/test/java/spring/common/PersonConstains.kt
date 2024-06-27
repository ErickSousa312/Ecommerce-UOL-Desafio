package spring.common

import spring.domain.Person

object PersonConstains {
    val PERSON_CONSTANTS: Person = Person(null, "erick", "erick@gmail.com")
    val PERSON_INVALID_CONSTANTS: Person = Person(null, null, "")
}