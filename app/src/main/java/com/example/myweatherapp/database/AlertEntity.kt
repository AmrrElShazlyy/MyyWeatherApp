package com.example.myweatherapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "alertEntity")
class AlertEntity {

     @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "alertId") var alertId : Int = 0
     @ColumnInfo(name = "sender_name") var senderName: String = ""
     @ColumnInfo(name = "event") var event: String = ""
     @ColumnInfo(name = "start") var start: Int = 0
     @ColumnInfo(name = "end") var end: Int = 0
     @ColumnInfo(name = "description") var descriptiion = ""

}