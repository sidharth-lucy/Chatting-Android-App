package com.example.letschat.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.letschat.R
import com.example.letschat.activity.HomeActivity
import com.example.letschat.databinding.LoginFragmentLayoutBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment:Fragment() {
    companion object{
        fun newInstance():LoginFragment{
            return LoginFragment()
        }
    }

    lateinit var binding: LoginFragmentLayoutBinding
    lateinit var auth:FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= LoginFragmentLayoutBinding.inflate(layoutInflater)
        auth= FirebaseAuth.getInstance()
        if(isLoggedIn()){
            openHomeActivity()
            activity?.finish()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }



    private fun setOnClickListener(){
        binding.tvSigunpLink.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_login_signup, SignUpFragmant.newInstance()).addToBackStack(null).commit()
        }
        binding.btnSignin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password=binding.etPassword.text.toString()
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if(it.isSuccessful){
//                    go to the home page and finish activity
                    updateLogin(email,password)
                    openHomeActivity()
                    activity?.finish()

                }else{
                    Toast.makeText(requireContext(), "Something went wrong! Please try again" ,
                        Toast.LENGTH_LONG)
                }
            }
        }

    }
    private fun openHomeActivity(){
        val intent= Intent(requireContext() , HomeActivity::class.java)
        startActivity(intent)

    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("letsChatUser", Context.MODE_PRIVATE)
        return sharedPreferences?.getBoolean(
            "isLoggedIn",
            false
        ) ?: false
    }

    private fun updateLogin(email: String, password: String) {
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("letsChatUser", Context.MODE_PRIVATE)
        val shEdit = sharedPreferences?.edit()
        shEdit?.putBoolean("isLoggedIn", true)
        shEdit?.putString("userEmail", email)
        shEdit?.putString("userPassword", password)
        shEdit?.apply()
    }


}