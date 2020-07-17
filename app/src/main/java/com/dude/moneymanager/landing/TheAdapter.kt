package com.dude.moneymanager.landing

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dude.moneymanager.R
import com.dude.moneymanager.convertLongToDateString
import com.dude.moneymanager.database.Exchange
import com.dude.moneymanager.databinding.ListItemExchangeBinding

//class TheAdapter : RecyclerView.Adapter<TheAdapter.ViewHolder>(){
class TheAdapter : ListAdapter<Exchange,TheAdapter.ViewHolder>(exchangeDiffCallback()){
    class exchangeDiffCallback : DiffUtil.ItemCallback<Exchange>(){
        override fun areItemsTheSame(oldItem: Exchange, newItem: Exchange): Boolean {
            return oldItem.exId == newItem.exId
        }

        override fun areContentsTheSame(oldItem: Exchange, newItem: Exchange): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder private  constructor(val binding: ListItemExchangeBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(
            item: Exchange
        ) {

            binding.exchange = item
            /**
             * This call is an optimization that asks data binding to execute any pending bindings right away.
             */
            binding.executePendingBindings()
        }
        companion object {
             fun from(parent: ViewGroup): ViewHolder {
                val infaldor = LayoutInflater.from(parent.context)

                val binding =  ListItemExchangeBinding.inflate(infaldor,parent,false)
                return ViewHolder(binding)
            }
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }




}