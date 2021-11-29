package com.example.marvels.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment


open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

}