package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "animes")
class Anime(val title: String,
            val category: String,
            val date: String,
            val description: String,
            val image: Int,
            @PrimaryKey(autoGenerate = true)
            var idAnime: Int = 0
) : Serializable {
}