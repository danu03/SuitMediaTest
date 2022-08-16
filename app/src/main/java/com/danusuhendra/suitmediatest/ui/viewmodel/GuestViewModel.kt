package com.danusuhendra.suitmediatest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danusuhendra.suitmediatest.model.response.Data
import com.danusuhendra.suitmediatest.repository.GuestDbRepository
import com.danusuhendra.suitmediatest.repository.GuestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuestViewModel @Inject constructor(
    private var repository: GuestRepository,
    private var guestDbRepository: GuestDbRepository
) : ViewModel() {

    private val _guest by lazy { MutableLiveData<List<Data?>>() }
    private val _loading by lazy { MutableLiveData<Boolean>() }
    private val _error by lazy { MutableLiveData<String>() }
    val curatedPhoto: LiveData<List<Data?>> = _guest
    val loading: LiveData<Boolean> = _loading
    val error: LiveData<String> = _error

    fun getGuest() {
        _loading.value = true
        repository.getGuest({
            _guest.value = it?.data
            viewModelScope.launch {
                guestDbRepository.insert(it?.data as List<Data>?)
            }
            _loading.value = false
        }, {
            _error.value = it.message
            _loading.value = false
        })
    }

    fun getGuestFromDb() {
        _loading.value = true
        viewModelScope.launch {
            guestDbRepository.getGuestDb { guest ->
                _guest.value = guest
            }
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }
}