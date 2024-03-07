package com.example.phonebook_assignment

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonebook_assignment.db.Contact
import com.example.phonebook_assignment.db.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    private val statusMessage = MutableLiveData<Event<String>>()
    val contacts = repository.contacts

    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val mobileno = MutableLiveData<String>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    fun saveContact(){
        if(validate()) {
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
    }

    fun updateContact(id: Int){
        if(validate()) {
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
    }

    fun validate(): Boolean{
        var valid = false
        if(firstName.value == null || firstName.value == ""){
            statusMessage.value = Event("Please enter first name!")
        }
        else if(lastName.value == null || lastName.value == ""){
            statusMessage.value = Event("Please enter last name!")
        }
        else if(mobileno.value == null || mobileno.value == ""){
            statusMessage.value = Event("Please enter phone number!")
        }
        else if(email.value == null || email.value == ""){
            statusMessage.value = Event("Please enter email!")
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches()){
            statusMessage.value = Event("Please enter a correct email address!")
        }
        else{
            statusMessage.value = Event("success")
            valid = true
        }
        return valid
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