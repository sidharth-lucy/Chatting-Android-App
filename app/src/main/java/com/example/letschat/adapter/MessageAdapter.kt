package com.example.letschat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.letschat.databinding.ReceiverMessageItemBinding
import com.example.letschat.databinding.SenderMessageItemBinding
import com.example.letschat.model.Message

class MessageAdapter(private val context: Context, private var messageList: List<Message>, private val userId:String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val SENDER= 1
    private val RECEIVER=2

    class SenderViewHolder(val binding: SenderMessageItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:Message){
            binding.tvMesage.text = data.message
        }

    }

    class ReceiverViewHolder(val binding: ReceiverMessageItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:Message){
            binding.tvMesage.text = data.message
        }

    }

    override fun getItemViewType(position: Int): Int {
        if(messageList[position].senderUid == userId){
            return SENDER
        }else{
            return RECEIVER
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1->{
                SenderViewHolder(SenderMessageItemBinding.inflate(LayoutInflater.from(context), parent ,false ))
            }
            else->{
                ReceiverViewHolder(ReceiverMessageItemBinding.inflate(LayoutInflater.from(context),parent,false))
            }
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is SenderViewHolder){
            holder.bind(messageList[position])
        }else if(holder is ReceiverViewHolder){
            holder.bind(messageList[position])
        }
    }

    fun updateDataSet(data: List<Message>){
        this.messageList = data
        notifyDataSetChanged()
    }

}