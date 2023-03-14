package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_anime.view.*


class AnimeAdapter(private val mContext: Context, private val listAnime: List<Anime>): ArrayAdapter<Anime>(mContext, 0, listAnime) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layout =  LayoutInflater.from(mContext).inflate(R.layout.item_anime, parent, false)

        val anime = listAnime[position]

        layout.name_anime_home.text = anime.title
        layout.text_category_anime_home.text = anime.category
        layout.text_date_anime_home.text = anime.date
        layout.text_description_anime_home.text = anime.description
        val imageUri = ImageController.getImageUri(mContext, anime.idAnime.toLong())
        layout.image_anime_home.setImageURI(imageUri)




        return layout
    }
}