package br.com.aranoua.solofacil.data.database


import androidx.room.TypeConverter
import br.com.aranoua.solofacil.data.model.Culture
import com.google.gson.Gson
import java.util.Date

class DatabaseConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromCultureJson(json: String?): Culture? {
        return json?.let { gson.fromJson(it, Culture::class.java) }
    }

    @TypeConverter
    fun cultureToJson(culture: Culture?): String? {
        return culture?.let { gson.toJson(it) }
    }
}