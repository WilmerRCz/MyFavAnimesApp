package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.myapplication.utils.TilValidator
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class RegisterForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form)

        //Obtener Database
        val database = AppDatabase.getDatabase(this)

        val btn_addaccount = findViewById<Button>(R.id.btn_addaccount)
        val btn_cancel = findViewById<Button>(R.id.btn_cancel)
        val name_user = findViewById<TextInputLayout>(R.id.name_user)
        val lastname_user = findViewById<TextInputLayout>(R.id.lastname_user)
        val username_user = findViewById<TextInputLayout>(R.id.username_user)
        val email_user = findViewById<TextInputLayout>(R.id.email_user)
        val password_user = findViewById<TextInputLayout>(R.id.password_user2)


        btn_addaccount.setOnClickListener {
            val name_user_register = name_user.editText?.text.toString()
            val lastname_user_register = lastname_user.editText?.text.toString()
            val username_user_register = username_user.editText?.text.toString()
            val email_user_register = email_user.editText?.text.toString()
            val password_user_register = password_user.editText?.text.toString()

            val name_valid = TilValidator(name_user).required().isValid()
            val lastname_valid = TilValidator(lastname_user).required().isValid()
            val username_valid = TilValidator(username_user).required().isValid()
            val email_valid = TilValidator(email_user).required().email().isValid()
            val password_valid = TilValidator(password_user).required().isValid()


            val user = User(email_user_register,name_user_register, lastname_user_register, username_user_register, password_user_register)

            if (name_valid && lastname_valid && username_valid && email_valid && password_valid){
                lifecycleScope.launch{
                    val id = database.userDao().setUser(user)

                    if(id > 0){
                        Toast.makeText(applicationContext, "Usuario creado correctamente",
                            Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@RegisterForm, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }




        }
        btn_cancel.setOnClickListener {
            val intent = Intent(this@RegisterForm, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}