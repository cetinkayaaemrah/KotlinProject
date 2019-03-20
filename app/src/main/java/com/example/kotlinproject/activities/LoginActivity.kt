package com.example.kotlinproject.activities

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.kotlinproject.R
import com.example.kotlinproject.common.BasisSingleton
import com.example.kotlinproject.database.database.NewUserActivity
import com.example.kotlinproject.database.database.User
import com.example.kotlinproject.database.database.ViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


class LoginActivity : AppCompatActivity() {
    private var callbackManager: CallbackManager? = null
    private var NEW_USER_ACTIVITY_REQUEST_CODE: Int = 1
    lateinit var mViewModel: ViewModel
    private var user = emptyList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
        BasisSingleton.instance?.init(this)
        mViewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        init()
    }

    fun init() {
        callbackManager = CallbackManager.Factory.create()

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnLogin_facebook = findViewById<LoginButton>(R.id.login_button)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)


        btnLogin.setOnClickListener {
            if (checkUser(etEmail.text.toString(), etPassword.text.toString(), etEmail, etPassword)) {
                val intent = Intent(this, Main2Activity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Registration fail! Check e mail or password...", Toast.LENGTH_SHORT).show()
            }

        }

        btnLogin_facebook.setOnClickListener {
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    val intent = Intent(applicationContext, Main2Activity::class.java)
                    startActivity(intent)
                }

                override fun onCancel() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onError(error: FacebookException?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                }

            })
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, NewUserActivity::class.java)
            startActivityForResult(intent, NEW_USER_ACTIVITY_REQUEST_CODE)
        }

        mViewModel.allUsers.observe(this, object : android.arch.lifecycle.Observer<List<User>> {
            override fun onChanged(t: List<User>?) {
                user = t!!
                Log.d("Test", "LoginActivity | allusers onChange() user:" + user.size)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_USER_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data.let { data ->
                val user = User(
                    0,
                    data?.getStringExtra(NewUserActivity.E_MAIL),
                    data?.getStringExtra(NewUserActivity.NAME),
                    data?.getStringExtra(NewUserActivity.SURNAME),
                    data?.getStringExtra(NewUserActivity.PASSWORD)
                )
                mViewModel.insertUser(user)
            }
        }
        if (resultCode == NewUserActivity.USER_EXIST) {
            BasisSingleton.instance?.toastShort("User Allready Exist!")
        }

    }


    private fun checkField(email: String, password: String, etEmail: EditText, etPassword: EditText): Boolean {
        if (email.equals("") || password.equals("")) {
            if(email.equals("")){
                etEmail.error = "Fill field"
            }
            if(password.equals("")){
                etPassword.error = "Fill field"
            }
            return false
        } else {
            return true
        }
    }

    private fun checkUser(email: String, password: String, etEmail: EditText, etPassword: EditText): Boolean {

        if (checkField(email, password, etEmail, etPassword)) {
            if (!user.isEmpty()) {
                for (i in user) {
                    if (i.user_name!!.equals(email) && i.user_password!!.equals(password)) {
                        return true
                    }
                }
                return false
            } else {
                return false
            }

        } else {
            return false
        }
    }

}
