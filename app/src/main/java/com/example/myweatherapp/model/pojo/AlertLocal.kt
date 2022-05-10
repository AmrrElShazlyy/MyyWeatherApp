package com.example.myweatherapp.model.pojo

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class AlertLocal (@PrimaryKey(autoGenerate = true)@ColumnInfo(name = "alertId") var alertLocalId : Int,
                       @ColumnInfo(name = "type") var type: String,
                       @ColumnInfo(name = "time") var time: String,
                       @ColumnInfo(name = "time") var startDate: String,
                       @ColumnInfo(name = "time") var endDate: String) {

}