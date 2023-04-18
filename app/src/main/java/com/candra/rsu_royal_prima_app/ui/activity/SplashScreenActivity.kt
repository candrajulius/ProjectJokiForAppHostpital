package com.candra.rsu_royal_prima_app.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import com.candra.rsu_royal_prima_app.R
import com.candra.rsu_royal_prima_app.databinding.ActivitySplashScreenBinding
import com.candra.rsu_royal_prima_app.helper.Constant
import com.candra.rsu_royal_prima_app.ui.viewmodel.MainViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.textTitleSplashscreen.apply {
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            text = getString(R.string.title_splashscreen).uppercase()
        }
        checkUserLoginOrNot()
    }

    private fun checkUserLoginOrNot()
    {
        Handler(mainLooper).postDelayed({
            mainViewModel.getUserLogin(this@SplashScreenActivity).observe(this){ user ->
                val targetActivity = if (user.isLogin == true) HomeActivity::class.java else MainActivity::class.java
                startActivity(Intent(this@SplashScreenActivity,targetActivity)).also {
                    finish()
                }
            }
        },Constant.TIME_OF_DELAYED)
    }

}