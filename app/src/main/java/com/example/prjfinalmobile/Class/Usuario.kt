package com.example.prjfinalmobile.Class

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val login: String = "",
    val senha: String = "",
    val email: String = ""
){

}