package com.example.phonebook_assignment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonebook_assignment.db.Contact
import com.example.phonebook_assignment.db.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    private lateinit var contactToShow: Contact
    val contacts = repository.contacts

    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val mobileno = MutableLiveData<String>()

    fun saveContact(){
        val first_name = firstName.value!!
        val last_name = lastName.value!!
        val user_email = email.value!!
        val mobile = mobileno.value!!
        insert(Contact(0, first_name, last_name, mobile, user_email))
        firstName.value = ""
        lastName.value = ""
        email.value = ""
        mobileno.value = ""
    }

    fun updateContact(id: Int){
        val first_name = firstName.value!!
        val last_name = lastName.value!!
        val user_email = email.value!!
        val mobile = mobileno.value!!
        update(Contact(id, first_name, last_name, mobile, user_email))
        firstName.value = ""
        lastName.value = ""
        email.value = ""
        mobileno.value = ""
    }

    fun insert(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(contact)
        }
    }

    fun update(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(contact)
        }
    }

    fun delete(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(contact)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}