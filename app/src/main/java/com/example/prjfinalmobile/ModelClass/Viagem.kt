package com.example.prjfinalmobile.ModelClass

import java.time.LocalDate
import java.util.Date


enum class TipoViagem(){
    Lazer,
    Negocio,
}

class Viagem(
    val id: Long = 1,
    val destino: String = "",
    val tipo: TipoViagem = TipoViagem.Lazer,
    val dtini: Date? = null,
    val dtFim: Date? = null,
    val orcamento: Float = 10.00f
)