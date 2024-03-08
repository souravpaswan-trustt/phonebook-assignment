package com.example.phonebook_assignment

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.phonebook_assignment.databinding.ActivityMainBinding
import com.example.phonebook_assignment.viewmodel.ExitAppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var exitAppViewModel: ExitAppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        exitAppViewModel = ViewModelProvider(this).get(ExitAppViewModel::class.java)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onBackPressed() {
        // Check if the current destination is the home fragment
        var exit = false
        lifecycleScope.launch(Dispatchers.IO) {
            exit = exitAppViewModel.getExitValue()
        }
        if(exit == true){
            finish()
        }
        if (navController.currentDestination?.id == R.id.homeFragment) {
            // If on the home fragment, finish the activity to exit the app
            AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes") { dialog, which ->
                    // If the user confirms, finish the activity to exit the app
                    finish()
                }
                .setNegativeButton("No", null) // Do nothing if the user cancels
                .show()
        } else {
            // If not on the home fragment, let the system handle back button behavior
            super.onBackPressed()
        }
    }
}