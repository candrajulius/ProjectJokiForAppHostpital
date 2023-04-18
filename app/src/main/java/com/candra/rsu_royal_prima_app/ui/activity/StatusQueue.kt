package com.candra.rsu_royal_prima_app.ui.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.candra.rsu_royal_prima_app.databinding.ContainerQueueLayoutBinding
import com.candra.rsu_royal_prima_app.helper.Constant
import com.candra.rsu_royal_prima_app.helper.Utils
import com.candra.rsu_royal_prima_app.ui.adapter.ViewPagerAdapter
import com.candra.rsu_royal_prima_app.ui.fragment.CancelFragment
import com.candra.rsu_royal_prima_app.ui.fragment.DoneFragment
import com.candra.rsu_royal_prima_app.ui.fragment.WaitingFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatusQueue : AppCompatActivity() {
    private lateinit var binding: ContainerQueueLayoutBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContainerQueueLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createListFragment()
        Utils.setToolbar(Constant.RSU,Constant.ACTIVITY_STATUS_QUEUE,supportActionBar,0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home)
        {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createListFragment()
    {
        binding.apply {
            val tabLayout = tabContainer
            val viewPager2 = viewPagerStatusQueue

            val listFragment = arrayListOf(
                WaitingFragment(),
                DoneFragment(),
                CancelFragment()
            )
            viewPager2.adapter = ViewPagerAdapter(listFragment,supportFragmentManager,lifecycle)
            TabLayoutMediator(tabLayout,viewPager2){
                tab,position ->
                when(position){
                    0 -> tab.text = Constant.WAITING
                    1 -> tab.text = Constant.DONE
                    2 -> tab.text = Constant.CANCEL
                }
            }.attach()
        }
    }
}