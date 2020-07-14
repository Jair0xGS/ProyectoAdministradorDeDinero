package com.dude.moneymanager.landing

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dude.moneymanager.database.TheDao
import java.lang.IllegalArgumentException

class TheViewModelFactory(
    private val dataSource: TheDao,
    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TheViewModel::class.java)){
            return TheViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown View model Class bro :/")

    }
}