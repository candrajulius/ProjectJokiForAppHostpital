package com.candra.rsu_royal_prima_app.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.candra.rsu_royal_prima_app.R
import com.candra.rsu_royal_prima_app.databinding.ActivityHomeBinding
import com.candra.rsu_royal_prima_app.databinding.DialogProfileBinding
import com.candra.rsu_royal_prima_app.helper.Constant
import com.candra.rsu_royal_prima_app.helper.Utils
import com.candra.rsu_royal_prima_app.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickForMenu()
        Utils.setToolbar(Constant.RSU,Constant.TITLE_HOME.uppercase(),supportActionBar,1)
        binding.tvClock.apply {
            format24Hour = Constant.FORMAT24HOURS
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
        binding.mtvDate.apply {
            text = Utils.formatDate
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if  (item.itemId == R.id.item_profile)
        {
            showDialogProfile()
        }

        return super.onOptionsItemSelected(item)
    }


    private fun showDialogProfile()
    {
        val builder = AlertDialog.Builder(this@HomeActivity).create()
        val view2 = DialogProfileBinding.inflate(LayoutInflater.from(this@HomeActivity))
        builder.setView(view2.root)
        view2.apply {
            mainViewModel.getUserLogin(this@HomeActivity).observe(this@HomeActivity){ user ->
                    edtUsername.setText(user.username)
                    edtPassword.setText(user.password)
                    tvGreeting.text = user.username?.let { Utils.greetingMessage(it) }
                    btnLogout.setOnClickListener {
                        lifecycleScope.launch{
                            mainViewModel.logoutUser(this@HomeActivity).also { builder.dismiss() }
                            startActivity(Intent(this@HomeActivity,MainActivity::class.java))
                                .also { finish() }
                    }
                }
                imgClose.setOnClickListener {
                    builder.dismiss()
                }
            }
        }
        builder.show()
    }

    private fun setClickForMenu(){
        binding.apply {
            containerDoctor.setOnClickListener {
                startActivity(Intent(this@HomeActivity,DoctorActivity::class.java))
            }
            containerPatient.setOnClickListener {
                startActivity(Intent(this@HomeActivity,PatientActivity::class.java))
            }
            containerSystemOnlineQuening.setOnClickListener {
                startActivity(Intent(this@HomeActivity,SystemQueueActivity::class.java))
            }
        }
    }

}