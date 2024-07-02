package com.example.prjfinalmobile.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.prjfinalmobile.Class.Viagem
import com.example.prjfinalmobile.ClassDao.ViagemDao

@Database(entities = [Viagem::class], version = 1, exportSchema = false)
abstract class SystemDataBase : RoomDatabase() {
    abstract val viagemDao:ViagemDao

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