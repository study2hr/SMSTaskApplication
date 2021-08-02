package com.getdefault.smsapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.getdefault.smsapplication.data.db.entity.SMSEntity
import com.getdefault.smsapplication.data.repository.SMSListRepository


class SMSListViewModel(application: Application) : AndroidViewModel(application) {
    fun getData(): LiveData<SMSEntity> {
        return SMSListRepository.smsListRepository.getSMSData()
    }

}