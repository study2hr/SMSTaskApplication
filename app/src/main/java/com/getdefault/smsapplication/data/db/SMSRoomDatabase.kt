package com.getdefault.smsapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.getdefault.smsapplication.data.db.entity.SMSEntity
import java.util.concurrent.Executors


@Database(entities = [SMSEntity::class], version = 2, exportSchema = false)
abstract class SMSRoomDatabase :RoomDatabase() {

    abstract fun smsDao(): SMSDao

    companion object {
        val  NUMBER_OF_THREADS = 4
        val databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS)
        @Volatile
        var INSTANCE: SMSRoomDatabase? = null

        fun getDatabase(
            context: Context,
        ): SMSRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SMSRoomDatabase::class.java,
                    "sms_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}
