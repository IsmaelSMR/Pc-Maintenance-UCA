package com.damo.ucamaintenance.`interface`


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.damo.ucamaintenance.entities.PcEntity

@Dao
interface PcDao {
    @Query("SELECT * FROM PcEntity")
    fun getAllPcs(): MutableList<PcEntity>

    @Insert
    fun addPc(pcEntity: PcEntity): Long

    @Update
    fun updatePc(pcEntity: PcEntity)

    @Delete
    fun deletePc(pcEntity: PcEntity)
}
