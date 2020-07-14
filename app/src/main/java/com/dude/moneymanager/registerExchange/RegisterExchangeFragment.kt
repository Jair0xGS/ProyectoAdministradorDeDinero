package com.dude.moneymanager.registerExchange

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
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
class RegisterExchangeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding  = DataBindingUtil.inflate<FragmentRegisterExchangeBinding>(

            inflater,
            R.layout.fragment_register_exchange,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        val arguments = RegisterExchangeFragmentArgs.fromBundle(requireArguments())
        val dataSource = MoneyManagerDb.getInstance(application).theDao

        val viewModelFactory = ExchangeViewModelFactory(arguments.typeExchange,dataSource)

        val viewModel = ViewModelProviders.of(this,viewModelFactory).get(ExchangeViewModel::class.java)
        binding.exchangeViewModel  = viewModel
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.setLifecycleOwner(this)

        viewModel.goToLanding.observe(viewLifecycleOwner, Observer {
                theString ->
            theString?.let {
                this.findNavController().navigate(
                    RegisterExchangeFragmentDirections.actionRegisterExchangeFragmentToLandingFragment()
                )
                viewModel.theNavigationHasFinished()
            }
        })


        return binding.root
        //return inflater.inflate(R.layout.fragment_register_exchange, container, false)
    }



}