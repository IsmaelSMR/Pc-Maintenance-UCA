package com.damo.ucamaintenance

import com.damo.ucamaintenance.entities.PcEntity
import com.damo.ucamaintenance.`interface`.PcDao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PcEntity::class], version = 2)
abstract class PcDatabase : RoomDatabase() {
    abstract fun pcDao() : PcDao
}