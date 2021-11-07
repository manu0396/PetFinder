package com.example.appadoskotlin2.ui.contract

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.appadoskotlin2.R
import com.example.appadoskotlin2.data.RoomResponse
import com.example.appadoskotlin2.data.ServiceItemResponse
import com.example.appadoskotlin2.databinding.FragmentContractBinding
import com.example.appadoskotlin2.ui.adapters.AdapterContract
import com.example.appadoskotlin2.ui.adapters.ContractAdapter
import com.example.appadoskotlin2.ui.adapters.LoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContractFragment : Fragment(), ContractAdapter.OnItemClickListener {

    private lateinit var contractViewModel: ContractViewModel
    private var _binding: FragmentContractBinding? = null
    private lateinit var searchView: SearchView
    private lateinit var rvContract:RecyclerView
    private lateinit var adapter: ContractAdapter
    //TODO("Inicialmente proporcionamos los servicios de manera local.
    // En el futuro hacerlo a traves de una API.")
    private lateinit var services: ArrayList<RoomResponse>
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contractViewModel =
            ViewModelProvider(this).get(ContractViewModel::class.java)

        _binding = FragmentContractBinding.inflate(inflater, container, false)
        val root: View = binding.root


        services = ArrayList()

        searchView = root.findViewById(R.id.svContract)
        rvContract = root.findViewById(R.id.rvContract)
        //adapter = AdapterContract(this, services)
        rvContract.adapter = adapter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if(query != null && query.length>=3){
                    binding.rvContract.scrollToPosition(0)
                    contractViewModel.searchServices(query)
                    searchView.clearFocus()
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                //TODO() Change the text on SerchView to newText

                return false
            }
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentContractBinding.bind(view)
        binding.apply {
            rvContract.setHasFixedSize(true)
            rvContract.itemAnimator = null
            //TODO("Cambiar a Adapter Paging 3")
            rvContract.adapter = adapter.withLoadStateHeaderAndFooter(
                header = LoadStateAdapter{adapter.retry()},
                footer = LoadStateAdapter{adapter.retry()}
            )
            btnRetryContract.setOnClickListener {
                adapter.retry()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun OnItemClick(service: ServiceItemResponse) {
        //TODO("Navigate to item layout")
        val action = ContractFragmentDirections.navigationContractToNavigationItemContract(service)
        findNavController().navigate(action)

    }
}