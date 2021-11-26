package com.example.avengers.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.avengers.R
import com.example.avengers.domain.model.request.CharactersRequest
import com.example.avengers.domain.utils.isNetworkAvailable
import com.example.avengers.presentation.characters.CharactersFragment
import com.example.avengers.presentation.characters.CharactersViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(CharactersFragment.newInstance())

    }

    private fun replaceFragment(fragment: Fragment?, addToBackStack: Boolean = true) {
        if (fragment == null) return
        val className = fragment.javaClass.name

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(className)
        }
        fragmentTransaction.replace(R.id.framelayout_main, fragment, className).commit()

    }
}