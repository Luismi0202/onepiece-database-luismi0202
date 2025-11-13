package com.example.backend_luismi.mapper

import com.example.backend_luismi.domain.Personaje
import com.example.backend_luismi.dto.PersonajeDTO
import org.springframework.stereotype.Component

@Component
object PersonajeMapper {
    fun toDTO(personaje: Personaje): PersonajeDTO {
        return PersonajeDTO(
            id = personaje.id,
            nombre = personaje.nombre,
            tripulacion = personaje.tripulacion,
            habilidades = personaje.habilidades.map { HabilidadMapper.toDTO(it) }
        )
    }

    fun toEntity(personajeDTO: PersonajeDTO): Personaje {
        return Personaje(
            id = personajeDTO.id,
            nombre = personajeDTO.nombre,
            tripulacion = personajeDTO.tripulacion,
            habilidades = mutableListOf()
        )
    }
}
