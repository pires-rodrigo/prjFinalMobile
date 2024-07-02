package com.example.prjfinalmobile.Class

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class TipoViagem() {
    Lazer,
    Negocio
}
@Entity
data class Viagem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val destino: String = "",
    val tipo: TipoViagem = TipoViagem.Lazer,
    val dtIni: Date? = null,
    val dtFim: Date? = null,
    val orcamento: Float = 0.00f
    ){
}