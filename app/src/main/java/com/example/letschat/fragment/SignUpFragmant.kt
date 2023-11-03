package com.example.letschat.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.letschat.R
import com.example.letschat.databinding.SignUpFragmentLayoutBinding
import com.example.letschat.model.User
import com.example.letschat.utils.hideSoftKeyboard
import com.example.letschat.utils.isValidEmail
import com.example.letschat.utils.isValidName
import com.example.letschat.utils.isValidPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpFragmant:Fragment() {

    companion object{
        fun newInstance():SignUpFragmant{
            return SignUpFragmant()
        }
    }
    lateinit var binding: SignUpFragmentLayoutBinding
    lateinit var auth:FirebaseAuth
    lateinit var authDB:DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignUpFragmentLayoutBinding.inflate(layoutInflater)
        auth= FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        hideTheKeyBoard()
    }

    private fun setOnClickListener(){
        binding.tvSignInLink.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_login_signup, LoginFragment.newInstance()).addToBackStack(null).commit()
        }

        binding.btnSignup.setOnClickListener {
            val email= binding.etEmail.text.toString()
            val userName= binding.etUserName.text.toString()
            val password= binding.etPassword.text.toString()
            if(isValidEmail(email) && isValidName(userName) && isValidPassword(password)){
                createNewUser(email,userName,password)
            }else{
                Toast.makeText(requireContext(), "Fill all the details correctly!" , Toast.LENGTH_SHORT)
            }

        }

    }

    private fun createNewUser(email:String , userName:String , password:String){
        auth.createUserWithEmailAndPassword(email , password).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(requireContext(), "Wow User created" ,Toast.LENGTH_LONG)
                addUserToDB(userName,email,auth.currentUser?.uid)
                openLoginPage()

            }else{
                Toast.makeText(requireContext(), "Something went wrong! Please try again" ,Toast.LENGTH_LONG)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun hideTheKeyBoard(){
        binding.root.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN){
                hideSoftKeyboard(v,requireContext())
            }
            return@setOnTouchListener false
        }
    }
    private fun openLoginPage() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fl_login_signup, LoginFragment.newInstance()).addToBackStack(null)
            .commit()
    }

    private fun addUserToDB(name:String,email:String,uid:String?){
        if(uid!=null){
            authDB = FirebaseDatabase.getInstance().getReference()
            authDB.child("user").child(uid).setValue(User(name,email,uid))
        }
    }
}