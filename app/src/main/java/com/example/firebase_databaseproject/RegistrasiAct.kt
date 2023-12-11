package com.example.firebase_databaseproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.FirebaseDatabase

class RegistrasiAct : AppCompatActivity() {
    private val PASSWORD_PATTERN =
        Regex(
            "^(?=.*[0-9])" +         //paling sedikit 1 digit
                    "(?=.*[a-z])" +         //paling sedikit 1 lowercase karakter
                    "(?=.*[A-Z])" +         //paling sedikit 1 upercase karakter
                    "(?=.*[@#$%^&+=])" +    //paling sedikit 1 spesial karakter
                    "(?=.*[\\s+$])." +      //tanpa spasi
                    "{6,}" +                //paling sedikit 6 upercase karakter
                    "$"
        )

    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var inputNama: EditText
    private lateinit var inputTanggal: EditText
    private lateinit var inputjnsKelamin: EditText
    private lateinit var inputNoTelepon: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var btnRegis: Button
    private lateinit var login: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi)

        auth = FirebaseAuth.getInstance()
        inputNama = findViewById(R.id.nama1)
        inputTanggal = findViewById(R.id.tanggal1)
        inputjnsKelamin = findViewById(R.id.jnsKelamin1)
        inputNoTelepon = findViewById(R.id.noTelepon)
        inputEmail = findViewById(R.id.email1)
        inputPassword = findViewById(R.id.password1)
        progressBar = findViewById(R.id.progressBar1)
        btnRegis = findViewById(R.id.btn_register)

        btnRegis.setOnClickListener {
            val nama = inputNama.text.toString()
            val tgl = inputTanggal.text.toString()
            val jk = inputjnsKelamin.text.toString()
            val noHp = inputNoTelepon.text.toString()
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            if (TextUtils.isEmpty(nama)) {
                Toast.makeText(applicationContext, "Masukan nama", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (!nama.matches("[a-zA-Z]+".toRegex())) {
                Toast.makeText(applicationContext, "Masukan hanya alfabet", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(tgl)) {
                Toast.makeText(applicationContext, "Masukan tanggal lahir", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (!tgl.matches("\\d{4}-\\d{2}-\\d{2}".toRegex())) {
                Toast.makeText(
                    applicationContext,
                    "Masukan hanya angka format YYYY-MM-DD",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(jk)) {
                Toast.makeText(applicationContext, "Masukan jenis kelamin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (!jk.matches("(?:pria|Pria|wanita|Wanita|PRIA|WANITA|Not prefer to say)$".toRegex())) {
                Toast.makeText(
                    applicationContext,
                    "Masukan hanya alfabet format pria/wanita/PRIA/WANITA/",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(noHp)) {
                Toast.makeText(applicationContext, "Masukan nomor telepon", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (!noHp.matches("^[+][0-9]{10,13}$".toRegex())) {
                Toast.makeText(
                    applicationContext,
                    "Masukan hanya angka format +62xxxxxxxxxxx",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Masukan email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(applicationContext, "Masukan email dengan valid", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Masukan password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (password.length < 6) {
                Toast.makeText(
                    applicationContext,
                    "Password harus lebih dari 6 karakter",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@RegistrasiAct) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful){
                        val firebaseUser = FirebaseAuth.getInstance().currentUser

                        FirebaseDatabase.getInstance().getReference("Pengguna/" +
                                FirebaseAuth.getInstance().currentUser!!.uid).
                        setValue(Users(inputNama.text.toString(),
                            inputTanggal.text.toString(),
                            inputjnsKelamin.text.toString(),
                            inputNoTelepon.text.toString(),
                            inputEmail.text.toString(),
                            inputPassword.text.toString()))

                        Toast.makeText(applicationContext, "Registrasi berhasil", Toast.LENGTH_SHORT).show()

                        startActivity(Intent(applicationContext, LoginAct::class.java))
                    } else{
                        Toast.makeText(
                            this@RegistrasiAct,"maaf registrasi gagal", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        login = findViewById(R.id.textLogin)
        login.setOnClickListener {
            startActivity(Intent(applicationContext, LoginAct::class.java))
        }
    }
}