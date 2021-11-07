package com.example.appadoskotlin2.ui.diologs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import com.example.appadoskotlin2.MainActivity
import com.example.appadoskotlin2.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LoginDialog(private val firebaseAuth: FirebaseAuth?, context: Context?) :
    DialogFragment() {
    private var login_cancel: ImageView? = null
    private var user_input_edit: TextInputEditText? = null
    private var pwd_input_edit: TextInputEditText? = null
    private var user_input_layout: TextInputLayout? = null
    private var pwd_input_layout: TextInputLayout? = null
    private var btn_enter: MaterialButton? = null
    //private var appNavigator: AppNavigation? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(activity)
        builder.setView(R.layout.dialog_login)
        val inflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_login, null)
        builder.setView(view)
            .setCancelable(true)
        initView(view)
        initListeners()
        val dialog = builder.create()
        dialog.show()
        return dialog
    }

    private fun initListeners() {
        login_cancel!!.setOnClickListener { dismiss() }
        user_input_layout!!.setEndIconOnClickListener { user_input_edit!!.text!!.clear() }
        pwd_input_layout!!.setEndIconOnClickListener { pwd_input_edit!!.text!!.clear() }
        pwd_input_edit!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                pwd_input_layout!!.isErrorEnabled = false
            }
        })
        btn_enter!!.setOnClickListener {
            firebaseAuth?.signInWithEmailAndPassword(user_input_edit!!.text.toString(),
                pwd_input_edit!!.text.toString())
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        pwd_input_layout!!.isErrorEnabled = false
                        //Send to HomeFragment
                        //TODO("Solucionar  java.lang.IllegalStateException: no current navigation node")
                            val intent = Intent(context?.applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            dismiss()
                    } else {
                        pwd_input_layout!!.error = getString(R.string.wrong_username_or_password)
                        pwd_input_layout!!.isErrorEnabled = true
                    }
                }
        }
    }

    private fun initView(view: View) {
        login_cancel = view.findViewById<View>(R.id.login_cancel) as ImageView
        btn_enter = view.findViewById<View>(R.id.btn_enter) as MaterialButton
        pwd_input_layout = view.findViewById<View>(R.id.pwd_input_layout) as TextInputLayout
        pwd_input_edit = view.findViewById<View>(R.id.pwd_input_edit) as TextInputEditText
        user_input_layout = view.findViewById<View>(R.id.user_input_layout) as TextInputLayout
        user_input_edit = view.findViewById<View>(R.id.user_input_edit) as TextInputEditText
    }

    init {
        //appNavigator = AppNavigator()
    }
}
