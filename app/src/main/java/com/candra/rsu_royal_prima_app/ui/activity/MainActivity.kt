package com.candra.rsu_royal_prima_app.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.candra.rsu_royal_prima_app.R
import com.candra.rsu_royal_prima_app.databinding.ActivityMainBinding
import com.candra.rsu_royal_prima_app.helper.Constant
import com.candra.rsu_royal_prima_app.helper.Utils
import com.candra.rsu_royal_prima_app.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.setToolbar(Constant.RSU,Constant.TITLE_LOGIN,supportActionBar,1)
        binding.btnLogin.setOnClickListener {
            setValidationUser()
        }
    }

    private fun setValidationUser()
    {
        binding.apply {
            val username = textUsername.editText?.text.toString().lowercase()
            val password = textPassword.editText?.text.toString().lowercase()

            if (username.isEmpty() && password.isEmpty()){
                Utils.makeToast(this@MainActivity,getString(R.string.toast_empty_data))
            }else if (username != Constant.USERNAME_LOGIN || password != Constant.PASSWORD_LOGIN)
            {
                Utils.makeToast(this@MainActivity,getString(R.string.toast_username_and_password_wrong))
            }else{
                lifecycleScope.launch {
                    mainViewModel.loginAccount(this@MainActivity,username,password).also {
                        toHomeActivity()
                    }
                }
            }
        }
    }

    private fun toHomeActivity(){
        startActivity(Intent(this@MainActivity,HomeActivity::class.java)).also { finish() }
    }


}