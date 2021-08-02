package com.getdefault.smsapplication.utils

import android.media.Rating
import androidx.recyclerview.widget.DiffUtil
import com.getdefault.smsapplication.data.db.entity.SMSEntity


class MessagesDiffUtilCallBack(oldList: List<SMSEntity>?, newList: List<SMSEntity>?) : DiffUtil.Callback() {
    private var oldList: List<SMSEntity>? = null
    private var newList: List<SMSEntity>? = null
    init {
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int {
        return oldList!!.size
    }

    override fun getNewListSize(): Int {
        return newList!!.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList!!.get(oldItemPosition).id == newList!!.get(newItemPosition).id;
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList!!.get(oldItemPosition);
        val newItem = newList!!.get(newItemPosition);
        return oldItem.message.equals(newItem.message);
    }

}