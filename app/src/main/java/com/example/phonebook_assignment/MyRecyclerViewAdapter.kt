package com.example.phonebook_assignment

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.phonebook_assignment.databinding.ListItemBinding
import com.example.phonebook_assignment.db.Contact

class MyRecyclerViewAdapter(private val contactList: List<Contact>,
    private val clickListener:(Contact) -> Unit):
    RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(contactList[position], clickListener)
    }
}

class MyViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(contact: Contact, clickListener:(Contact) -> Unit){
        binding.firstNameTextView.text = contact.firstName
        binding.lastNameTextView.text = contact.lastName
        binding.listItemLayout.setOnClickListener{
            clickListener(contact)
        }
    }
}