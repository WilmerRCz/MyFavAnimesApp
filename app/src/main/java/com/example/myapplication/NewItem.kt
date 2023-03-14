package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.utils.TilValidator
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_new_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class NewItem : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")

    private val SELECT_ACTIVITY = 50
    private var imageUri: Uri? = null

    private fun Date.dateToString(format: String): String {
        //simple date formatter
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())

        //return the formatted date string
        return dateFormatter.format(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item)

        //Obtener Database
        val database = AppDatabase.getDatabase(this)

        //Creación de Spinner Category
        val list_category_anime = listOf("Shonen", "Escolar", "Shojou", "Acción", "Deportes", "Seinen", "Yaoi", "Aventura", "Ecchi", "Terror", "Romance", "Fantasia")
        val spinner_category_animes = findViewById<Spinner>(R.id.spinner_category_anime)
        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, list_category_anime)
        spinner_category_animes.adapter = arrayAdapter

        val btn_addanime = findViewById<Button>(R.id.btn_addanime)
        val btn_cancel = findViewById<Button>(R.id.btn_cancel)
        val name_anime = findViewById<TextInputLayout>(R.id.name_anime)
        val description_anime = findViewById<TextInputLayout>(R.id.description_anime)

        var idAnime: Int? = null
        if(intent.hasExtra("anime")){
            val anime = intent.extras?.getSerializable("anime") as Anime

            edit_title.setText(anime.title)
            edit_description.setText(anime.description)
            idAnime = anime.idAnime

            val imageUri = ImageController.getImageUri(this, idAnime.toLong())
            image_anime.setImageURI(imageUri)

        }


        btn_addanime.setOnClickListener {
            val name_anime_item = name_anime.editText?.text.toString()
            val category_anime_item = spinner_category_animes.selectedItem.toString()
            val description_anime_item = description_anime.editText?.text.toString()
            //Consiguiento Fecha Actual
            //val date_actual = "18-10-2022"
            val timestampt = Date()
            val date_actual = timestampt.dateToString("dd-MM-yyyy")


            val anime = Anime(name_anime_item,category_anime_item,date_actual, description_anime_item, R.drawable.ic_launcher_background)


            val name_anime_valid = TilValidator(name_anime).required().isValid()
            val description_anime_valid = TilValidator(description_anime).required().isValid()

            if(name_anime_valid && description_anime_valid){
                if(idAnime != null){
                    CoroutineScope(Dispatchers.IO).launch {
                        anime.idAnime = idAnime
                        database.animes().update(anime)
                        imageUri?.let {
                            val intent = Intent()
                            intent.data = it
                            setResult(Activity.RESULT_OK, intent)
                            ImageController.saveImage(this@NewItem, idAnime.toLong(), it)
                        }
                        this@NewItem.finish()
                    }
                }else{
                    CoroutineScope(Dispatchers.IO).launch {
                        val id = database.animes().insertAll(anime)[0]
                        imageUri?.let {
                            ImageController.saveImage(this@NewItem, id, it)
                        }
                        this@NewItem.finish()
                }}
            }
        }

        btn_cancel.setOnClickListener {
            this@NewItem.finish()
        }

        image_anime.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)



        }


    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imageUri = data!!.data

            image_anime.setImageURI(imageUri)

        }
    }






}