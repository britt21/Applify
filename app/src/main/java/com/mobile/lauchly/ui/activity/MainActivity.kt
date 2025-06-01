package com.mobile.lauchly.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.mobile.lauchly.cache.DataManager
import com.mobile.lauchly.databinding.ActivityMainBinding
import com.mobile.lauchly.ui.adapters.OnboardingPagerAdapter
import com.mobile.lauchly.ui.fragments.OnbordingFragmentOne
import com.mobile.lauchly.ui.fragments.OnbordingFragmentThree
import com.mobile.lauchly.ui.fragments.OnbordingFragmentTwo

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var vadapter: OnboardingPagerAdapter

    lateinit var dataManager: DataManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        dataManager = DataManager(this)



        var fragmentList = arrayListOf(
            OnbordingFragmentOne(),
            OnbordingFragmentTwo(),
            OnbordingFragmentThree(),
        )

        vadapter = OnboardingPagerAdapter(this, fragmentList)
        binding.viewPager.adapter = vadapter

        binding.nextbutton.setOnClickListener {
            val current = binding.viewPager.currentItem

            saveOnboardingStep(current)

            when (current) {
                0 -> {
                    binding.viewPager.currentItem = 1
                }

                1 -> {
                    saveOnboardingStep(1)
                    if (!isDefaultLauncher()) {
                        // Ask system to change default home
                        val intent = Intent(Intent.ACTION_MAIN).apply {
                            addCategory(Intent.CATEGORY_HOME)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(intent)
                    } else {
                        binding.viewPager.currentItem = 2
                    }
                }

                2 -> {
                    clearOnboardingStep()
                    finish()
                }
            }
        }

        handleSwipe()
    }

    fun handleSwipe() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {


                println("SWIPED==:" + position)
                println("COUNT;;SWIPED==:" + vadapter.itemCount)
                println("CURRSTEP;;SWIPED==:" + getOnboardingStep())
                saveOnboardingStep(position)



                if (position == vadapter.itemCount - 1 || (position == vadapter.itemCount - 2) && !isDefaultLauncher()) {
                    binding.viewPager.isUserInputEnabled = false

                    binding.nextbutton.visibility = View.GONE // hides the button
                } else {
                    binding.nextbutton.visibility = View.VISIBLE // shows the button
                }
            }
        })

    }

    private fun saveOnboardingStep(step: Int) {
        Log.v("SAVING STEP", "SAVING STEP: $step")
        dataManager.saveData("step", step.toString())
    }

    private fun getOnboardingStep(): Int {
        val prefs = dataManager.readData("step", "0")
        return prefs.toInt()
    }

    private fun clearOnboardingStep() {
        dataManager.saveData("step", "0")
    }


    private fun isDefaultLauncher(): Boolean {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        val resolveInfo = packageManager.resolveActivity(intent, 0)
        return resolveInfo?.activityInfo?.packageName == packageName
    }

    fun onOnboardingComplete(view: View) {
        startHomeActivity()
    }

    private fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun onResume() {
        super.onResume()

        val lastStep = getOnboardingStep()

        if (lastStep == 4) {

            startHomeActivity()
        } else {
            Log.v("OnboardingActivity", "Last Step: $lastStep")
            binding.viewPager.currentItem = lastStep
//            binding.nextbutton.visibility = View.GONE
        }
    }
}



