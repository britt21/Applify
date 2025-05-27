package com.mobile.lauchly

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mobile.lauchly.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var vadapter: OnboardingPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        var fragmentList = arrayListOf(
            OnbordingFragmentOne(),
            OnbordingFragmentTwo(),
            OnbordingFragmentThree(),
        )

        vadapter = OnboardingPagerAdapter(this, fragmentList)
        binding.viewPager.adapter = vadapter

        binding.nextbutton.setOnClickListener {
            val nextPage = binding.viewPager.currentItem + 1

            if (nextPage < vadapter.itemCount) {
                binding.viewPager.currentItem = nextPage
            }

            Log.v("CURRENT_ITEM", binding.viewPager.currentItem.toString())
            Log.v("NEXT_ITEM", nextPage.toString())
            Log.v("TOTAL_ITEM_COUNT", vadapter.itemCount.toString())


            if (binding.viewPager.currentItem == vadapter.itemCount - 1) {
                binding.nextbutton.text = "Get Started"
            } else {
                binding.nextbutton.text = "Next"
            }
        }


    }

    fun onOnboardingComplete(view: View) {
        startHomeActivity()
    }

    private fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}
