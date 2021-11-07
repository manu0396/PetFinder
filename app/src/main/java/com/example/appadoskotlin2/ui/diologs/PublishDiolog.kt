package com.example.appadoskotlin2.ui.diologs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.appadoskotlin2.MainActivity
import com.example.appadoskotlin2.R
import com.example.appadoskotlin2.data.RoomResponse
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

private const val TITLE = "Confirmar:"


class PublishDiolog: DialogFragment() {
    internal lateinit var listener: ConfirmationDialogListener
    private var service: RoomResponse? = null
    private lateinit var input: TextInputEditText
    private lateinit var input_price: TextInputEditText
    private lateinit var btn_publish: MaterialButton
    private lateinit var btn_cancel: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            listener = targetFragment as ConfirmationDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((targetFragment.toString() + " must implement ConfirmationDialogListener"))
        }

        arguments?.let {
            service = it.getSerializable("service-to-publish") as RoomResponse

        }
    }
    //TODO("Crear custom Diolog para solicitar descripción y precio del servicio.")
    @SuppressLint("ResourceType")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val view: View = inflater.inflate(R.layout.dialog_publish, null, false)
            builder.setView(view)
            initView(view)
            initListener()
            input.setHint("Descripción")
            input.inputType = InputType.TYPE_CLASS_TEXT

            input_price.setHint("€")
            input_price.inputType = InputType.TYPE_CLASS_NUMBER
            builder
                .setMessage(service?.type)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun initListener() {
       btn_publish.setOnClickListener(View.OnClickListener {
           var d_Text = input.text.toString()
           //savedInstanceState?.putString("user_des", d_Text)
           Toast.makeText(context, "Servicio " + d_Text + " publicado correctamente", Toast.LENGTH_LONG).show()
           listener.onDialogPositiveClick(this)
           //After add the services, return to Home.


       })
        btn_cancel.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, "Operación canncelada", Toast.LENGTH_LONG).show()
            listener.onDialogNegativeClick(this)
            val intent = Intent(context?.applicationContext, MainActivity::class.java)
            startActivity(intent)
        })
    }

    private fun initView(view: View) {
        input = view.findViewById(R.id.description_input_text)
        input_price = view.findViewById(R.id.price_input_edit)
        btn_publish = view.findViewById(R.id.btn_publish)
        btn_cancel = view.findViewById(R.id.btn_diologPublishCancel)
    }

    interface ConfirmationDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String?,service: RoomResponse?) =
            PublishDiolog().apply {
                arguments = Bundle().apply {
                    putParcelable("service-to-publish", service)
                    putString(TITLE + ' '  + service?.type, title)
                    view?.let { initView(it) }
                }
            }
    }
}