package com.example.appadoskotlin2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.appadoskotlin2.R
import com.example.appadoskotlin2.data.RoomResponse


//TODO("Pasarle por atributo el item seleccionado y pintarlo en esta nueva pantalla")

class ItemServiceFragment: Fragment() {

    private lateinit var service: RoomResponse
    var tvDescription: TextView?     = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var root : View = inflater.inflate(R.layout.fragment_item_home, container, false) as View

        //Recover data send by HomeFragment:
        service = (arguments?.get("service") as RoomResponse)

        tvDescription = root.findViewById(R.id.tvDescItemHome)
        tvDescription?.setText(service.toString())


        return root;
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }




}