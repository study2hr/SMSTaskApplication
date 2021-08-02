package com.getdefault.smsapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.getdefault.smsapplication.data.db.entity.SMSEntity
import com.getdefault.smsapplication.data.repository.SMSDbRepository


class SMSDbViewModel(application: Application) : AndroidViewModel(application) {
    var mRepository: SMSDbRepository = SMSDbRepository(application)
    var allMessagedLiveData: LiveData<List<SMSEntity>> = mRepository.getAllWords()
    fun getAllMessages(): LiveData<List<SMSEntity>> {
        return allMessagedLiveData
    }

    fun insert(message: SMSEntity) {
        mRepository.insert(message)
    }
}