package com.example.myweatherapp.model.pojo

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity(tableName = "weatherEntity" )
data class WeatherDataModel ( @PrimaryKey(autoGenerate = true) var id : Int,
                             @SerializedName("lat")@ColumnInfo(name = "lat")  var lat: Double?,
                             @SerializedName("lon")  @ColumnInfo(name ="lon")  var lon: Double?,
                             @SerializedName("timezone")  @ColumnInfo(name ="timezone")  var timezone: String?,
                             @SerializedName("timezone_offset") @ColumnInfo(name ="timezone_offset") var timezoneOffset: Int?,
                             @SerializedName("current")  @ColumnInfo(name ="current")  var current: Current?,
                             @SerializedName("hourly")  @ColumnInfo(name ="hourly")  var hourly: List<Hourly>?,
                             @SerializedName("daily")   @ColumnInfo(name ="daily")  var daily: List<Daily>?,
                             @SerializedName("alerts")  @ColumnInfo(name ="alerts")  var alert: List<Alert>?,
                             var isFavourite : Boolean = false){

    constructor() : this(0,0.0,0.0,"",0,null,emptyList(), emptyList(), emptyList())

}


/*
data class WeatherDataModel (@SerializedName("lat")  var lat: Double?,
                             @SerializedName("lon")  var lon: Double?,
                             @SerializedName("timezone")  var timezone: String?,
                             @SerializedName("timezone_offset") var timezoneOffset: Int?,
                             @SerializedName("current")  var current: Current?,
                             @SerializedName("hourly")  var hourly: List<Hourly>?,
                             @SerializedName("daily")  var daily: List<Daily>?,
                             @SerializedName("alerts")  var alert: List<Alert>?,
                                var isFavourite : Boolean = false){
 */