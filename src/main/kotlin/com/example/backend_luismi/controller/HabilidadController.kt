package com.example.backend_luismi.controller

import com.example.backend_luismi.dto.HabilidadDTO
import com.example.backend_luismi.service.HabilidadService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/habilidades")
class HabilidadController(private val habilidadService: HabilidadService) {

    @GetMapping
    fun findAll(): List<HabilidadDTO> {
        return habilidadService.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<HabilidadDTO> {
        val habilidad = habilidadService.findById(id)
        return if (habilidad != null) {
            ResponseEntity.ok(habilidad)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun create(@RequestBody habilidadDTO: HabilidadDTO): HabilidadDTO {
        return habilidadService.save(habilidadDTO)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody habilidadDTO: HabilidadDTO): ResponseEntity<HabilidadDTO> {
        val existingHabilidad = habilidadService.findById(id)
        return if (existingHabilidad != null) {
            ResponseEntity.ok(habilidadService.save(habilidadDTO))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        habilidadService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
