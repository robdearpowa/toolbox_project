package com.example.testapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddressBookFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddressBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddressBookFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView lwContacts;

    private List<Contact> contactList;

    private OnFragmentInteractionListener mListener;

    public AddressBookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddressBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddressBookFragment newInstance(String param1, String param2) {
        AddressBookFragment fragment = new AddressBookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_address_book, container, false);
        // Inflate the layout for this fragment
        contactList = new ArrayList<Contact>();

        lwContacts = v.findViewById(R.id.lwContacts);

        contactList.add(new Contact(1, "Roberto", "Gigli", "3271822553", "roberto.gigli98@outlook.it"));
        contactList.add(new Contact(2, "Mario", "Rossi", "00000000", "sample@mail.com"));
        contactList.add(new Contact(3, "Luca", "Bianchi", "00000000", "sample@mail.com"));
        contactList.add(new Contact(4, "Luigi", "Rossi", "00000000", "sample@mail.com"));
        for (int i = 0; i < 5000; i++){
            contactList.add(new Contact(i, "Persona"+i, "Cognome"+i, "000000", "sample"+i+"@mail.com"));
        }

        //AddressBookListAdapter addressBookListAdapter = new AddressBookListAdapter(contactList, v.getContext());
        AddressBookListAdapter_new addressBookListAdapter_new = new AddressBookListAdapter_new(contactList, v.getContext());
        lwContacts.setLayoutManager(new LinearLayoutManager(v.getContext()));
        lwContacts.setAdapter(addressBookListAdapter_new);



        return v;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
