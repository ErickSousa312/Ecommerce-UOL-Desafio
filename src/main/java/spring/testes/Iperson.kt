package spring.testes

import spring.domain.Person

interface Iperson {
    fun create(person : Person) : Person
}