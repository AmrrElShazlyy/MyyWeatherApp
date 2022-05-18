package com.example.myweatherapp.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "alertsLocal")
data class AlertLocal (@PrimaryKey(autoGenerate = true)@ColumnInfo(name = "alertId") var alertLocalId : Int? = null,
                       var lat : Double,
                       var lon : Double,
                       var startDate : Long,
                       var endDate : Long,
                       var alertDays : List<String>,
                       var alertTime : Long,
                       var alertType : String) {

}

