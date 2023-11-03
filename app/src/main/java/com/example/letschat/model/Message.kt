package com.example.letschat.model


class Message{
    var message:String?=null
    var senderUid:String?=null

    constructor(){}
    constructor(message:String? ,senderUid:String?){
        this.message=message
        this.senderUid=senderUid
    }

}

