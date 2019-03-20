package com.example.kotlinproject.database.database

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.kotlinproject.R
import kotlinx.android.synthetic.main.activity_new_user.*


class NewUserActivity : AppCompatActivity() {
    lateinit var mViewModel: ViewModel
    var user= emptyList<User>()

    companion object {
        const val E_MAIL="email"
        const val NAME="name"
        const val SURNAME="surname"
        const val PASSWORD="password"
        const val USER_EXIST=1442
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        button_register.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var replyIntent: Intent = Intent()
                if (TextUtils.isEmpty(etEmail.text) || TextUtils.isEmpty(etName.text) || TextUtils.isEmpty(etSurname.text)
                    || TextUtils.isEmpty(etPassword.text) || TextUtils.isEmpty(etPassword2.text)) {
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                } else
                {
                    if(isUserExist()){
                        setResult(USER_EXIST, replyIntent)
                    }
                    else{
                        replyIntent.putExtra(E_MAIL, etEmail.text.toString())
                        replyIntent.putExtra(NAME, etName.text.toString())
                        replyIntent.putExtra(SURNAME, etSurname.text.toString())
                        replyIntent.putExtra(PASSWORD, etPassword.text.toString())
                        setResult(Activity.RESULT_OK, replyIntent)
                    }
                }
                finish()
            }
        })

    }


    fun isUserExist():Boolean{
        mViewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        mViewModel.allUsers.observe(this, Observer { words ->
            words?.let { user=it }
        })

        if(user!=null){
            for (i in user) {
                if (i.user_email!!.equals(etEmail.text)) {
                    return true
                }
            }
            return false
        }
        else
        {
           return false
        }
    }
}
