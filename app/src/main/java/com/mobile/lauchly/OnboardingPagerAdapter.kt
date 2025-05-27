package com.mobile.lauchly

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingPagerAdapter(
    activity: FragmentActivity,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return  fragments[position]

    }

    override fun getItemCount(): Int {
       return fragments.size
    }

} 