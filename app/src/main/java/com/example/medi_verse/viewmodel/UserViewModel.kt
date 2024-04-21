package com.example.medi_verse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medi_verse.data.remote.model.RegisterRequest
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class UserViewModel (private val remoteRepo: RemoteRepo): ViewModel() {
    // Initialize _registerState with an initial value
    private val _registerState = MutableStateFlow<Result<String>>(Result.Loading())
    // Convert _registerState to a SharedFlow
    val registerState: SharedFlow<Result<String>> = _registerState

    fun createUser(
        name: String,
        username : String,
        email: String,
        password: String
    )=viewModelScope.launch{
        _registerState.emit(Result.Loading())
        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || username.isEmpty()){
            _registerState.emit(Result.Error("Some Fields are Empty" , ""))
            return@launch
        }
        if(!isEmailValid(email)){
            _registerState.emit(Result.Error("Email is not valid!", ""))
            return@launch
        }

        val newUser = RegisterRequest(
            name = name,
            username = username,
            email = email,
            password = password
        )
        _registerState.emit(remoteRepo.createUser(newUser))
    }

    private fun isEmailValid(email :String):Boolean{
        var regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
        val pattern  = Pattern.compile(regex)
        return (email.isNotEmpty() && pattern.matcher(email).matches())
    }


}