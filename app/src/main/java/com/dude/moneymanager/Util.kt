package com.dude.moneymanager

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class TextItemViewHolder(val textView: TextView):RecyclerView.ViewHolder(textView)

@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy', 'HH:mm")
        .format(systemTime).toString()
}
