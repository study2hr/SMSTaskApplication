package com.getdefault.smsapplication.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sms_table")
data class SMSEntity(
    @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "message") var message: String,
    @ColumnInfo(name = "time") var time: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}