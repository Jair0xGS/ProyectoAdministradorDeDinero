package com.dude.moneymanager.registerExchange

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dude.moneymanager.database.TheDao
import com.dude.moneymanager.landing.TheViewModel
import java.lang.IllegalArgumentException

class ExchangeViewModelFactory(
    private val exchangeTypeParam: String,
private val dataSource: TheDao
): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(ExchangeViewModel::class.java)){
            return ExchangeViewModel(
                exchangeTypeParam,
                dataSource

            ) as T
        }
        throw IllegalArgumentException("It's not a Exchange  View model Class bro :/")

    }
}