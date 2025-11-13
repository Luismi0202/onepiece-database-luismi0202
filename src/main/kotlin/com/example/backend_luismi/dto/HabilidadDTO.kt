package com.example.backend_luismi.dto

data class HabilidadDTO(
    val id: Long = 0,
    val nombre: String,
    val descripcion: String,
    val personajeId: Long? = null
)
