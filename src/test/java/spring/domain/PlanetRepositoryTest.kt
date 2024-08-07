package spring.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spring.common.PlanetConstaints.PLANET

@DataJpaTest
class PlanetRepositoryTest {
    @Autowired
    lateinit var planetRepository: PlanetRepository

    @Test
    fun testGivenPlanet_whenCreate_thenReturnPlanet() {
        val planet = planetRepository.save(PLANET)
        assertNotNull(planet)
        assertThat(planet).isEqualTo(PLANET)
    }

    @Test
    fun testGivenPlanet_whenFindByName_thenReturnPlanet() {
        val planet = planetRepository.save(PLANET)
        val planetSearch = planetRepository.findByName(planet.planetName!!)
        println(planetSearch)
        assertNotNull(planetSearch)
        assertThat(planetSearch.get().planetName).isEqualTo(PLANET.planetName)
    }

    @Test
    fun testGivenPlanet_whenFindByTerrain_thenReturnPlanet() {
        val planet = planetRepository.save(PLANET)
        val planetSearchTerrain = planetRepository.findByTerrain(planet.terrain!!)
        assertNotNull(planetSearchTerrain)
        assertThat(planetSearchTerrain.get().terrain).isEqualTo(planet.terrain)
    }
}