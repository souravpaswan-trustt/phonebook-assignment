package com.example.phonebook_assignment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.phonebook_assignment.databinding.FragmentContactDetailsBinding
import com.example.phonebook_assignment.db.Contact
import com.example.phonebook_assignment.db.ContactDatabase
import com.example.phonebook_assignment.db.ContactRepository
import com.example.phonebook_assignment.viewmodel.ContactViewModel

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

        binding.showDetailsNameTextView.text = first_name + " " + last_name

        binding.showDetailsNumberTextView.text = mobileno
        binding.showDetailsEmailTextView.text = email

        binding.editContactFAB.setOnClickListener {
            val bundle = bundleOf(
                "contact_key" to primaryKey,
                "first_name" to first_name,
                "last_name" to last_name,
                "email" to email,
                "mobileno" to mobileno
            )
            it.findNavController()
                .navigate(R.id.action_contactDetailsFragment_to_addContactFragment, bundle)
        }

        binding.deleteContactFAB.setOnClickListener {

            val dialog = AlertDialog.Builder(this@ContactDetailsFragment.requireContext())
            dialog.setMessage("Are you sure you want to delete?")
            dialog.setTitle("Delete Contact")
            dialog.setPositiveButton("Yes") { _, _ ->
                contactViewModel.delete(Contact(primaryKey, first_name!!, last_name!!, mobileno!!, email!!))
                it.findNavController().navigate(R.id.action_contactDetailsFragment_to_homeFragment2)
                Toast.makeText(this@ContactDetailsFragment.requireContext(),
                    "Contact deleted successfully", Toast.LENGTH_SHORT).show()

            }
            dialog.setNegativeButton("No"){_,_ ->}
            val dialogBox = dialog.create()
            dialogBox.show()
        }
        return binding.root
    }
}