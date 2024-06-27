package spring.testes

import spring.domain.Person

class PersonService2:Iperson {
    override fun create( person: Person):Person {
        if(person.name.isNullOrEmpty()||person.publicName.isNullOrEmpty()){
            throw RuntimeException()
        }
        return Person(person)
    }
}
