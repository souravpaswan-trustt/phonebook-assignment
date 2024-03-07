package com.example.phonebook_assignment.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_list")
data class Contact (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    val id : Int,

    @ColumnInfo(name = "first_name")
    var firstName : String,

    @ColumnInfo(name = "last_name")
    var lastName : String,

    @ColumnInfo(name = "contact_number")
    val number : String,

    @ColumnInfo(name = "contact_email")
    var email : String
)