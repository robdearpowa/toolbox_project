package com.example.testapp

import java.time.LocalDateTime
import java.util.*



class Memo(id: Long, title: String, text: String){
    var id : Long? = id;
    var title : String = title
    var text : String = text
    var date : String = Calendar.getInstance().time.toString()

    companion object {
        fun emptyMemo() : Memo = Memo(999999, "", "")
    }

}