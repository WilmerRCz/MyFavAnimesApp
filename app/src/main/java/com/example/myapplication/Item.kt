package com.example.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_item.*
import kotlinx.android.synthetic.main.activity_new_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Item : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var anime: Anime
    private lateinit var animeLiveData: LiveData<Anime>
    private val EDIT_ACTIVITY = 49


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        database = AppDatabase.getDatabase(this)

        val btn_edit = findViewById<ImageView>(R.id.image_edit_item)
        val btn_delete_item = findViewById<ImageView>(R.id.imageDeleteItem)
        val btn_arrow_back = findViewById<ImageView>(R.id.imageArrowBack)
        text_description_anime_item.movementMethod = ScrollingMovementMethod()
        val builder = AlertDialog.Builder(this)


        val idAnime = intent.getIntExtra("id", 0)

        val imageUri = ImageController.getImageUri(this, idAnime.toLong())
        image_anime_item.setImageURI(imageUri)

        animeLiveData = database.animes().get(idAnime)
        animeLiveData.observe(this, Observer{
            anime = it

            text_name_anime_item.text = anime.title
            text_category_anime_item.text = anime.category
            text_date_anime_item.text = anime.date
            text_description_anime_item.text = anime.description

        })



        btn_edit.setOnClickListener {
            val intent = Intent(this@Item, NewItem::class.java)
            intent.putExtra("anime", anime)
            resultLauncher.launch(intent)

        }
        btn_arrow_back.setOnClickListener {
            val intent = Intent(this@Item, Home::class.java)
            startActivity(intent)
        }

        btn_delete_item.setOnClickListener {
            builder.setTitle("Estas seguro?")
            builder.setMessage("¿Estas seguro de eliminar este anime para siempre?")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.yes, Toast.LENGTH_SHORT).show()

                animeLiveData.removeObservers(this)
                CoroutineScope(Dispatchers.IO).launch {
                    database.animes().delete(anime)
                    ImageController.deleteImage(this@Item, anime.idAnime.toLong())
                    this@Item.finish()

                }
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }

            builder.show()
        }

    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            image_anime_item.setImageURI(data!!.data)

        }
    }


}

