package com.example.coffeeorderapp.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeorderapp.data.db.UserRepository
import com.example.coffeeorderapp.data.db.User // Eğer User sınıfınız varsa.
import com.example.coffeeorderapp.data.local.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess

    fun login(email: String) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                userRepository.getUserByEmail(email.trim())
            }

            if (user != null) {
                println("Kullanıcı mevcut, giriş başarılı.")
                dataStoreManager.setLoggedIn(true)
                dataStoreManager.saveEmail(email)
                _loginSuccess.value = true
            } else {
                _loginSuccess.value = false
                createNewUser(email.trim())
            }
        }
    }

    private suspend fun createNewUser(email: String) {
        withContext(Dispatchers.IO) {
            try {
                val newUser = User(email = email)
                userRepository.addUser(newUser)
                _loginSuccess.value = true
            } catch (e: Exception) {
                Log.e("createNewUser", "Hata: ${e.message}")
            }
        }
    }

    fun resetLoginState() {
        _loginSuccess.value = null
    }
}
