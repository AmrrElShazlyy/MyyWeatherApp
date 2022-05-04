package com.example.myweatherapp.model.weather_data_model

import com.google.gson.annotations.SerializedName

data class Daily (@SerializedName("dt")  var dt: Int?,
                  @SerializedName("sunrise")  var sunrise: Int?,
                  @SerializedName("sunset")  var sunset: Int?,
                  @SerializedName("moonrise")  var moonrise: Int?,
                  @SerializedName("moonset")  var moonset: Int?,
                  @SerializedName("temp")  var temp: Temp?,
                  @SerializedName("feels_like")  var feelsLike: FeelsLike?,
                  @SerializedName("pressure")  var pressure: Int?,
                  @SerializedName("humidity")  var humidity: Int?,
                  @SerializedName("dew_point")  var dewPoint: Double?,
                  @SerializedName("wind_speed")  var windSpeed: Double?,
                  @SerializedName("wind_deg")  var windDeg: Int?,
                  @SerializedName("weather")  var weather: List<Weather>?,
                  @SerializedName("clouds")  var clouds: Int?,
                  @SerializedName("pop")  var pop: Double?,
                  @SerializedName("rain")  var rain: Double?,
                  @SerializedName("uvi")  var uvi: Double? )