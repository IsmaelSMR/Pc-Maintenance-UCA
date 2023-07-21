package com.damo.ucamaintenance

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class PcApplication : Application() {

    companion object{
        lateinit var database: PcDatabase
    }

    override fun onCreate() {
        super.onCreate()

        val MIGRATION_1_2 = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE PcEntity ADD COLUMN photoUrl TEXT NOT NULL DEFAULT ''")
            }
        }
        database = Room.databaseBuilder(
            this,
            PcDatabase::class.java,
            "PcDatabase"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}
