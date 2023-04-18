package com.candra.rsu_royal_prima_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.candra.rsu_royal_prima_app.data.Doctor
import com.candra.rsu_royal_prima_app.usecase.HospitalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(
    private val useCase: HospitalUseCase
): ViewModel()
{
    val showAllDoctor = useCase.showAllDoctor().asLiveData()

    suspend fun insert(doctor: Doctor) = viewModelScope.launch {
        useCase.insertDoctor(doctor)
    }
    suspend fun deleteDoctor(doctor: Doctor) = viewModelScope.launch {
        useCase.deleteDoctor(doctor)
    }
    suspend fun updateDoctor(doctor: Doctor) = viewModelScope.launch {
        useCase.updateDoctor(doctor)
    }
}