package spring.common

import spring.domain.Planet

object PlanetConstaints {
    val PLANET: Planet = Planet(null, "name", "climate", "terrain")
    val INVALID: Planet = Planet(null, "", "climate", "terrain")
    val PLANET_ID = Planet(1, "name", "climate", "terrain")
}