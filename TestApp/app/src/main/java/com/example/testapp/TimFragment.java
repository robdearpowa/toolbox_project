package com.example.testapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView txtTim;
    private Button btnStartTim;
    private Button btnStopTim;

    private Timer timer2;

    private CountUpTimer timer;
    private double count = 0;
    private long startCount = 0;

    public TimFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimFragment newInstance(String param1, String param2) {
        TimFragment fragment = new TimFragment();
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

        if (savedInstanceState != null) {
            startCount = savedInstanceState.getLong("startCount");
        }

        if (startCount != 0) {
            clickStartTim();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tim, container, false);

        txtTim = v.findViewById(R.id.txtTim);
        btnStartTim = v.findViewById(R.id.btnStartTim);
        btnStopTim = v.findViewById(R.id.btnStopTim);

        txtTim.setText("" + count);

        btnStartTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStartTim();
            }
        });

        btnStopTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStopTim();
            }
        });

        return v;
    }

    private void clickStartTim() {
        /*new CountDownTimer(30000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                txtTim.setText("" + count);
                count++;
            }

            @Override
            public void onFinish() {
                txtTim.setText("Finish!");
            }
        }.start();*/
        /*if(timer == null){
            timer = new CountUpTimer(1000){
                @Override
                public void onTick(long elapsedTime) {
                    txtTim.setText("" + count);
                    count++;
                }
            };

            timer.start();
        }*/
        if (timer2 == null) {
            timer2 = new Timer();
            timer2.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (startCount == 0) {
                        startCount = System.currentTimeMillis();
                    }
                    long mills = System.currentTimeMillis();
                    count = (mills - startCount);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DecimalFormat df = new DecimalFormat("0.00");

                                txtTim.setText(df.format(count/1000d));
                            }
                        });
                    } else {
                        this.cancel();
                    }

                }
            }, 0, 10);
        }


    }

    private void updateTxtTim() {

    }

    private void clickStopTim() {
        if (timer2 != null) {
            timer2.cancel();
            timer2.purge();
            count = 0;
            startCount = 0;
            txtTim.setText("0");
            timer2 = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("startCount", startCount);
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
