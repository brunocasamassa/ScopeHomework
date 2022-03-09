package com.scope.application.screens.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scope.application.R
import com.scope.application.databinding.FragmentListBinding
import com.scope.application.domain.models.Driver
import com.scope.application.screens.BaseFragment
import com.scope.application.screens.ViewModelCommands.OnError
import com.scope.application.screens.ViewModelCommands.OnListOfDriversRequested
import com.scope.application.screens.list.adapter.ListDriversAdapter
import com.scope.application.screens.list.adapter.OnItemClickListener


class ListFragment : BaseFragment() ,OnItemClickListener{

    companion object {
        const val USER_ID_SELECTED = "_user_id_selected"
    }

    lateinit var binding: FragmentListBinding

    val listDriversAdapter by lazy { ListDriversAdapter(context) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()

        viewModel.getListOfDrivers()


        //val locationManager : LocationManager? = requireActivity().getSystemService(Context.LOCATION_SERVICE)
    }

    private fun setupObserver() {
        viewModel.applicationLiveData.observe(this.viewLifecycleOwner) { response ->
            when (response) {
                is OnListOfDriversRequested -> {
                    response.drivers?.let { setupRecyclerView(it) }
                }
                is OnError -> {
                    viewModel.getListOfDrivers()
                }
            }
        }
    }

    private fun setupRecyclerView(driversResponse: List<Driver>) {
        listDriversAdapter.listOfDrivers = driversResponse
        listDriversAdapter.onItemClicked = this

        with(binding.recyclerList) {
            adapter = listDriversAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context, RecyclerView.VERTICAL, false
            )
            itemAnimator = null

        }




    }

    override fun onDriverCardClicked(driver: Driver) {
        findNavController().navigate(R.id.action_list_to_map_fragment, bundleOf(USER_ID_SELECTED to driver.userid.toString()))
    }
}