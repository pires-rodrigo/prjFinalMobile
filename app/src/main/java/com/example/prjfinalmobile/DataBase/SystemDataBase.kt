package com.example.prjfinalmobile.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.prjfinalmobile.Class.Usuario
import com.example.prjfinalmobile.Class.Viagem
import com.example.prjfinalmobile.ClassDao.UsuarioDao
import com.example.prjfinalmobile.ClassDao.ViagemDao
import com.example.prjfinalmobile.Converters.ConvertTypes

@Database(entities = [Viagem::class, Usuario::class], version = 1, exportSchema = false)
@TypeConverters(ConvertTypes::class)
abstract class SystemDataBase : RoomDatabase() {

    abstract val viagemDao:ViagemDao
    abstract val usuarioDao:UsuarioDao

    companion object{
        @Volatile
        private var INSTANCE: SystemDataBase? = null

        fun getDataBase(context: Context): SystemDataBase = INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                context,
                SystemDataBase::class.java,
                "viagem-db"
            ).build()
            INSTANCE = instance
            instance
        }

    }

}