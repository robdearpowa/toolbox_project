package com.example.testapp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Script;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalcFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalcFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalcFragment extends Fragment implements JavascriptInterface{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private enum Operator{
        PLUS, MINUS, MULTI, DIV, NOTHING
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private LinearLayout numPadContainer;
    private List<LinearLayout> numPad_rows;
    private List<Button> numPad_btns;
    private List<Button> op_btns;
    private Button btnCalc_Del;
    private Button btnCalc_Exec;
    private Button btnCalc_Dot;
    private TextView txtCalc;
    private TextView txtCalcLastNumber;

    private Operator operationExec = Operator.NOTHING;
    private double actualNumber;
    private double lastNumber;

    public CalcFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalcFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalcFragment newInstance(String param1, String param2) {
        CalcFragment fragment = new CalcFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        numPad_rows = new ArrayList<LinearLayout>();
        numPad_btns = new ArrayList<Button>();
        op_btns = new ArrayList<Button>();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calc, container, false);



        numPadContainer = v.findViewById(R.id.numPadContainer);

        for (int i = 0; i < numPadContainer.getChildCount(); i++){
            numPad_rows.add((LinearLayout) numPadContainer.getChildAt(i));
        }

        for(int i = 0; i < 10; i++){
            int id = getResources().getIdentifier("btnCalc_" + i, "id", getContext().getPackageName());
            numPad_btns.add((Button) v.findViewById(id));
        }

        op_btns.add((Button) v.findViewById(R.id.btnCalc_Plus));
        op_btns.add((Button) v.findViewById(R.id.btnCalc_Minus));
        op_btns.add((Button) v.findViewById(R.id.btnCalc_Multi));
        op_btns.add((Button) v.findViewById(R.id.btnCalc_Div));

        op_btns.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOperation(Operator.PLUS);
            }
        });
        op_btns.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOperation(Operator.MINUS);
            }
        });
        op_btns.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOperation(Operator.MULTI);
            }
        });
        op_btns.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOperation(Operator.DIV);
            }
        });

        txtCalc = v.findViewById(R.id.txtCalc);
        txtCalcLastNumber = v.findViewById(R.id.txtCalcLastNumber);
        btnCalc_Del = v.findViewById(R.id.btnCalc_Del);
        btnCalc_Exec = v.findViewById(R.id.btnCalc_Exec);
        btnCalc_Dot = v.findViewById(R.id.btnCalc_Dot);

        if(savedInstanceState!=null){
            operationExec = Operator.valueOf(savedInstanceState.getString("operationExec"));
            lastNumber = savedInstanceState.getDouble("lastNumber");
            actualNumber = savedInstanceState.getDouble("actualNumber");
            txtCalc.setText(savedInstanceState.getString("txtCalc"));
        }

        btnCalc_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLastChar();
            }
        });

        btnCalc_Del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                resetTxtCalc("0");
                return false;
            }
        });

        btnCalc_Exec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOperation(Operator.NOTHING);
            }
        });

        btnCalc_Dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeDot();
            }
        });

        for(int i = 0; i < numPad_btns.size(); i++){
            Button selectedButton = numPad_btns.get(i);
            selectedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    int value = Integer.parseInt(b.getText().toString());
                    writeNumber(value);
                }
            });
        }

        // Inflate the layout for this fragment
        return v;
    }

    private void writeNumber(int value){
        String text  = txtCalc.getText().toString();
        if(text.equals("0") || text.equals("Indefinito")){
            text = ""+value;
        }
        else{
            text += value;
        }

        txtCalc.setText(text);
    }

    private void writeDot(){
        String text = txtCalc.getText().toString();
        if(text.equals("Indefinito")){
            text = "0";
        }
        Pattern p = Pattern.compile("([0-9]{0,100}[.]{0})\\w+");
        Matcher m = p.matcher(text);
        if(m.matches()){
            text += ".";
            txtCalc.setText(text);
        }

    }

    private void deleteLastChar(){
        String text = txtCalc.getText().toString();
        if(text.length() > 1){
            text = text.substring(0, text.length() - 1);
        }
        else{
            text = "0";
        }

        txtCalc.setText(text);
    }

    private void updateActualNumber(){
        String number = txtCalc.getText().toString();
        double parsedNumber = Double.parseDouble(number);
        lastNumber = actualNumber;
        actualNumber = parsedNumber;
    }

    private void resetTxtCalc(String resetWith){
        txtCalcLastNumber.setText(""+lastNumber);
        txtCalc.setText(resetWith);
    }

    private void checkOperation(Operator op){
        if(operationExec.equals(Operator.NOTHING) && !op.equals(Operator.NOTHING)){
            startOperation(op);
        }
        else if(op.equals(Operator.NOTHING)){
            endOperation();
        }
        else{
            endOperation();
            startOperation(op);
        }
    }

    private void startOperation(Operator op){
        operationExec = op;
        updateActualNumber();
        resetTxtCalc("0");
    }

    private void endOperation(){
        switch(operationExec){
            case PLUS:
                updateActualNumber();
                double sum = lastNumber+actualNumber;
                resetTxtCalc(""+sum);
                operationExec = Operator.NOTHING;
                break;
            case MINUS:
                updateActualNumber();
                double sott = lastNumber-actualNumber;
                resetTxtCalc(""+sott);
                operationExec = Operator.NOTHING;
                break;
            case MULTI:
                updateActualNumber();
                double mult = lastNumber*actualNumber;
                resetTxtCalc(""+mult);
                operationExec = Operator.NOTHING;
                break;
            case DIV:
                updateActualNumber();
                if(actualNumber != 0){
                    double div = lastNumber/actualNumber;
                    resetTxtCalc(""+div);
                }
                else{
                    resetTxtCalc("Indefinito");
                    lastNumber = 0;
                }

                operationExec = Operator.NOTHING;
                break;
            case NOTHING:
                break;

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("operationExec", operationExec.name());
        outState.putDouble("lastNumber", lastNumber);
        outState.putDouble("actualNumber", actualNumber);
        outState.putString("txtCalc", txtCalc.getText().toString());
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

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
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
