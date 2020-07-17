package com.dude.moneymanager.landing

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dude.moneymanager.R
import com.dude.moneymanager.convertLongToDateString
import com.dude.moneymanager.database.Exchange
import java.text.DateFormat
import java.util.*

@BindingAdapter("descriptionString")
fun TextView.setDescriptionFormatted(item: Exchange){
    text = item.description
}
@BindingAdapter("categoryString")
fun TextView.setCategoryFormatted(item: Exchange){
    text = item.category
}
@BindingAdapter("amountFormatted")
fun TextView.setAmountFormatted(item: Exchange){
    text = item.amount.toString() +" S/."
    setTextColor(
        when (item.type) {
            "Income" -> Color.GREEN
            else -> Color.RED
        }
    )

}
@BindingAdapter("dateFormatted")
fun TextView.setDateFormatted(item: Exchange){
    text = DateFormat.getDateInstance(DateFormat.DEFAULT).format( item.dateOfExchange)
}
@BindingAdapter("exchangeImage")
fun ImageView.setExchangeImage(item: Exchange){
    setImageResource(
        when (item.type) {
            "Income" -> R.drawable.ic_baseline_trending_up_24
            else -> R.drawable.ic_baseline_trending_down_24
        }
    )
}
