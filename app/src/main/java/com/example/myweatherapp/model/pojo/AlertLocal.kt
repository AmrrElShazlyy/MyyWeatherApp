package com.example.myweatherapp.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "alertsLocal")
data class AlertLocal (@PrimaryKey(autoGenerate = true)@ColumnInfo(name = "alertId") var alertLocalId : Int = 0,
                       var lat : Double,
                       var lon : Double,
                       var startDate : Long,
                       var endDate : Long,
                       var alertTime : Long,
                       var alertDays : List<String>,
                       var alertType : String) {

}




/*
@ColumnInfo(name = "type") var type: String,
                       @ColumnInfo(name = "time") var time: String,
                       @ColumnInfo(name = "time") var startDate: String,
                       @ColumnInfo(name = "time") var endDate: String
 */