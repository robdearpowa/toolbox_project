package com.example.testapp

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.StringBuilder
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.util.*



class Memo(var title: String, var text: String){
    var id_old : Int? = (0..99999).random()
    var id : UUID = UUID.randomUUID()
    //var title : String = title
    //var text : String = text
    var date : Date = Calendar.getInstance().time

    init {
        var result : Boolean = false
        if(memoList == null){
            memoList = mutableListOf()
        }
        /*do{
            for (memo in memoList as MutableList<Memo>){
                if(memo.id == this.id){
                    result = true
                }
                else{
                    result = false
                }
            }

            if(result){
                this.id = (0..99999).random()
            }
        }while (result)*/
        (memoList as MutableList<Memo>).add(0,this)
        saveMemoList()
    }

    companion object {
        var emptyMemoList : MutableList<Memo> = mutableListOf()
        var memoList : MutableList<Memo>? = mutableListOf()

        fun createMemo(memo : Memo){
            var m : Memo = memo
        }

        fun saveMemoList(){
            if(MemoFragment.memoSaver != null){
                var editor : SharedPreferences.Editor = MemoFragment.memoSaver.edit();
                var gson : Gson = Gson()
                var json : String = gson.toJson(memoList)

                editor.putString("memoList", json)
                editor.commit()
            }
        }

        fun loadMemoList(){
            if(MemoFragment.memoSaver != null){
                var gson : Gson = Gson()
                var json : String  = MemoFragment.memoSaver.getString("memoList", "")

                var turnsType = object : TypeToken<MutableList<Memo>>() {}.type

                memoList = gson.fromJson<MutableList<Memo>>(json, turnsType)
            }

        }

        fun deleteElementAtPositon(pos : Int){
            memoList?.remove(memoList?.get(pos))
            saveMemoList()
            MemoFragment.instance.updateMemoListAdapter()
        }

        fun modifyElementAtPosition(pos: Int?, title: String, text: String){
            if(pos!=null) {
                deleteElementAtPositon(pos)
                Memo(title, text)
            }
        }
    }
}