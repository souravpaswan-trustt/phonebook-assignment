package com.example.phonebook_assignment.db

class ContactRepository(private val dao: ContactDAO) {
    val contacts = dao.getAllContacts()

    fun insert(contact: Contact){
        return dao.insertContact(contact)
    }

    fun update(contact: Contact){
        return dao.updateContact(contact)
    }

    fun delete(contact: Contact){
        return dao.deleteContact(contact)
    }

    fun deleteAll(){
        return dao.deleteAll()
    }
}