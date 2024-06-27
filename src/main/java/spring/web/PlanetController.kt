package spring.web

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import spring.domain.Planet
import spring.domain.PlanetService
import java.net.URI

@RestController
@RequestMapping("/planets")
class PlanetController (val planetService: PlanetService){

    @PostMapping
    fun create(@RequestBody @Valid planet: Planet): ResponseEntity<Planet> {
        val result = planetService.create(planet)
        return ResponseEntity.created(URI.create("/planets/${result.id}")).body(result)
    }
}