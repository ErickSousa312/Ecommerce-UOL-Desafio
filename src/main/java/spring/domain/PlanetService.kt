package spring.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PlanetService (val planetRepository: PlanetRepository ) {

    fun create (planet: Planet) : Planet {
        return planetRepository.save(planet)
    }
}