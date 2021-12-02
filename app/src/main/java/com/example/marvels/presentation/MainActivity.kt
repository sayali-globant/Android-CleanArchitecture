package com.example.marvels.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.marvels.R
import com.example.marvels.presentation.characters.CharactersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(CharactersFragment.newInstance(),false)

    }

    fun replaceFragment(fragment: Fragment?, addToBackStack: Boolean = true) {
        if (fragment == null) return
        val className = fragment.javaClass.name

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(className)
        }
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment, className).commit()

    }
}
