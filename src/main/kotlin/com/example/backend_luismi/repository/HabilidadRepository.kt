package com.example.backend_luismi.repository

import com.example.backend_luismi.domain.Habilidad
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HabilidadRepository : JpaRepository<Habilidad, Long>
