package com.example.myweatherapp.model.weather_data_model

import com.google.gson.annotations.SerializedName

data class Rain (@SerializedName("1h")  private var _1h: Double?){
    fun get1h(): Double? {
        return _1h
    }
    fun set1h(_1h: Double?) {
        this._1h = _1h
    }
}