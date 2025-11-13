package com.example.backend_luismi.service

import com.example.backend_luismi.domain.Personaje
import com.example.backend_luismi.repository.PersonajeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PersonajeService(private val personajeRepository: PersonajeRepository) {

    fun findAll(): List<Personaje> = personajeRepository.findAll()

    fun findById(id: Long): Personaje? = personajeRepository.findById(id).orElse(null)

    fun save(personaje: Personaje): Personaje = personajeRepository.save(personaje)

    fun update(id: Long, personajeActualizado: Personaje): Personaje? {
        return personajeRepository.findById(id).map { personajeExistente ->
            // Actualiza los campos del personaje
            personajeExistente.nombre = personajeActualizado.nombre
            personajeExistente.tripulacion = personajeActualizado.tripulacion

            // Limpia la lista de habilidades anterior
            personajeExistente.habilidades.clear()

            // Añade las nuevas habilidades y establece la relación
            personajeActualizado.habilidades.forEach { nuevaHabilidad ->
                personajeExistente.habilidades.add(nuevaHabilidad)
                nuevaHabilidad.personaje = personajeExistente
            }

            personajeRepository.save(personajeExistente)
        }.orElse(null)
    }

    fun deleteById(id: Long) {
        if (personajeRepository.existsById(id)) {
            personajeRepository.deleteById(id)
        }
    }
}
