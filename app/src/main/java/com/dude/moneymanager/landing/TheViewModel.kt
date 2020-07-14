package com.dude.moneymanager.landing

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase
import com.dude.moneymanager.database.Exchange
import com.dude.moneymanager.database.TheDao
import kotlinx.coroutines.*

class TheViewModel(
    val database: TheDao,
    application: Application
) : AndroidViewModel(application){

    private var theViewModelJob = Job()
    private  var currentExchange = MutableLiveData<Exchange?>()
    private val uiScope = CoroutineScope(Dispatchers.Main + theViewModelJob)

    //getting all exchanges from db
    val allExchanges = database.getAllExchanges()
    
    //setting a navigation to navigate between frames
    private val _navigationToExchange = MutableLiveData<String>()

    val navigationToExchange : LiveData<String>
        get() = _navigationToExchange

    fun theNavigationHasFinished(){
        _navigationToExchange.value = null
    }

    private suspend fun getExchangeFromDb(): Exchange?{
        return withContext(Dispatchers.IO){
            var exchange = database.getFistExchange()
            if (exchange?.category == "") {
                exchange = null
            }
            exchange
        }
    }
    private suspend fun insert(exchange: Exchange){
        withContext(Dispatchers.IO){
            database.insert(exchange)
        }
    }


    init {
        Log.i("TheViewModel","TheViewModel was created my Dude!!!")
        initCurrentExchangeVar()

    }
    private fun initCurrentExchangeVar(){
        uiScope.launch {
            currentExchange.value = getExchangeFromDb()
        }
    }

    /**
     * implement function in the view model for the register exchange frangment...meanwhile is ussless
     */
    fun onStartExchange(){
        uiScope.launch {
            val newExchange = Exchange()
            insert(newExchange)
            currentExchange.value = getExchangeFromDb ()
        }
    }


    override fun onCleared() {
        super.onCleared()
        theViewModelJob.cancel()
        Log.i("TheViewModel", "GameViewModel destroyed all the jobs and itself!")
    }

    fun incomeHandler(){
        masterExchangeHandler("Income")
    }
    fun expenseHandler(){
        masterExchangeHandler("Expense")
    }
    private fun masterExchangeHandler(type:String) {
        _navigationToExchange.value =type
    }

}