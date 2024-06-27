package spring.domain

import jakarta.persistence.*
import kotlinx.serialization.Serializable

@Serializable
@Entity
class Person (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private val id: Long? = null,
    var name:String? = null,
    var email: String? = null,
){
    constructor(person:Person):this (
        id = person.id,
        name = person.name,
        email = person.email
    )

    var publicName: String?
        get() = name
        set(value) {
            name = value
        }
    override fun toString(): String {
        return "Person(id=$id name=$name, email=$email)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (id != other.id) return false
        if (name != other.name) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        return result
    }

}