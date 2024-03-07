package com.example.phonebook_assignment.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDAO {

    @Insert
    fun insertContact(contact: Contact)

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("DELETE FROM contact_list")
    fun deleteAll()

    @Query("SELECT * FROM contact_list")
    fun getAllContacts(): LiveData<List<Contact>>

}