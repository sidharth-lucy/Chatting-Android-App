package com.example.letschat.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letschat.adapter.UserListAdapter
import com.example.letschat.databinding.HomeActivityLayoutBinding
import com.example.letschat.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity:AppCompatActivity() {

    lateinit var binding: HomeActivityLayoutBinding
    var adapter:UserListAdapter? = null
    lateinit var auth:FirebaseAuth
    lateinit var authDB:DatabaseReference
    var userList= mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authDB= FirebaseDatabase.getInstance().getReference()
        auth= FirebaseAuth.getInstance()
        observe()

    }

    private fun populateAllFriendList(friendList:List<User>){
        if(adapter==null){
            adapter = UserListAdapter(this@HomeActivity,friendList,
                onClickOnName = {
                    openChattingActivity(it)
                })
            binding.rvFriendsList.adapter= adapter
            binding.rvFriendsList.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        }else{
            adapter?.notifyDataSetChanged()
        }
    }

    private fun observe(){
        authDB.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(child in snapshot.children){
                    val user= child.getValue(User::class.java)
                    if(user!=null && user.uid!=auth.currentUser?.uid){
                        userList.add(user)
                    }
                }
                populateAllFriendList(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun openChattingActivity(user: User){
        val intent= Intent(this , ChattingActivity::class.java)
        intent.putExtra("friendName" , user.name)
        intent.putExtra("friendUid" , user.uid)
        startActivity(intent)
    }


}