package com.example.backend_luismi.dto

data class PersonajeDTO(
    val id: Long = 0,
    val nombre: String,
    val tripulacion: String,
    val habilidades: List<HabilidadDTO> = emptyList()
)
