package com.example.phonebook_assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async

class ExitAppViewModel: ViewModel() {
    val exit = MutableLiveData<Boolean>(false)
    suspend fun getExitValue(): Boolean {
        var deferred = viewModelScope.async {
            exit.value
        }.await()
        return deferred!!
    }
}