package com.farenas.challengeml.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel /*@ViewModelInject constructor()*/ : ViewModel() {

    private val _splashTimeOut = MutableLiveData<Boolean>()
    val splashTimeOut: LiveData<Boolean> = _splashTimeOut

    init {
        viewModelScope.launch {
            delay(2000)
            _splashTimeOut.postValue(true)
        }
    }
}