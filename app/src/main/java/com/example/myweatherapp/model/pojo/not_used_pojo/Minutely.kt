package com.example.myweatherapp.model.pojo.not_used_pojo

import com.google.gson.annotations.SerializedName

data class Minutely (@SerializedName("dt")  var dt: Int?,
                     @SerializedName("precipitation")  var precipitation: Double?    )