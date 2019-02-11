package com.example.testapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.zip.Inflater

class MemoListAdapter(memoList: MutableList<Memo>?, context: Context?) : RecyclerView.Adapter<MemoListAdapter.ViewHolder>(){

    var memoList : MutableList<Memo>? = memoList
    var contex : Context? = context

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val li : LayoutInflater = contex?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val v = li.inflate(R.layout.memo_layout, null)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return memoList?.size ?: 0
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val memo : Memo = memoList?.get(p1) ?: Memo.emptyMemo()

        p0.txtMemoTitle.setText(memo.title)
        p0.txtMemoDate.setText(memo.date)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtMemoTitle : TextView = itemView.findViewById(R.id.txtMemoTitle)
        val txtMemoDate : TextView = itemView.findViewById(R.id.txtMemoDate)
    }
}