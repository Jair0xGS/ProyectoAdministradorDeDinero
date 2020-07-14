package com.dude.moneymanager.registerExchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dude.moneymanager.database.Exchange
import com.dude.moneymanager.database.TheDao
import kotlinx.coroutines.*

class ExchangeViewModel(
    private val exchangeTypeParam: String ="",
    private val dataBaseDao : TheDao
):ViewModel(){
    private  val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main+viewModelJob)

    /**
     * encapsulacion en variable para regresar al landing frame
     */
    private val _goToLanding = MutableLiveData<Boolean?>()
    val goToLanding : LiveData<Boolean?>
        get()   =_goToLanding
    fun theNavigationHasFinished(){
        _goToLanding.value = null
    }

     val date =  MutableLiveData<String>()

      val description = MutableLiveData<String>()

      val category = MutableLiveData<String>()

      val amount = MutableLiveData<String>()



    private suspend fun insert(exchange: Exchange){
        withContext(Dispatchers.IO){

            Log.i("Exchange view Model","amount : ${amount.value}")
            Log.i("Exchange view Model","descripcion : ${description.value}")
            Log.i("Exchange view Model","exchange : $exchangeTypeParam")
            Log.i("Exchange view Model","date : ${date.value} ")
            Log.i("Exchange view Model","category : ${category.value} ")

            exchange.amount = amount.value!!.toFloat()
            exchange.description = description.value.toString()
            //exchange.dateOfExchange = System.currentTimeMillis()
            exchange.category = "myDude"
            exchange.type = exchangeTypeParam
            dataBaseDao.insert(exchange)
        }
    }

    fun saveExchangeHandler(){
        //description.value = description.value.plus("x")
        uiScope.launch {
            val newExchange = Exchange()

            insert(newExchange)
            _goToLanding.value = true
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}