package com.example.phonebook_assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.phonebook_assignment.databinding.FragmentContactDetailsBinding
import com.example.phonebook_assignment.db.Contact
import com.example.phonebook_assignment.db.ContactDatabase
import com.example.phonebook_assignment.db.ContactRepository

class ContactDetailsFragment : Fragment() {

    private lateinit var binding: FragmentContactDetailsBinding
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contact_details, container, false)
        val dao = ContactDatabase.getInstance(requireContext()).contactDAO
        val repository = ContactRepository(dao)
        val factory = ContactViewModelFactory(repository)
        contactViewModel = ViewModelProvider(this, factory)[ContactViewModel::class.java]
        binding.myViewModel = contactViewModel
        binding.lifecycleOwner = this


        val primaryKey = requireArguments().getInt("contact_key")
        val first_name = requireArguments().getString("first_name")
        val last_name = requireArguments().getString("last_name")
        val mobileno = requireArguments().getString("mobileno")
        val email = requireArguments().getString("email")

        binding.showDetailsNameTextView.text = first_name +" " + last_name

        binding.showDetailsNumberTextView.text = mobileno
        binding.showDetailsEmailTextView.text = email

        binding.editContactFAB.setOnClickListener {

        }

        binding.deleteContactFAB.setOnClickListener {
            try {
                val ob: Contact
                ob = Contact(primaryKey, first_name!!, last_name!!, mobileno!!, email!!)
                contactViewModel.delete(ob)
                it.findNavController().navigate(R.id.action_contactDetailsFragment_to_homeFragment2)
                Toast.makeText(this@ContactDetailsFragment.requireContext(),
                    "Contact deleted successfully",
                    Toast.LENGTH_SHORT).show()
            } catch(e : Exception){
                Toast.makeText(this@ContactDetailsFragment.requireContext(), e.toString(),
                    Toast.LENGTH_LONG).show()
            }

        }

        return binding.root
    }
}