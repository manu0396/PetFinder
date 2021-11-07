package com.example.appadoskotlin2

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.appadoskotlin2.ui.diologs.LoginDialog
import com.example.appadoskotlin2.ui.diologs.RegisterDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

class LoginActivity : AppCompatActivity() {
    private var btn_login: MaterialButton? = null
    private var btn_google: MaterialButton? = null
    private var btn_register: MaterialButton? = null
    private var btn_container: ConstraintLayout? = null
    private var logo: ImageView? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var mGoogleSignIn: GoogleSignInClient? = null
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)
        initView()
        initListeners()
    }

    private fun initView() {
        btn_login = findViewById<View>(R.id.btn_login) as MaterialButton
        btn_register = findViewById<View>(R.id.btn_register_login) as MaterialButton
        btn_google = findViewById<View>(R.id.btn_google) as MaterialButton
        btn_container = findViewById<View>(R.id.btn_container) as ConstraintLayout
        logo = findViewById<View>(R.id.logo) as ImageView
        FirebaseApp.initializeApp(this)
        firebaseAuth = FirebaseAuth.getInstance()


        /*
            Pasar la refencia a la BBDD al PublishFragment y ContractFragment, pasandole el
            'child': tipo de servicio.

            ¿¿Debería instanciar la BBDD en los fragment donde se usan??
            No, instanciar el ViewModel que erá el que contacte co la BBDD,
            y actualice la UI.
            //TODO: El servicio que seleccione el usuario es el child de la consulta a la BBDD.

         */
        //database = FirebaseDatabase.getInstance().reference

        btn_container!!.visibility = View.INVISIBLE
        logo!!.alpha = 0f
        logo!!.animate()
            .alpha(1f)
            .setDuration(3000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    logo!!.visibility = View.VISIBLE
                    btn_container!!.visibility = View.VISIBLE
                }
            })
        btn_container!!.alpha = 0f
        btn_container!!.animate()
            .alpha(1f)
            .setDuration(4500)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    btn_container!!.visibility = View.VISIBLE
                }
            })
    }

    private fun initListeners() {
        val loginActivity = this
        btn_register!!.setOnClickListener {
            val registerDialog = RegisterDialog(firebaseAuth, loginActivity)
            registerDialog.show(supportFragmentManager, "RegisterDialog")
        }
        btn_login!!.setOnClickListener {
            val loginDialog = LoginDialog(firebaseAuth, applicationContext)
            loginDialog.show(supportFragmentManager, "LoginDialog")
        }
        btn_google!!.setOnClickListener {
            createRequest()
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignIn!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        showHome()
    }

    fun showHome() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.w(ContentValues.TAG, "Google sign in failed", e)
            }
        }
    }

    private fun createRequest() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("786240463795-o92gljk39cmdd0pra162arebskntkdu5.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignIn = GoogleSignIn.getClient(this, gso)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val activity: Activity = this
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this
            ) { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.w(ContentValues.TAG,
                        "signInWithCredential:failure",
                        task.exception)
                    Toast.makeText(applicationContext,
                        applicationContext.getString(R.string.error),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        private const val RC_SIGN_IN = 123
    }

    init {
        //appNavigator = AppNavigator()
    }
}