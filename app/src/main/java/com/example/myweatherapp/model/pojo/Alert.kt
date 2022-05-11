package com.example.myweatherapp.model.pojo


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myweatherapp.database.converters.AlertTypeConverter
import com.google.gson.annotations.SerializedName

@TypeConverters(AlertTypeConverter::class)
@Entity(tableName = "alertEntity")
data class Alert(@PrimaryKey(autoGenerate = true)@ColumnInfo(name = "alertId") var alertId : Int,
                 @SerializedName("sender_name")  @ColumnInfo(name = "sender_name") var senderName: String ,
                 @SerializedName("event")  @ColumnInfo(name = "event") var event: String ,
                 @SerializedName("start")  @ColumnInfo(name = "start") var start: Int ,
                 @SerializedName("end")  @ColumnInfo(name = "end") var end: Int ,
                 @SerializedName("description")  @ColumnInfo(name = "description") var descriptiion :String?,
                 @SerializedName("tags") var tags: List<String>)


