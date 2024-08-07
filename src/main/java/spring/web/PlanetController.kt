package spring.web

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import spring.domain.Planet
import spring.domain.PlanetService
import spring.web.exceptions.ErrorResponse
import java.net.URI

@RestController
@RequestMapping("/planets")
class PlanetController (val planetService: PlanetService){

    @PostMapping
    fun create(@RequestBody @Valid planet: Planet): ResponseEntity<Planet> {
        val result = planetService.create(planet)
        return ResponseEntity.created(URI.create("/planets/${result.id}")).body(result)
    }

    @GetMapping("/{id}")
    fun findById( @PathVariable id: Long): ResponseEntity<out Any>? {

        val validId = id.takeIf { it > 0 } ?: return ResponseEntity.badRequest().body("Invalid ID: ID must be greater than 0")

        val planet = planetService.find(validId)
        return planet?.let {
            ResponseEntity.ok().body(planet)
        } ?: ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse(
            message = "Planet not found",
            details = "Planet with id $id does not exist in the database"
        )
        )
    }

    @GetMapping("/search")
    fun finder(@RequestParam name: String): ResponseEntity<out Any> {
        val planet = planetService.findName(name)
        return ResponseEntity.ok().body(planet!!)
    }
}