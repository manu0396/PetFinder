package com.example.appadoskotlin2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appadoskotlin2.R
import com.example.appadoskotlin2.data.RoomResponse
import com.example.appadoskotlin2.databinding.FragmentHomeBinding
import com.example.appadoskotlin2.ui.adapters.AdapterHome
import com.example.appadoskotlin2.ui.utils.SimpleDividerItemDecoration
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),  (RoomResponse) -> Unit  {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var rvHomeFragment : RecyclerView
    private lateinit var rvAdapterHome : AdapterHome
    private lateinit var navController : NavController
    private var selectService: RoomResponse? = null
    private lateinit var bundle: Bundle
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var tvUser : TextView
    private lateinit var mAuth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Print the username into the home Screen.
        mAuth = FirebaseAuth.getInstance()
        tvUser = root.findViewById(R.id.tvTitleHome)
        tvUser.text = getString(R.string.welcome_messages, mAuth.currentUser?.email.toString())


        //Set the recycleView configuration
        rvHomeFragment = binding.root.findViewById(R.id.rvServicesHome);
        rvHomeFragment.addItemDecoration(SimpleDividerItemDecoration(this.context, R.drawable.line_divider))
        linearLayoutManager = LinearLayoutManager(this.context)
        rvHomeFragment.layoutManager = linearLayoutManager
        //Set inicial data for the adapter.
        val services = ArrayList<RoomResponse>()
        /*
        services.add(RoomResponse("ofertado", "Carpinteria", 0))
        services.add(RoomResponse("contratado", "Fontaneria", 0))
        services.add(RoomResponse("ofertado", "Electricidad", 0))
        */
        rvAdapterHome = AdapterHome(services, this)

        rvHomeFragment.adapter = rvAdapterHome

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun invoke(s: RoomResponse) {
        //Guardo el servicio seleccionado en un bundle para pasarselo al NavHost.
        selectService = s;
        bundle = Bundle()
        bundle.putParcelable("service", selectService)

        //Navegamos al frament del item seleccionado pasandole el servicio.
        navController = Navigation.findNavController(this.requireView())
        navController.navigate(R.id.navigation_item_home, bundle)
        Toast.makeText(context, "Navigating to Details Services", Toast.LENGTH_SHORT).show()

    }
}