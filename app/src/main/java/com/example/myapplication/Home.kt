package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.launch

class Home : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val username_text = findViewById<TextView>(R.id.username)
        var listAnime = emptyList<Anime>()

        val database = AppDatabase.getDatabase(this)

        database.animes().getAll().observe(this, Observer{
            listAnime = it

            val adapter = AnimeAdapter(this, listAnime)

            lista.adapter = adapter

        })

        lifecycleScope.launch {
            val extras = intent.extras
            val username = extras?.getString("username")
            var response = database.userDao().getUserName(username.toString())

            username_text.setText(response)
        }




        lista.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@Home, Item::class.java)
            intent.putExtra("id", listAnime[position].idAnime)
            startActivity(intent)
        }

        val add_anime = findViewById<FloatingActionButton>(R.id.floatingActionButton_add_anime1)
        add_anime.setOnClickListener {
            val intent = Intent(this@Home, NewItem::class.java)
            startActivity(intent)
        }
    }
}