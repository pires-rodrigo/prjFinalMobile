package com.example.prjfinalmobile.Converters

import androidx.room.TypeConverter
import com.example.prjfinalmobile.Class.TipoViagem
import java.util.Date

class ConvertTypes {
    @TypeConverter
    fun fromTipoViagem(tipo: TipoViagem): String {
        return tipo.name
    }

    @TypeConverter
    fun toTipoViagem(value: String): TipoViagem {
        return TipoViagem.valueOf(value)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }
}