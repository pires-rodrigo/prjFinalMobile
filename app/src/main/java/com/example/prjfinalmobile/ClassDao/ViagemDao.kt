package com.example.prjfinalmobile.ClassDao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.prjfinalmobile.Class.Viagem
import kotlinx.coroutines.flow.Flow

@Dao
interface ViagemDao {
    @Insert
    fun insert(viagem: Viagem) : Long

    @Update
    fun update(viagem: Viagem)

    @Upsert
    suspend fun upsert(viagem: Viagem) : Long

    @Delete
    fun delete(viagem: Viagem)

    @Query("SELECT * FROM Viagem ORDER BY id DESC")
    fun getAll() : Flow<List<Viagem>>

    @Query("SELECT * FROM Viagem v WHERE v.id = :id")
    fun findByid(id: Long) : Viagem?

}