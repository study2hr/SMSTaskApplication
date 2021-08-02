package com.getdefault.smsapplication.data.repository

import androidx.lifecycle.MediatorLiveData
import com.getdefault.smsapplication.data.db.entity.SMSEntity
import androidx.lifecycle.LiveData


class SMSListRepository {

    private val smsData: MediatorLiveData<SMSEntity> = MediatorLiveData<SMSEntity>()

    companion object{
        val smsListRepository = SMSListRepository()
    }
    fun getSMSData(): LiveData<SMSEntity> {
        return smsData
    }
    fun addDataSource(data: LiveData<SMSEntity>) {
        smsData.addSource(data, smsData::setValue)
    }

    fun removeDataSource(data: LiveData<SMSEntity>) {
        smsData.removeSource(data)
    }

}