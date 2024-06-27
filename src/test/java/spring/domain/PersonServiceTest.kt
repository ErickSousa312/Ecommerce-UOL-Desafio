package spring.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import spring.common.PersonConstains.PERSON_CONSTANTS
import spring.common.PersonConstains.PERSON_INVALID_CONSTANTS
import spring.testes.PersonService2


class PersonServiceTest {

    @DisplayName("when create persons should return a Person")
    @Test
    fun createPersonTest_shouldReturnPersonValid() {
        //Given / Arrange
        val personService2 = PersonService2()
        //when / Act
        val person = personService2.create(PERSON_CONSTANTS)
        //then / Assert
        assertNotNull(person)
        assertThat(person).isEqualTo(PERSON_CONSTANTS)
    }
    @DisplayName("when getname persons should return a name")
    @Test
    fun getNamePersonTest_shouldReturnNotNullPersonName() {
        val personService2 = PersonService2()
        val person = personService2.create(PERSON_CONSTANTS)
        assertNotNull(person)
        assertThat(person.name).isNotNull()
    }


    @DisplayName("when update persons should return a Person")
    @Test
    fun invalidPersonTest_shouldReturnRunTimeException() {
        val personService2 = PersonService2()
        assertThrows(RuntimeException::class.java) {
            personService2.create(PERSON_INVALID_CONSTANTS)
        }
    }

}