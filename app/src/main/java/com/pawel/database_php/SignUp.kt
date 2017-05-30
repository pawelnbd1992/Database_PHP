package com.pawel.database_php

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {


    private var auth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()

        sign_up_email?.setText("lidner12@gmail.com")
        sign_up_password?.setText("szlendak1992")
        sign_up_repeat_password?.setText("szlendak1992")


        sign_up_button.setOnClickListener(View.OnClickListener {

            val email = sign_up_email.text.toString();
            val password = sign_up_password.text.toString();
            val password_repeat = sign_up_repeat_password.text.toString();


            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if (password != password_repeat) {
                Toast.makeText(applicationContext, "Passwords are different!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }


            auth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Użytkownik " + task.result.user.email!!.toString() + " zarejestrowany!", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    sign_up_email?.setText(task.exception!!.toString())

                }
            }
        })


    }
}
