package com.example.backend_luismi.controller

import com.example.backend_luismi.domain.Personaje
import com.example.backend_luismi.service.PersonajeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/personajes")
class PersonajeController(private val personajeService: PersonajeService) {

    @GetMapping
    fun getAllPersonajes(): List<Personaje> = personajeService.findAll()

    @GetMapping("/{id}")
    fun getPersonajeById(@PathVariable id: Long): ResponseEntity<Personaje> {
        val personaje = personajeService.findById(id)
        return if (personaje != null) {
            ResponseEntity.ok(personaje)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createPersonaje(@RequestBody personaje: Personaje): ResponseEntity<Personaje> {
        val savedPersonaje = personajeService.save(personaje)
        return ResponseEntity(savedPersonaje, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updatePersonaje(@PathVariable id: Long, @RequestBody personaje: Personaje): ResponseEntity<Personaje> {
        val updatedPersonaje = personajeService.update(id, personaje)
        return if (updatedPersonaje != null) {
            ResponseEntity.ok(updatedPersonaje)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deletePersonaje(@PathVariable id: Long): ResponseEntity<Void> {
        personajeService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
