package com.getdefault.smsapplication.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.getdefault.smsapplication.utils.Constants.Util.SESSION_AUTH_TOKEN
import com.getdefault.smsapplication.utils.Constants.Util.SHARED_PREF_NAME

class SplashViewModel(application: Application) : AndroidViewModel(application) {
    val context: Context = application.applicationContext

    var isLoggedIn = MutableLiveData<Boolean>()

    fun mRedirect() {
        val sp = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val token = sp.getString(SESSION_AUTH_TOKEN, "")
        isLoggedIn.value = (token != null && token != "")
    }
}