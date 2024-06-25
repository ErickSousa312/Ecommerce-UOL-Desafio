package spring.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import spring.entities.Person
import spring.repositories.PersonRepository

@Service
class PersonService ( val personRepository: PersonRepository) {
    fun save(person: Person): Person {
        return personRepository.save(person)
    }
    fun findAll(): List<Person> {
        val person =  personRepository.findAll()
        return person.toList()
    }
}