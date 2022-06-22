package com.woocommerce.android.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.woocommerce.android.viewmodel.ScopedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginNoJetpackViewModel @Inject constructor(
    savedState: SavedStateHandle,
    private val loginNoJetpackRepository: LoginNoJetpackRepository
) : ScopedViewModel(savedState) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isJetpackAvailable = MutableLiveData<Boolean>()
    val isJetpackAvailable: LiveData<Boolean> = _isJetpackAvailable

    fun verifyJetpackAvailable(siteAddress: String) {
        launch {
            _isLoading.value = true
            _isJetpackAvailable.value = loginNoJetpackRepository.verifyJetpackAvailable(siteAddress)
            _isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        loginNoJetpackRepository.onCleanup()
    }
}
