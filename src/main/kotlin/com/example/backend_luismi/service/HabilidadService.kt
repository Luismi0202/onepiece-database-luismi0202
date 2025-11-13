package com.example.backend_luismi.service

import com.example.backend_luismi.dto.HabilidadDTO
import com.example.backend_luismi.mapper.HabilidadMapper
import com.example.backend_luismi.repository.HabilidadRepository
import org.springframework.stereotype.Service

@Service
class HabilidadService(private val habilidadRepository: HabilidadRepository) {

    fun findAll(): List<HabilidadDTO> {
        return habilidadRepository.findAll().map { HabilidadMapper.toDTO(it) }
    }

    fun findById(id: Long): HabilidadDTO? {
        return habilidadRepository.findById(id).map { HabilidadMapper.toDTO(it) }.orElse(null)
    }

    fun save(habilidadDTO: HabilidadDTO): HabilidadDTO {
        val habilidad = HabilidadMapper.toEntity(habilidadDTO)
        val savedHabilidad = habilidadRepository.save(habilidad)
        return HabilidadMapper.toDTO(savedHabilidad)
    }

    fun update(id: Long, habilidadDTO: HabilidadDTO): HabilidadDTO? {
        return if (habilidadRepository.existsById(id)) {
            val habilidad = HabilidadMapper.toEntity(habilidadDTO.copy(id = id))
            HabilidadMapper.toDTO(habilidadRepository.save(habilidad))
        } else {
            null
        }
    }

    fun deleteById(id: Long) {
        habilidadRepository.deleteById(id)
    }
}
