package com.algorithm416.csjolup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LecturePoint.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LecturePoint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LecturePoint extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // 12 ~ 15 학번
    private String A;
    private String B;
    private String C;
    private String D;
    private String E;
    private String F;
    private String G;
    private String AG_SUM;

    private String M;
    private String S;
    private String BSM_SUM;

    // 16 ~ 18 학번
    private String 공통역량교양;


    private int ListSize; // 리스트 사이즈

    // 12 ~ 15 학번
    private int A_Point = 0;
    private int B_Point = 0;
    private int C_Point = 0;
    private int D_Point = 0;
    private int E_Point = 0;
    private int F_Point = 0;
    private int G_Point = 0;
    private int AG_Sum_point = 0;

    private int M_Point = 0;
    private int S_Point = 0;
    private int MS_Sum_Point = 0;

    // 16 ~ 18 학번
    private int 공통역량필수 = 0;

    // 12 ~ 15 학번
    private TextView TextA;
    private TextView TextB;
    private TextView TextC;
    private TextView TextD;
    private TextView TextE;
    private TextView TextF;
    private TextView TextG;
    private TextView TextSum;
    private TextView TextAG_Sum;

    private TextView TextM;
    private TextView TextS;
    private TextView TextMS_Sum; // BSM

    // 16 ~ 18 학번
    private TextView Text공통역량;
    private TextView 공통역량교양텍스트;

    private ArrayList<Lecture> ArList;

    private CurriculumDB db;

    private View view4;

    private LinearLayout KCC;
    private LinearLayout NoneKCC;
    private LinearLayout LinearLayG;

    private Liberal_arts liberalArts;

    public LecturePoint() {
        // Required empty public constructor
    }

    public Lecture getItem(int position){
        return ArList.get(position);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LecturePoint.
     */
    // TODO: Rename and change types and number of parameters
    public static LecturePoint newInstance(String param1, String param2) {
        LecturePoint fragment = new LecturePoint();
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

        db = new CurriculumDB(getContext());

        ConnectDB(mParam2);

        Lecturepointsum();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lecture_point, container, false);

        TextA = (TextView) view.findViewById(R.id.textA);
        TextB = (TextView) view.findViewById(R.id.textB);
        TextC = (TextView) view.findViewById(R.id.textC);
        TextD = (TextView) view.findViewById(R.id.textD);
        TextE = (TextView) view.findViewById(R.id.textE);
        TextF = (TextView) view.findViewById(R.id.textF);
        TextG = (TextView) view.findViewById(R.id.textG);
        TextSum = (TextView) view.findViewById(R.id.textSUM);
        TextAG_Sum = (TextView)  view.findViewById(R.id.textAG_SUM);

        TextM = (TextView) view.findViewById(R.id.textM);
        TextS = (TextView) view.findViewById(R.id.textS);
        TextMS_Sum = (TextView) view.findViewById(R.id.textMS_SUM);

        공통역량교양텍스트 = (TextView) view.findViewById(R.id.공통역량);
        Text공통역량 = (TextView) view.findViewById(R.id.text공통역량);

        view4 = (View) view.findViewById(R.id.view4);

        KCC = (LinearLayout) view.findViewById(R.id.KCC);
        NoneKCC = (LinearLayout) view.findViewById(R.id.NoneKCC);
        LinearLayG = (LinearLayout) view.findViewById(R.id.LinerG);

        SettingCurriculum();

        return view;
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

    public void Lecturepointsum() {
        ArList = liberalArts.getList();
        ListSize = ArList.size();

        for (int i = 0; i < ListSize; i++) {

            switch (mParam2) {
                case "2012":
                case "2013":
                case "2014":
                case "2015":
                    if (ArList.get(i).getItemCheck()) {
                        if (ArList.get(i).getLectureType().contains("A")) {
                            A_Point += Integer.parseInt(ArList.get(i).getLectureCredit());
                        } else if (ArList.get(i).getLectureType().contains("B")) {
                            B_Point += Integer.parseInt(ArList.get(i).getLectureCredit());
                        } else if (ArList.get(i).getLectureType().contains("C")) {
                            C_Point += Integer.parseInt(ArList.get(i).getLectureCredit());
                        } else if (ArList.get(i).getLectureType().contains("D")) {
                            D_Point += Integer.parseInt(ArList.get(i).getLectureCredit());
                        } else if (ArList.get(i).getLectureType().contains("E")) {
                            E_Point += Integer.parseInt(ArList.get(i).getLectureCredit());
                        } else if (ArList.get(i).getLectureType().contains("F")) {
                            F_Point += Integer.parseInt(ArList.get(i).getLectureCredit());
                        } else if (ArList.get(i).getLectureType().contains("G")) {
                            G_Point += Integer.parseInt(ArList.get(i).getLectureCredit());
                        } else if (ArList.get(i).getLectureType().contains("M")) {
                            M_Point += Integer.parseInt(ArList.get(i).getLectureCredit());
                        } else if (ArList.get(i).getLectureType().contains("S")) {
                            S_Point += Integer.parseInt(ArList.get(i).getLectureCredit());
                        }
                    }
                    break;
                case "2016":
                    if (ArList.get(i).getItemCheck()) {
                        switch (ArList.get(i).getLectureNum()) {
                            case "ZZA10007":
                            case "ZZA10008":
                            case "ZZA10009":
                            case "ZZA30005":
                                공통역량필수 += Integer.parseInt(ArList.get(i).getLectureCredit());
                                break;
                            default:

                                break;
                        }
                    }
                    break;
                case "2017":
                case "2018":
                    if (ArList.get(i).getItemCheck()) {
                        switch (ArList.get(i).getLectureNum()) {
                            case "ZZA10007":
                            case "ZZA10008":
                            case "ZZA10009":
                            case "ZZA30005":
                                공통역량필수 += Integer.parseInt(ArList.get(i).getLectureCredit());
                                break;
                            default:
                                break;
                        }
                    }
                    break;
            }
        }

        if (mParam2.equals("2015")) {
            AG_Sum_point = A_Point + B_Point + C_Point + D_Point + E_Point + F_Point;
        } else {
            AG_Sum_point = A_Point + B_Point + C_Point + D_Point + E_Point + F_Point + G_Point;
        }

        MS_Sum_Point = M_Point + S_Point;
    }

    private void ConnectDB(String year) {

        String[][] table = db.GetMinCredits(year);
        switch (year)
        {
            case "2012":
            case "2013":
            case "2014":
            case "2015":
                A = table[3][1];
                B = table[4][1];
                C = table[5][1];
                D = table[6][1];
                E = table[7][1];
                F = table[8][1];
                G = table[9][1];
                AG_SUM = table[10][1];

                M = table[11][1];
                S = table[12][1];
                BSM_SUM = table[13][1];
                break;
            case "2016":
            case "2017":
            case "2018":
                break;
        }
        공통역량교양 = table[3][1];
    }

    public void SettingCurriculum(){
        switch (mParam2)
        {
            case "2012":
            case "2013":
            case "2014":
            case "2015":
                if(mParam2.equals("2015")){
                    view4.setVisibility(View.GONE);
                    LinearLayG.setVisibility(View.GONE);
                    TextSum.setText("A ~ F 합 : ");
                }
                NoneKCC.setVisibility(View.GONE);
                TextA.setText("" + A_Point + " / " + A);
                TextB.setText("" + B_Point + " / " + B);
                TextC.setText("" + C_Point + " / " + C);
                TextD.setText("" + D_Point + " / " + D);
                TextE.setText("" + E_Point + " / " + E);
                TextF.setText("" + F_Point + " / " + A);
                TextG.setText("" + G_Point + " / " + G);
                TextAG_Sum.setText("" + AG_Sum_point + " / " + AG_SUM);
                TextM.setText("" + M_Point + " / " + M);
                TextS.setText("" + S_Point + " / " + S);
                TextMS_Sum.setText("" + MS_Sum_Point + " / " + BSM_SUM);
                break;
            case "2016":
            case "2017":
            case "2018":
                KCC.setVisibility(View.GONE);
                if(mParam2.equals("2016")){
                    Text공통역량.setText("공통 : ");
                }
                else{
                    Text공통역량.setText("역량 : ");
                }
                공통역량교양텍스트.setText("" + 공통역량필수 + " / " + 공통역량교양);
                break;
        }
    }
}
