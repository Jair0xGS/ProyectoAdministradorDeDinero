package com.dude.moneymanager.landing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dude.moneymanager.database.MoneyManagerDb
import com.dude.moneymanager.databinding.FragmentLandingBinding
import com.dude.moneymanager.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [LandingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LandingFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var viewModel: TheViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding  = DataBindingUtil.inflate<FragmentLandingBinding>(

            inflater,
            R.layout.fragment_landing,
            container,
            false
        )


        val application = requireNotNull(this.activity).application
        val dataSource = MoneyManagerDb.getInstance(application).theDao
        val theFactoryOfViewModels =
            TheViewModelFactory(
                dataSource,
                application
            )
        val theViewModel = ViewModelProviders.of(
            this,
            theFactoryOfViewModels
        ).get(TheViewModel::class.java)


        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = this
        binding.theViewModel = theViewModel


        //in the source code it was passed this instead of viewLifecycleOwner
        theViewModel.navigationToExchange.observe(viewLifecycleOwner, Observer {
            theString ->
            theString?.let {
                this.findNavController().navigate(
                    LandingFragmentDirections.actionLandingFragmentToRegisterExchangeFragment(
                        it
                    )
                )
                theViewModel.theNavigationHasFinished()
            }
        })

        val adapter = TheAdapter()
        binding.theRecyclerView.adapter = adapter

        theViewModel.allExchanges.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LandingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            LandingFragment()
    }
}