package com.example.phonebook_assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.phonebook_assignment.databinding.FragmentLoginBinding
import com.example.phonebook_assignment.db.ContactDatabase
import com.example.phonebook_assignment.db.ContactRepository
import com.example.phonebook_assignment.viewmodel.ContactViewModel
import com.example.phonebook_assignment.viewmodel.ExitAppViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var preference:SharedPreferencesManager
    private lateinit var exitViewMOdel: ExitAppViewModel
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val dao = ContactDatabase.getInstance(requireContext()).contactDAO
        val repository = ContactRepository(dao)
        val factory = ContactViewModelFactory(repository)
        contactViewModel = ViewModelProvider(this, factory)[ContactViewModel::class.java]
        binding.myViewModel = contactViewModel
        binding.lifecycleOwner = this

        preference = SharedPreferencesManager.getInstance(this@LoginFragment.requireContext(),"123")
        exitViewMOdel = ViewModelProvider(this).get(ExitAppViewModel::class.java)

        binding.loginButton.setOnClickListener {
            val name = contactViewModel.loginUsername.value
            val pass = contactViewModel.loginPassword.value
            var bundle = bundleOf("user_name" to name)

            if (name == ""|| pass==""){
                AlertDialog.Builder(this.requireContext())
                    .setMessage("Please enter Values in the Field")
                    .setPositiveButton("Yes") { dialog, which ->
                        // If the user confirms, finish the activity to exit the app

                    }
                    .setNegativeButton("No") { dialog, which ->
                        // go back to ask login page
                        it.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment2)
                    }
                    .show()
            }
            else if(pass.equals(preference.getData(name!!,""))) {
                it.findNavController().navigate(R.id.action_loginFragment_to_homeFragment,bundle)
            }
            else{
//                Toast.makeText(this@LoginFragment.requireContext(),"User Not Found",Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(this.requireContext())
                    .setMessage("You don't have an account, Create one?")
                    .setPositiveButton("Yes") { dialog, which ->
                        // If the user confirms, finish the activity to exit the app
                        it.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment2)
                    }
                    .setNegativeButton("No") { dialog, which ->
                        activity?.finish()
                    }
                    .show()
                //it.findNavController().navigate(R.id.action_loginFragment_to_newUserFragment)
            }
        }

        return binding.root
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
//        val savedUsername = preferenceManager.getSavedUsername()
//        val savedPassword = preferenceManager.getSavedPassword()

        val savedUsername = "sourav"
        val savedPassword = "1234"

        return username.equals(savedUsername) && password.equals(savedPassword)
    }
}