package com.candra.rsu_royal_prima_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.candra.rsu_royal_prima_app.data.Admisi
import com.candra.rsu_royal_prima_app.data.Payment
import com.candra.rsu_royal_prima_app.usecase.HospitalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdmisiViewModel @Inject constructor(
    private val useCase: HospitalUseCase
): ViewModel()
{
    val showAllAdmisiPatient = useCase.showAllAdmisiPatient().asLiveData()

    fun showAllAdmisiWhereStatusQueue(status_queue: String) =
        useCase.showAllAdmisiWhereStatusQueue(status_queue).asLiveData()
    suspend fun insertAdmisi(admisi: Admisi) = viewModelScope.launch {
        useCase.insertAdmisi(admisi)
    }
    suspend fun deleteAdmisi(admisi: Admisi) = viewModelScope.launch {
        useCase.deleteAdmisi(admisi)
    }
    suspend fun updateAdmisi(admisi: Admisi) = viewModelScope.launch {
        useCase.updateAdmisi(admisi)
    }

    suspend fun insertPayment(payment: Payment) = viewModelScope.launch {
        useCase.insertPayment(payment)
    }
}