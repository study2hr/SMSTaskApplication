package com.getdefault.smsapplication.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.text.SimpleDateFormat
import java.util.*


object Utils {
    fun getAuthToken(context: Context): String {
        val sp: SharedPreferences =
            context.getSharedPreferences(Constants.Util.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return "Bearer " + sp.getString(Constants.Util.SESSION_AUTH_TOKEN, "")
    }

    fun setAuthToken(context: Context, token: String) {
        val sp =
            context.getSharedPreferences(Constants.Util.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(Constants.Util.SESSION_AUTH_TOKEN, token)
        editor.apply()
    }

    fun getDateCurrentTimeZone(timestamp: Long): String {
        try {
            val calendar: Calendar = Calendar.getInstance()
            val tz: TimeZone = TimeZone.getDefault()
            calendar.setTimeInMillis(timestamp * 1000)
            val sdf = SimpleDateFormat("EEEE, d MMM yyyy - hh:mm a", Locale.getDefault())
            val currenTimeZone: Date = calendar.getTime() as Date
            return sdf.format(currenTimeZone)
        } catch (e: Exception) {
        }
        return ""
    }

    fun isAllPermissionsGranted(
        activity: Activity,
        listOfPermissions: Array<String>
    ): Boolean {
        for (permission in listOfPermissions) {
            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun requestPermissions(
        activity: Activity,
        listOfPermissions: Array<String>,
        permissionRequestCode: Int
    ) {
        ActivityCompat.requestPermissions(
            activity,
            listOfPermissions,
            permissionRequestCode
        )
    }
}