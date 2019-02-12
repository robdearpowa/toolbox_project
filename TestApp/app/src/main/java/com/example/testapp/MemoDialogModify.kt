package com.example.testapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.memo_dialog.view.*

class MemoDialogModify() : DialogFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            var li : LayoutInflater = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;
            var v : View = li.inflate(R.layout.memo_dialog, null)

            var memo : Memo? = Memo.memoList?.get(MemoFragment.instance.lastMemoSelected!!)

            if(memo != null){
                v.etxtMemoTitle.setText(memo.title)
                v.etxtMemoContent.setText(memo.text)
            }

            builder.setView(v)
                    // Add action buttons
                    .setPositiveButton("Salva",
                            DialogInterface.OnClickListener { dialog, id ->
                                var title : String = v.etxtMemoTitle!!.text.toString()
                                var content : String = v.etxtMemoContent!!.text.toString()

                                Memo.modifyElementAtPosition(MemoFragment.instance.lastMemoSelected, title, content)
                                MemoFragment.instance.updateMemoListAdapter()
                                Toast.makeText(context, "'"+title+"'" + " Aggiornata", Toast.LENGTH_SHORT).show()

                            })
                    .setNegativeButton("Annulla",
                            DialogInterface.OnClickListener { dialog, id ->
                                getDialog().cancel()
                            })
                    .setNeutralButton("Elimina", DialogInterface.OnClickListener{dialog, id ->
                        Memo.deleteElementAtPositon(MemoFragment.instance.lastMemoSelected!!)
                        Toast.makeText(context, "'"+memo?.title+"'" + " Eliminata", Toast.LENGTH_SHORT).show()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}