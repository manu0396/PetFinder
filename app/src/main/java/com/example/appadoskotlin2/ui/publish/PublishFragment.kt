package com.example.appadoskotlin2.ui.publish

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appadoskotlin2.MainActivity
import com.example.appadoskotlin2.R
import com.example.appadoskotlin2.data.RoomEntity
import com.example.appadoskotlin2.data.RoomResponse
import com.example.appadoskotlin2.databinding.FragmentPublishBinding
import com.example.appadoskotlin2.ui.adapters.AdapterPublish
import com.example.appadoskotlin2.ui.diologs.PublishDiolog
import com.example.appadoskotlin2.ui.utils.SimpleDividerItemDecoration
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PublishFragment : Fragment(), (RoomResponse) -> Unit, PublishDiolog.ConfirmationDialogListener {

    private lateinit var publishViewModel: PublishViewModel
    private var _binding: FragmentPublishBinding? = null
    private lateinit var rvPublish: RecyclerView
    private lateinit var adapter: AdapterPublish
    //TODO("Inicialmente proporcionamos los servicios de manera local.
    // En el futuro hacerlo a traves de una API.")
    private lateinit var user_description: String
    private lateinit var linearLayoutManager: LinearLayoutManager
    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!
    private var servicesOffer = ArrayList<RoomResponse>()
    private var service : RoomResponse? = null
    private lateinit var database: DatabaseReference
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        user_description = savedInstanceState?.getString("user_des").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        publishViewModel =
            ViewModelProvider(this).get(PublishViewModel::class.java)

        _binding = FragmentPublishBinding.inflate(inflater, container, false)
        val root: View = _binding!!.root

        //TODO("Cargar array services con servicios de BBDD.")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot != null){
                    for (ds in dataSnapshot.children) {
                        val key = ds.getKey()
                        val value = ds.getValue(String::class.java)
                        if(value.equals("ofertado", true)){
                            //Add the services of BBDD into local ArrayList

                        }
                        //Do what you need to do with those values.
                        Log.d("TAG", "$key / $value")
                    }

                }else{
                    Toast.makeText(context, "No hay ningun servicio disponible", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Toast.makeText(context, "Operación cancelada", Toast.LENGTH_LONG).show()
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }


        rvPublish = root.findViewById(R.id.rvPublish)
        rvPublish.addItemDecoration(SimpleDividerItemDecoration(this.context, R.drawable.line_divider))
        linearLayoutManager = LinearLayoutManager(this.context)
        rvPublish.layoutManager = linearLayoutManager

        //Add the reference services of the Screen.
        servicesOffer

        adapter = AdapterPublish(this, servicesOffer)
        rvPublish.adapter = adapter

        return root
    }


    override fun invoke(s: RoomResponse) {
        //TODO("Mostrar Alert Diolog pidiendiendo los detalles del servicio: descripción y precio")
        //TODO("Si es "Otros" permitir al usuario que introduzca la descripcion")

        val parent  = this.parentFragment?.requireView() as ViewGroup
        parent.removeAllViews()
        if(s != null){
            service=s
            showdialog(s)
        }else{
            Toast.makeText(context, "No se ha encontrado el servicio a publicar", Toast.LENGTH_LONG).show()
        }
    }

    fun showdialog(s:RoomResponse){
        fragmentManager?.let {
            val confirmationDialogFragment = PublishDiolog
                .newInstance(
                    "Confirmar: " + service?.type,
                    service,
                )
            //TODO("java.lang.IllegalStateException: The specified child already has a parent.
            // You must call removeView() on the child's parent first.")
            if(confirmationDialogFragment.parentFragment != null){
                (confirmationDialogFragment.parentFragment as ViewGroup).removeAllViews()
            }
            confirmationDialogFragment.setTargetFragment(this, 0)
            //Send the service to the Diolog.
            val args = Bundle()
            args.putParcelable("service-to-publish", s)
            confirmationDialogFragment.arguments = args
            confirmationDialogFragment.show(it, "Confirm")
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        //TODO("add to remomte DDBB la publicación del servicio.")

        //Send to Homefragment
        val intent = Intent(context?.applicationContext, MainActivity::class.java)
        startActivity(intent)
        //TODO("Guardar item en RoomDatabse")
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Toast.makeText(context, "Operación cancelada", Toast.LENGTH_SHORT).show()
        dialog.dismiss()
    }
}