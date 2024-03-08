package com.example.phonebook_assignment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phonebook_assignment.databinding.FragmentHomeBinding
import com.example.phonebook_assignment.db.Contact
import com.example.phonebook_assignment.db.ContactDatabase
import com.example.phonebook_assignment.db.ContactRepository
import com.example.phonebook_assignment.viewmodel.ContactViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val dao = ContactDatabase.getInstance(requireContext()).contactDAO
        val repository = ContactRepository(dao)
        val factory = ContactViewModelFactory(repository)
        contactViewModel = ViewModelProvider(this,factory)[ContactViewModel::class.java]
        binding.myViewModel = contactViewModel
        binding.lifecycleOwner = this

        initReyclerView()

        binding.addContactFAB.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addContactFragment)
        }
        return binding.root
    }

    private fun initReyclerView() {
        binding.contactRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        displayContactList()
    }

    private fun displayContactList(){
        contactViewModel.contacts.observe(viewLifecycleOwner, Observer {
            Log.i("MYTAG",it.toString())
            binding.contactRecyclerView.adapter = MyRecyclerViewAdapter(it, {selectedItem:Contact->listItemClicked(selectedItem)})
        })
    }

    private fun listItemClicked(contact: Contact){
        try {
            val bundle = bundleOf("contact_key" to contact.id,
                "first_name" to contact.firstName,
                "last_name" to contact.lastName,
                "email" to contact.email,
                "mobileno" to contact.number)

            requireView().findNavController()
                .navigate(R.id.action_homeFragment_to_contactDetailsFragment2, bundle)

        } catch (e: Exception){
            Toast.makeText(this@HomeFragment.requireContext(), e.toString(),
                Toast.LENGTH_LONG).show()
        }
    }
}