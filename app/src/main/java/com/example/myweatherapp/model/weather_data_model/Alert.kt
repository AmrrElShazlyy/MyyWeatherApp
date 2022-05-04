package com.example.myweatherapp.model.weather_data_model

import com.google.gson.annotations.SerializedName

data class Alert(@SerializedName("sender_name")  var senderName: String?,
                 @SerializedName("event")  var event: String?,
                 @SerializedName("start")  var start: Int?,
                 @SerializedName("end")  var end: Int?,
                 @SerializedName("description")  var description: String?  )