package com.getdefault.smsapplication.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.getdefault.smsapplication.data.db.entity.SMSEntity
import com.getdefault.smsapplication.utils.Constants.Util.action_SMS_RECEIVER
import com.getdefault.smsapplication.utils.Utils.getDateCurrentTimeZone


class SMSReceiver : BroadcastReceiver() {

    internal var smsResponseMediatorLiveData: MutableLiveData<SMSEntity> = MutableLiveData()
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == action_SMS_RECEIVER){
        val bundle = intent.extras

        try {
            if (bundle != null) {
                val pdusObj = bundle["pdus"] as Array<*>?
                for (i in pdusObj!!.indices) {
                    val currentMessage = SmsMessage.createFromPdu(
                        pdusObj[i] as ByteArray
                    )
                    val phoneNumber = currentMessage.displayOriginatingAddress.toString()
                    val message = currentMessage.displayMessageBody
                    val time = getDateCurrentTimeZone(currentMessage.timestampMillis/1000)
                    smsResponseMediatorLiveData.value = SMSEntity(phoneNumber,message,time)
                    Log.e("MessageReceiver", message)

                } // end for loop
            } // bundle is null
        } catch (e: Exception) {
            Log.e("SmsReceiver", "Exception smsReceiver$e")
        }
    }
}
    fun getSMSResponseData(): LiveData<SMSEntity> {
        return smsResponseMediatorLiveData
    }


}
