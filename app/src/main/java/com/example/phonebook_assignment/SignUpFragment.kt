package com.example.phonebook_assignment

import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.phonebook_assignment.databinding.FragmentSignUpBinding
import com.example.phonebook_assignment.db.ContactDatabase
import com.example.phonebook_assignment.db.ContactRepository
import com.example.phonebook_assignment.viewmodel.ContactViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var preferences:SharedPreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        val dao = ContactDatabase.getInstance(requireContext()).contactDAO
        val repository = ContactRepository(dao)
        val factory = ContactViewModelFactory(repository)
        contactViewModel = ViewModelProvider(this, factory)[ContactViewModel::class.java]
        binding.myViewModel = contactViewModel
        binding.lifecycleOwner = this

        GlobalScope.launch(Dispatchers.IO) {
            preferences = SharedPreferencesManager.getInstance(this@SignUpFragment.requireContext(),"123")
        }

        val mSpannableString = SpannableString("Login")
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        binding.loginTextView.text = mSpannableString

        binding.loginTextView.setOnClickListener{
            it.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        binding.SignUpButton.setOnClickListener {
            if(contactViewModel.signUpUsername.value == "" || contactViewModel.signUpPassword.value == ""){

                AlertDialog.Builder(this.requireContext())
                    .setMessage("Please Enter Some values")
                    .setPositiveButton("Yes") { dialog, which ->
                        // If the user confirms, go to the home page
                    }
                    .setNegativeButton("No") { dialog, which ->
                        // go back to ask login page
                        it.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                    }
                    .show()
            }
            else{
                val name = contactViewModel.signUpUsername.value
                val pass = contactViewModel.signUpPassword.value
                preferences.saveData(name!!,pass!!)
                var bundle = bundleOf("user_name" to name)

                AlertDialog.Builder(this.requireContext())
                    .setMessage("Are you sure you want to create an account?")
                    .setPositiveButton("Yes") { dialog, which ->
                        // If the user confirms, go to the home page
                        it.findNavController().navigate(R.id.action_signUpFragment_to_homeFragment,bundle)
                    }
                    .setNegativeButton("No") { dialog, which ->
                        // go back to ask login page
                        it.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                    }
                    .show()
            }
        }
        return binding.root
    }
}