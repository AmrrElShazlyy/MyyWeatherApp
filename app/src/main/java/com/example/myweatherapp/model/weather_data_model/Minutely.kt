package com.example.myweatherapp.model.weather_data_model

import com.google.gson.annotations.SerializedName

data class Minutely (@SerializedName("dt")  var dt: Int?,
                     @SerializedName("precipitation")  var precipitation: Double?    )