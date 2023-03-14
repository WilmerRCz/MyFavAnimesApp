package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User {
    @PrimaryKey
    var email: String
    var firstName: String? = null
    var lastName: String? = null
    var userName: String? = null
    var password: String? = null

    constructor(email: String, firstName: String, lastName: String, userName: String, password: String){
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
        this.userName = userName
        this.password = password
    }
}