package com.example.prjfinalmobile.ClassDao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.prjfinalmobile.Class.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Insert
    fun insert(usuario: Usuario) : Long

    @Update
    fun update(usuario: Usuario)

    @Upsert
    suspend fun upsert(usuario: Usuario) : Long

    @Delete
    suspend fun delete(usuario: Usuario)

    @Query("SELECT * FROM Usuario ORDER BY id DESC")
    fun getAll() : Flow<List<Usuario>>

    @Query("SELECT * FROM Usuario V WHERE V.id = :id")
    suspend fun findById(id: Long) : Usuario?

    @Query("SELECT * FROM Usuario p where p.login = :usuario")
    suspend fun findByLogin(usuario : String) : Usuario?
}