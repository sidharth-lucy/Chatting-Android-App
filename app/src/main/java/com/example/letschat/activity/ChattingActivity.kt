package com.example.letschat.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letschat.adapter.MessageAdapter
import com.example.letschat.databinding.ChattingActivityLayoutBinding
import com.example.letschat.model.Message
import com.example.letschat.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChattingActivity:AppCompatActivity() {

    lateinit var binding: ChattingActivityLayoutBinding
    lateinit var auth: FirebaseAuth
    lateinit var authDB:DatabaseReference
    var senderNode:String?=null
    var receiverNode:String?=null
    var sender: FirebaseUser?=null
    private var allMessages= mutableListOf<Message>()
    var adapter: MessageAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChattingActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val friendName= intent.getStringExtra("friendName")
        val friendUid= intent.getStringExtra("friendUid")
        supportActionBar?.title = friendName
        auth= FirebaseAuth.getInstance()
        authDB= FirebaseDatabase.getInstance().getReference()
        sender= auth.currentUser
        senderNode = sender?.uid + friendUid
        receiverNode= friendUid+sender?.uid
        setOnClickListner()
        Update()
    }

    private fun setOnClickListner(){
        binding.ivSendMessage.setOnClickListener {
            if(binding.etMessages.text.isNotBlank() && sender!=null && receiverNode!=null && senderNode!=null){
                //save it
                val msg = binding.etMessages.text.toString()
                senderNode?.let { it1 ->
                    authDB.child("chats").child(it1).child("message").push().setValue(Message(
                        msg, sender!!.uid
                    ))
                }
                receiverNode?.let {
                    authDB.child("chats").child(it).child("message").push().setValue(Message(
                        msg, sender!!.uid
                    ))
                }


            }
            binding.etMessages.setText("")
        }
    }

    private fun Update() {
        senderNode?.let {
            authDB.child("chats").child(it).child("message")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        allMessages.clear()
                        for (child in snapshot.children) {
                            val message = child.getValue(Message::class.java)
                            if(message!=null){
                                allMessages.add(message)
                            }
                        }
                        if(adapter==null && sender!=null ){
                            adapter = MessageAdapter(this@ChattingActivity , allMessages ,sender!!.uid)
                            binding.rvMessages.adapter=adapter
                            binding.rvMessages.layoutManager= LinearLayoutManager(this@ChattingActivity , LinearLayoutManager.VERTICAL,false )
                        }else if( adapter!=null && sender!=null){
                            adapter?.updateDataSet(allMessages)

                        }
                        binding.rvMessages.smoothScrollToPosition(allMessages.size)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
        }
    }

}