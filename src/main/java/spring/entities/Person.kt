package spring.entities

import jakarta.persistence.*
import kotlinx.serialization.Serializable

@Serializable
@Entity
class Person (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private var id: Long? = null,
    private var name:String? = null,
    private var email: String? = null,
){
    override fun toString(): String {
        return "Person(id=$id name=$name, email=$email)"
    }
}