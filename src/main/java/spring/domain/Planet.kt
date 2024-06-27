package spring.domain

import jakarta.persistence.*
import org.hibernate.validator.constraints.Length
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "planets")
class Planet(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = 0,
    @NotNull()
    @Length(min = 2, max = 255)
    var planetName: String? = null,
    var climate: String? = null,
    var terrain: String? = null,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Planet

        if (id != other.id) return false
        if (planetName != other.planetName) return false
        if (climate != other.climate) return false
        if (terrain != other.terrain) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (planetName?.hashCode() ?: 0)
        result = 31 * result + (climate?.hashCode() ?: 0)
        result = 31 * result + (terrain?.hashCode() ?: 0)
        return result
    }
}