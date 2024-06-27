package spring.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import spring.domain.Person

@Repository
interface PersonRepository: CrudRepository<Person, Long>