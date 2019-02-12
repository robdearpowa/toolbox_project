package com.example.testapp

import android.content.Context
import android.os.MemoryFile
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.memo_layout.view.*
import java.util.zip.Inflater
import android.widget.Toast
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


class MemoListAdapter(var context: Context?) : RecyclerView.Adapter<MemoListAdapter.ViewHolder>(){

    var df : SimpleDateFormat = SimpleDateFormat("HH:mm EEE d/MMM/yyyy", Locale("it"))

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val li : LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val v = li.inflate(R.layout.memo_layout, p0, false)

        v.setOnClickListener(View.OnClickListener { v ->
            var itemPos : Int = MemoFragment.instance.memoListXML!!.getChildAdapterPosition(v)
            MemoFragment.instance.lastMemoSelected = itemPos
            var mdm : MemoDialogModify = MemoDialogModify()
            mdm.show(MemoFragment.instance.fragmentManager, "MemoModify")

        })

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return Memo.memoList?.size ?: Memo.emptyMemoList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val memo : Memo? = Memo.memoList?.get(p1)

        if(memo!=null && (Memo.memoList?.size ?: Memo.emptyMemoList.size) > 0){
            p0.txtMemoTitle.setText(memo.title)
            p0.txtMemoDate.setText(df.format(memo.date))
        }

    }

    fun updateMemoList(){
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtMemoTitle : TextView = itemView.txtMemoTitle
        val txtMemoDate : TextView = itemView.txtMemoDate
    }
}