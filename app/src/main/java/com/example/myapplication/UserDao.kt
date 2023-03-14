package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM User WHERE email = :email")
    suspend fun getUser(email: String): User

    @Query("SELECT userName FROM User WHERE email = :email")
    suspend fun getUserName(email: String): String

    @Query("SELECT * FROM User WHERE email= :email AND password = :password")
    suspend fun login(email: String, password: String): List<User>

    @Insert
    suspend fun setUser(user: User):Long

    @Query("UPDATE User SET firstName = :firstName, lastName = :lastName, password = :password WHERE email = :email")
    suspend fun updateUser(firstName: String, lastName: String, email: String, password: String): Int

    @Query("DELETE FROM User WHERE email= :email")
    suspend fun deleteUser(email: String)
}