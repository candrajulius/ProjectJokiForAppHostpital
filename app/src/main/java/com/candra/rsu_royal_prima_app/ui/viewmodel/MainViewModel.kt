package com.candra.rsu_royal_prima_app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.candra.rsu_royal_prima_app.data.LoginUser
import com.candra.rsu_royal_prima_app.helper.Utils
import kotlinx.coroutines.launch

class MainViewModel : ViewModel()
{
    suspend fun loginAccount(context: Context, name: String, password: String) = viewModelScope.launch{
         Utils.updateDataUser(
             context, LoginUser(username = name, password = password, isLogin = true)
         )
    }

    suspend fun logoutUser(context: Context) = viewModelScope.launch {
        Utils.updateDataUser(context,LoginUser("","", isLogin = false))
    }

    fun getUserLogin(context: Context) = Utils.getUser(context).asLiveData()
}
