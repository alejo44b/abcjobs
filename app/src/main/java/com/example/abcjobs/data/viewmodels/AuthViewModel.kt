package com.example.abcjobs.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.abcjobs.data.models.Token
import com.example.abcjobs.services.network.Security
import com.example.abcjobs.services.repositories.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AuthViewModel( application: Application) : AndroidViewModel(application){
    private val tokenRepository = TokenRepository(application)
    private var _token = MutableLiveData<Token>()

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: MutableLiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: MutableLiveData<Boolean>
        get() = _isNetworkErrorShown

    fun getToken(requestData: JSONObject){
        try {
            viewModelScope.launch (Dispatchers.Default) {
                withContext(Dispatchers.IO){
                    var data = tokenRepository.getToken(requestData)
                    _token.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }catch (e:Exception){
            _eventNetworkError.value=true
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}