package com.example.testapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_settings.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SettingsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SettingsFragment : Fragment() {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var spinnerAdapter : TabSpinnerAdapter
    private lateinit var spinnerXML : Spinner
    private lateinit var btnSettings_save : Button

    lateinit var tabList : MutableList<Tab>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_settings, container, false)

        tabList = mutableListOf()
        spinnerXML = v.tabSpinner1
        btnSettings_save = v.btnSettings_save

        tabList.add(Tab(0, "Calcolatrice"))
        tabList.add(Tab(1, "Timer"))
        tabList.add(Tab(2, "Memo"))
        tabList.add(Tab(3, "Impostazioni"))


        spinnerAdapter = TabSpinnerAdapter(context!!, R.layout.tab_text_spinner_layout, tabList)

        spinnerXML.adapter = spinnerAdapter

        spinnerXML.setSelection(MainTestActivity.instance.settings.initialTab ?: FancyBoxSettings.defaultInitialTab)

        btnSettings_save.setOnClickListener(View.OnClickListener { v: View? ->
            MainTestActivity.instance.settings.initialTab = spinnerXML.selectedItemPosition
            MainTestActivity.instance.settings.saveSettings()
            Toast.makeText(context, "Impostazioni Salvate", Toast.LENGTH_SHORT).show()
        })


        return v
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


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SettingsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
