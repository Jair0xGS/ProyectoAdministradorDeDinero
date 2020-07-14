package com.dude.moneymanager.landing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dude.moneymanager.R
import com.dude.moneymanager.TextItemViewHolder
import com.dude.moneymanager.convertLongToDateString
import com.dude.moneymanager.database.Exchange

class TheAdapter : RecyclerView.Adapter<TheAdapter.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val detail: TextView = itemView.findViewById(R.id.detailText)
        val amount: TextView = itemView.findViewById(R.id.amountText)
        val category : TextView = itemView.findViewById(R.id.categoryText)
        val date : TextView = itemView.findViewById(R.id.dateText)
        val image: ImageView = itemView.findViewById(R.id.typeExchangeImage)

    }
    var data = listOf<Exchange>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources

        holder.category.text = item.category
        holder.detail.text = item.description
        holder.amount.text = item.amount.toString()+" S/."
        holder.date.text = convertLongToDateString(item.dateOfExchange)
        holder.image.setImageResource(when (item.type) {
            "Income" -> R.drawable.ic_baseline_trending_up_24
            else -> R.drawable.ic_baseline_trending_down_24
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val infaldor =  LayoutInflater.from(parent.context)

        val view  = infaldor.inflate(R.layout.list_item_exchange,parent,false)
        return ViewHolder(view)


    }


}