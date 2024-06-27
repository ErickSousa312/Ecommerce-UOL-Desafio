package spring.domain

import org.springframework.data.jpa.repository.JpaRepository

interface PlanetRepository : JpaRepository<Planet, Long> {
}