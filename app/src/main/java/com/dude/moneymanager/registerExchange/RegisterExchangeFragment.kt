package com.dude.moneymanager.registerExchange

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.dude.moneymanager.CalendarDialogFragment
import com.dude.moneymanager.R
import com.dude.moneymanager.database.MoneyManagerDb
import com.dude.moneymanager.databinding.FragmentRegisterExchangeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterExchangeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterExchangeFragment : Fragment() , CalendarDialogFragment.EditDateDialogListener {

    lateinit  var binding  : FragmentRegisterExchangeBinding
    lateinit var viewModel: ExchangeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.binding = DataBindingUtil.inflate<FragmentRegisterExchangeBinding>(

            inflater,
            R.layout.fragment_register_exchange,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val arguments = RegisterExchangeFragmentArgs.fromBundle(requireArguments())
        val dataSource = MoneyManagerDb.getInstance(application).theDao

        val viewModelFactory = ExchangeViewModelFactory(arguments.typeExchange,dataSource =  dataSource)

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(ExchangeViewModel::class.java)
        binding.exchangeViewModel  = viewModel
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.setLifecycleOwner(this)

        viewModel.goToLanding.observe(viewLifecycleOwner, Observer {
                theBooleanValue ->
            theBooleanValue?.let {
                this.findNavController().navigate(
                    RegisterExchangeFragmentDirections.actionRegisterExchangeFragmentToLandingFragment()
                )
                viewModel.theNavigationHasFinished()
            }
        })

        binding.editTextDate.setOnClickListener{
            showCalendarDialog()
        }
        binding.categorySelection.setOnClickListener{
            showCategoryDialog()
        }

        return binding.root
        //return inflater.inflate(R.layout.fragment_register_exchange, container, false)
    }
    @SuppressLint("LongLogTag")
    private fun showCategoryDialog(){

        //Crear un dialog  simplemente usando Material Dialog
        MaterialDialog(requireContext()).show {
            //asginar el titulo
            title(R.string.select_title)
            //asignar los items disponibles de acuerdo a el tipo de transaccion
            listItemsSingleChoice(when(viewModel.typeValue){
                "Income" -> R.array.incomes_array
                else -> R.array.expenses_array
            }){ dialog, index, text ->
                //asignar la categoria en el view model
                viewModel.category.value = text.toString()
                if(!viewModel.allowSubmit.value!!){
                    viewModel.allowSubmit.value = true
                }
            }
            positiveButton(R.string.select_text)
        }
    }

    private fun showCalendarDialog() {
        val fm: FragmentManager? = fragmentManager
        val editNameDialogFragment: CalendarDialogFragment =
            CalendarDialogFragment.getNewInstance(viewModel.date.value!!)
        // SETS the target fragment for use later when sending results
        editNameDialogFragment.setTargetFragment(this, 300)
        //mostrar el fragmento
        editNameDialogFragment.show(fm!!, "dialog_fragment_calendar_picker")
    }
    override fun onFinishCalendarDialog(inputText: String?) {
        Log.i("my men","the textPassed id ${inputText}")
        viewModel.date.value = inputText
    }


}