package spring.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import spring.common.PlanetConstaints.PLANET


//@SpringBootTest(classes = [PlanetService::class])
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension::class)
class PlanetServiceUnitTest {
    @InjectMocks
    lateinit var planetService: PlanetService

    @Mock
    lateinit var planetRepository: PlanetRepository

    @Test
    fun createPlanetTest_with_validData_shouldReturnValidPlanet() {
        `when`(planetRepository.save(PLANET)).thenReturn(PLANET)

        val sut = planetService.create(PLANET)

        assertThat(sut).isNotNull()
        assertThat(sut).isEqualTo(PLANET)

    }

    @Test
    fun createPlanetTest_with_invalidData_shouldThrowException() {
        `when`(planetRepository.save(PLANET)).thenThrow(RuntimeException::class.java)

        assertThrows(RuntimeException::class.java) {
            planetService.create(PLANET)
        }
    }

}