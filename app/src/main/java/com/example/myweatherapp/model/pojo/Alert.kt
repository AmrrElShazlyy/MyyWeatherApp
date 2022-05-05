package com.example.myweatherapp.model.pojo


import androidx.room.TypeConverters
import com.example.myweatherapp.database.converters.AlertTypeConverter
import com.google.gson.annotations.SerializedName

@TypeConverters(AlertTypeConverter::class)
data class Alert(@SerializedName("sender_name")  var senderName: String?,
                 @SerializedName("event")  var event: String?,
                 @SerializedName("start")  var start: Int?,
                 @SerializedName("end")  var end: Int?,
                 @SerializedName("description")  var description: String?  )