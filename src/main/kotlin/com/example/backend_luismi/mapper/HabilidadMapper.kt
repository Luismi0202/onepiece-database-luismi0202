package com.example.backend_luismi.mapper

import com.example.backend_luismi.domain.Habilidad
import com.example.backend_luismi.domain.Personaje
import com.example.backend_luismi.dto.HabilidadDTO
import org.springframework.stereotype.Component

@Component
object HabilidadMapper {
    fun toDTO(habilidad: Habilidad): HabilidadDTO {
        return HabilidadDTO(
            id = habilidad.id,
            nombre = habilidad.nombre,
            descripcion = habilidad.descripcion,
            personajeId = habilidad.personaje?.id
        )
    }

    fun toEntity(habilidadDTO: HabilidadDTO): Habilidad {
        return Habilidad(
            id = habilidadDTO.id,
            nombre = habilidadDTO.nombre,
            descripcion = habilidadDTO.descripcion,
            personaje = habilidadDTO.personajeId?.let { Personaje(id = it) }
        )
    }
}
