package spring.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlanetService (val planetRepository: PlanetRepository ) {

    fun create (planet: Planet) : Planet {
        return planetRepository.save(planet)
    }

    fun find(id : Long) : Planet? {
        return planetRepository.findById(id).orElse(null)
    }

    fun findName(name: String) : Planet? {
        return planetRepository.findByName(name).orElse(null)
    }
}