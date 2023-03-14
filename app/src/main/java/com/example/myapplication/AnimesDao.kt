package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AnimesDao {
    @Query("SELECT * FROM animes")
    fun getAll(): LiveData<List<Anime>>

    @Query("SELECT * FROM animes WHERE idAnime = :id")
    fun get(id: Int): LiveData<Anime>

    @Insert
    fun insertAll(vararg animes: Anime): List<Long>

    @Update
    fun update(anime: Anime)

    @Delete
    fun delete(anime: Anime)
}