package spring.entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import spring.resources.UserResource
import spring.services.UserService
import java.util.*


@ExtendWith(MockitoExtension::class)
class UserTest {
    @Mock
    private val userService: UserService? = null

    @InjectMocks
    private val userResource: UserResource? = null

    @Test
    fun test_getUsers_returns_all_users() {
        // Arrange
        val users: List<User> = listOf(
            User(1, "User 1", "user1@example.com", "1234567890", "password"),
            User(2, "User 2", "user2@example.com", "0987654321", "password")
        )
        `when`(userService!!.findAll()).thenReturn(users)


        val responseEntity = userResource!!.getUsers()

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertEquals(users, responseEntity.body)
    }


//
//        assertThat(json, containsString("name"))
//        assertThat(json, containsString("email"))
//        assertThat(json, containsString("phone"))
//        assertThat(json, containsString("password"))
//        assertThat(json, containsString("id"))
//        assertThat(json, containsString("orders"))

//        val userServiceMock = mock(UserService::class.java)
//
//        // Configurando o comportamento do mock
//        `when`(userServiceMock.getUserById(1L)).thenReturn(User(1, "erick", "ericksousa312@gmail.com", "92992333149", "123456"))
//
//        // Criando uma instância do seu serviço que utiliza o UserService mockado
//        val serviceUnderTest = YourServiceUnderTest(userServiceMock)
//
//        // Executando o teste
//        val json = serviceUnderTest.serializeUserById(1L)
//
//        // Verificando asserções
//        assertThat(json, containsString("name"))
//        assertThat(json, containsString("email"))
//        assertThat(json, containsString("phone"))
//        assertThat(json, containsString("password"))
//        assertThat(json, containsString("id"))
//        assertThat(json, containsString("orders"))

}