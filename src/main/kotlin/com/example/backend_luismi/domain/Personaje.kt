package com.example.backend_luismi.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
class Personaje(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var nombre: String = "",
    var tripulacion: String = "",

    @OneToMany(
        mappedBy = "personaje",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    @JsonManagedReference
    var habilidades: MutableList<Habilidad> = mutableListOf()
) {
    @PrePersist
    @PreUpdate
    private fun SincronizarHabilidades() {
        habilidades.forEach { it.personaje = this }
    }
}
