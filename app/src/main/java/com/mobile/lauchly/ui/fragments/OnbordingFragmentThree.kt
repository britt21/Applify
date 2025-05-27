package com.mobile.lauchly.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobile.lauchly.databinding.FragmentThreeBinding

class OnbordingFragmentThree : Fragment(){

    lateinit var binding: FragmentThreeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThreeBinding.inflate(layoutInflater)



        return binding.root


    }


}