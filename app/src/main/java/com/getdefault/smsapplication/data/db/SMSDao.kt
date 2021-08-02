package com.getdefault.smsapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.getdefault.smsapplication.data.db.entity.SMSEntity

@Dao
interface SMSDao {

    @Query("SELECT * from sms_table")
    fun getAllMessages(): LiveData<List<SMSEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertMessage(message: SMSEntity)
}
