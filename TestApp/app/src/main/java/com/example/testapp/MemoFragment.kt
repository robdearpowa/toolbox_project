package com.example.testapp

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_memo.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MemoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MemoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MemoFragment : Fragment() {

    var lastMemoSelected : Int? = null

    private var memoListAdapter : MemoListAdapter? = null
    var memoListXML : RecyclerView? = null; private set
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_memo, container, false)

        memoListXML = v.memoList as RecyclerView
        val btnAddMemo = v.btnAddMemo as FloatingActionButton

        btnAddMemo.setOnClickListener{
            /*Memo("Memo" + Memo.memoList?.size, "Memo Content")
            Log.d("MemoList", Memo.memoList?.size.toString())
            updateMemoListAdapter()*/
            var md : MemoDialog = MemoDialog()
            md.show(fragmentManager, "MemoCreate")
        }

        memoSaver = activity!!.getPreferences(MODE_PRIVATE)
        Memo.loadMemoList()



        memoListAdapter = MemoListAdapter(v.context)
        memoListXML?.layoutManager = LinearLayoutManager(v.context)
        memoListXML?.adapter = memoListAdapter

        // Inflate the layout for this fragment
        return v
    }

    fun updateMemoListAdapter(){
        memoListAdapter?.updateMemoList();
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        lateinit var instance : MemoFragment
        lateinit var memoSaver : SharedPreferences


        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                MemoFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    init {
        instance = this
    }
}
