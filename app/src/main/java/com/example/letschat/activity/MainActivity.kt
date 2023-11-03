package com.example.letschat.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.letschat.R
import com.example.letschat.databinding.MainActivityLayoutBinding
import com.example.letschat.fragment.LoginFragment


class MainActivity:AppCompatActivity() {
    lateinit var binding: MainActivityLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportActionBar?.hide()
        addFragment(LoginFragment())
    }


    private fun addFragment(fragment: Fragment){
        val transation = supportFragmentManager.beginTransaction()
        transation.add(R.id.fl_login_signup,fragment)
        transation.commit()
    }

}