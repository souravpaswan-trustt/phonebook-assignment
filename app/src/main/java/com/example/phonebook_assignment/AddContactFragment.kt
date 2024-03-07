package com.example.phonebook_assignment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.phonebook_assignment.ContactViewModel
import com.example.phonebook_assignment.ContactViewModelFactory
import com.example.phonebook_assignment.databinding.FragmentAddContactBinding
import com.example.phonebook_assignment.db.Contact
import com.example.phonebook_assignment.db.ContactDatabase
import com.example.phonebook_assignment.db.ContactRepository
import java.lang.Exception

class AddContactFragment : Fragment() {

    private lateinit var binding: FragmentAddContactBinding
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_contact, container, false)
        val dao = ContactDatabase.getInstance(requireContext()).contactDAO
        val repository = ContactRepository(dao)
        val factory = ContactViewModelFactory(repository)
        contactViewModel = ViewModelProvider(this, factory)[ContactViewModel::class.java]
        binding.myViewModel = contactViewModel
        binding.lifecycleOwner = this

        try {
            val primaryKey = requireArguments().getInt("contact_key")
            val first_name = requireArguments().getString("first_name")
            val last_name = requireArguments().getString("last_name")
            val mobileno = requireArguments().getString("mobileno")
            val email = requireArguments().getString("email")
            contactViewModel.firstName.value = first_name
            contactViewModel.lastName.value = last_name
            contactViewModel.email.value = email
            contactViewModel.mobileno.value = mobileno

            binding.saveContactButton.setOnClickListener {
                contactViewModel.updateContact(primaryKey)
                contactViewModel.contacts.observe(viewLifecycleOwner, Observer {
                    Log.i("MYTAG", it.toString())
                })
                var flag = ""
                contactViewModel.message.observe(viewLifecycleOwner, Observer {
                    it.getContentIfNotHandled()?.let{
                        flag = it
                    }
                })
                if(flag.equals("success")) {
                    Toast.makeText(this@AddContactFragment.requireContext(),
                        "Contact updated sucessfully!", Toast.LENGTH_SHORT).show()
                    it.findNavController().navigate(R.id.action_addContactFragment_to_homeFragment)
                } else{
                    Toast.makeText(this@AddContactFragment.requireContext(),
                        flag, Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            binding.saveContactButton.setOnClickListener {
                contactViewModel.saveContact()
                contactViewModel.contacts.observe(viewLifecycleOwner, Observer {
                    Log.i("MYTAG", it.toString())
                })
                var flag = ""
                contactViewModel.message.observe(viewLifecycleOwner, Observer {
                    it.getContentIfNotHandled()?.let{
                        flag = it
                    }
                })
                if(flag.equals("success")) {
                    Toast.makeText(this@AddContactFragment.requireContext(),
                        "Contact added sucessfully!", Toast.LENGTH_SHORT).show()
                    it.findNavController().navigate(R.id.action_addContactFragment_to_homeFragment)
                } else {
                    Toast.makeText(
                        this@AddContactFragment.requireContext(),
                        flag, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.discardButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_addContactFragment_to_homeFragment)
        }
        return binding.root
    }
}