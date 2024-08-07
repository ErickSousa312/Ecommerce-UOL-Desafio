package spring.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface PlanetRepository : JpaRepository<Planet, Long> {

    @Query("select p from Planet p where p.planetName = :name")
    fun findByName(@Param("name") name: String): Optional<Planet>

    @Query(value = "SELECT * FROM planets WHERE terrain = :terrain", nativeQuery = true)
    fun findByTerrain(@Param("terrain") terrain: String): Optional<Planet>
}