package spring.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spring.common.PlanetConstaints.PLANET
import org.assertj.core.api.Assertions.assertThat

@DataJpaTest
class PlanetRepositoryTest {
    @Autowired
    lateinit var PlanetRepository: PlanetRepository

    @Test
    fun testGivenPlanet_whenCreate_thenReturnPlanet() {
        val planet = PlanetRepository.save(PLANET)
        assertNotNull(planet)
        assertThat(planet).isEqualTo(PLANET)
    }

}