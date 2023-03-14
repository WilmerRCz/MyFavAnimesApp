package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.utils.TilValidator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Obtener Database
        val database = AppDatabase.getDatabase(this)

        val btn_register = findViewById<Button>(R.id.btn_register)
        val btn_init = findViewById<Button>(R.id.btn_init)
        val email_address = findViewById<TextInputLayout>(R.id.email_user)
        val password = findViewById<TextInputLayout>(R.id.password_user)




        btn_init.setOnClickListener {
            val email_user = email_address.editText?.text.toString()
            val password_user = password.editText?.text.toString()

            val email_valid = TilValidator(email_address).required().email().isValid()
            val password_valid = TilValidator(password).required().isValid()


            if(email_valid && password_valid){

                lifecycleScope.launch {
                    val response = database.userDao().login(email_user, password_user)

                    if(response.size == 1){
                        Toast.makeText(applicationContext, "Acceso correcto", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, Home::class.java)
                        intent.putExtra("username", email_user)
                        startActivity(intent)
                        finish()
                    }else{
                        email_address.error = "Acceso incorrecto"
                        password.error = "Acceso incorrecto"
                        Toast.makeText(applicationContext, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btn_register.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterForm::class.java)
            startActivity(intent)
        }

    }
}