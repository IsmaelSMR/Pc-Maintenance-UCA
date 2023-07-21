package com.damo.ucamaintenance.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "PcEntity")
data class PcEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String,
    var desc : String,
    var brand : String,
    var model : String,
    var processor : String,
    var ram : String,
    var storage : String,
)
