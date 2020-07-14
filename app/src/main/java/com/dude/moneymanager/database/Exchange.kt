package com.dude.moneymanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.time.LocalDateTime
//the table
@Entity
data class Exchange(
    @PrimaryKey(autoGenerate = true)
    var exId: Long = 0L,
    @ColumnInfo
    var dateOfExchange: Long = System.currentTimeMillis(),
    @ColumnInfo
    var description : String = "",
    @ColumnInfo
    var category : String = "",
    @ColumnInfo
    var amount : Float = 0.0F,
    @ColumnInfo
    var type : String = ""
)