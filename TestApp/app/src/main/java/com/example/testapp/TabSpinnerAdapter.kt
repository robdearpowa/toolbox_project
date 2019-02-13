package com.example.testapp

import android.content.Context
import android.database.ContentObservable
import android.database.DataSetObserver
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import org.w3c.dom.Text

class TabSpinnerAdapter(ctx : Context, var viewId : Int, var tabList : MutableList<Tab>) : ArrayAdapter<Tab>(ctx, viewId, tabList) {
    override fun getCount(): Int {
        return tabList.size
    }

    override fun getItem(position: Int): Tab? {
        return tabList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}