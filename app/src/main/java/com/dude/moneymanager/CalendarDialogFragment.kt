package com.dude.moneymanager

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import java.text.DateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {


    var currentDate : String =""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
            return  DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
        val date = Calendar.getInstance()
        date.set(Calendar.YEAR,year)
        date.set(Calendar.MONTH,month)
        date.set(Calendar.DAY_OF_MONTH,day)
        currentDate = DateFormat.getDateInstance().format(
            date.time
        )
        Log.i("CalendarDialogFragment","setting current date value ${currentDate}")
        sendBackResult()
    }

    interface EditDateDialogListener {
        fun onFinishCalendarDialog(inputText: String?)
    }

    // Call this method to send the data back to the parent fragment
    fun sendBackResult() {
        Log.i("CalendarDialogFragment","Sending data to parent FRagment ${currentDate}")
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        val listener = targetFragment as EditDateDialogListener?
        listener!!.onFinishCalendarDialog(currentDate)
        dismiss()
    }
}