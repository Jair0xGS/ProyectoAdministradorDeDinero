package com.dude.moneymanager.registerExchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dude.moneymanager.database.Exchange
import com.dude.moneymanager.database.TheDao
import kotlinx.coroutines.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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

    /**
     * encapsulacion de variable para ir al dialog fragment para seleccionar la fecha
     */
    private val _goToCalendar = MutableLiveData<Boolean?>()
    val goToCalendar : LiveData<Boolean?>
        get() = _goToCalendar



    fun theNavigationHasFinished(){
        _goToLanding.value = null
    }

     val date =  MutableLiveData<String>()

      val description = MutableLiveData<String>()

      val category = MutableLiveData<String>()

      val amount = MutableLiveData<String>()
        init {
            date.value =DateFormat.getDateInstance(DateFormat.DEFAULT).format( Calendar.getInstance().time)
        }
    private suspend fun insert(exchange: Exchange){
        withContext(Dispatchers.IO){

            Log.i("Exchange view Model","amount : ${amount.value}")
            Log.i("Exchange view Model","descripcion : ${description.value}")
            Log.i("Exchange view Model","exchange : $exchangeTypeParam")
            Log.i("Exchange view Model","date : ${date.value} ")
            Log.i("Exchange view Model","category : ${category.value} ")

            exchange.amount = amount.value!!.toFloat()
            exchange.description = description.value.toString()
            exchange.dateOfExchange = toMilliseconds(date.value!!)
            exchange.category = "myDude"
            exchange.type = exchangeTypeParam
            dataBaseDao.insert(exchange)
        }
    }
    fun toMilliseconds(stringDate :String) :Long{
        var f = SimpleDateFormat("MMM dd,yyyy");

        var d = f.parse(stringDate);
        var milliseconds = d.getTime();
        return milliseconds
    }

    fun saveExchangeHandler(){
        //description.value = description.value.plus("x")
        uiScope.launch {
            val newExchange = Exchange()

            insert(newExchange)
            _goToLanding.value = true
        }
    }
    fun onCalendarCalledHandler(){
        _goToCalendar.value = true
    }
    fun calendarFinished(){
        _goToCalendar.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}