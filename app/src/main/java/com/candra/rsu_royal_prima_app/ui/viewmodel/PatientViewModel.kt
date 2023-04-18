package com.candra.rsu_royal_prima_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.candra.rsu_royal_prima_app.data.Patient
import com.candra.rsu_royal_prima_app.usecase.HospitalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val useCase: HospitalUseCase
): ViewModel()
{
    val showAllPatient = useCase.showAllPatient().asLiveData()
    suspend fun insertPatient(patient: Patient) = viewModelScope.launch {
        useCase.insertPatient(patient)
    }
    suspend fun updatePatient(patient: Patient) = viewModelScope.launch {
        useCase.updatePatient(patient)
    }
    suspend fun deletePatient(patient: Patient) = viewModelScope.launch {
        useCase.deletePatient(patient)
    }
}