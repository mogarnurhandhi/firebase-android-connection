package com.example.firebase_databaseproject

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class LoginAct : AppCompatActivity() {
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var btnLogin: Button
    private lateinit var register: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        register = findViewById(R.id.textRegister)
        register.setOnClickListener {
            startActivity(Intent(applicationContext, RegistrasiAct::class.java))
        }

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            startActivity(Intent(this@LoginAct, MainActivity::class.java))
            finish()
        }

        inputEmail = findViewById(R.id.email)
        inputPassword = findViewById(R.id.password)
        progressBar = findViewById(R.id.progressBar)
        btnLogin = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Masukan email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Masukan password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@LoginAct, OnCompleteListener<AuthResult> { task ->
                    progressBar.visibility = View.GONE
                    if (!task.isSuccessful) {
                        if (password.length < 6) {
                            inputPassword.error = "password harus 6 karakter"
                        } else {
                            Toast.makeText(this@LoginAct, "maaf login gagal", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val intent = Intent(this@LoginAct, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                })
        }
    }
}