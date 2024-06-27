package spring.web

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import spring.domain.Person
import spring.services.PersonService

@RestController
@RequestMapping("/")
class FirstController(
    private var personService: PersonService
) {

    private val logger: org.slf4j.Logger? = LoggerFactory.getLogger(FirstController::class.java)

    @GetMapping("/created")
    fun first(): ResponseEntity<Person> {
        val person = Person(null, "erick", "ericksousa312@gmail.com")
        personService.save(person)
        return ResponseEntity.ok(person)
    }

    @GetMapping("/all")
    fun all(): ResponseEntity<List<Person>> {
        val persons = personService.findAll()
        return ResponseEntity.ok(persons)
    }
    @PostMapping("/add")
    fun add(@RequestBody person: Person): ResponseEntity<Person> {
        println(person)
        logger?.info("Received request to add person: {}", person)
        personService.save(person)
        return ResponseEntity.ok(person)
    }
}