package com.algorithm416.csjolup;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JolupPoint.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JolupPoint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JolupPoint extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String MinJopup;

    private TextView TextJolup;

    private ArrayList<Lecture> ArList;

    private CurriculumDB db;

    public static List<PieEntry> pieEntries = new ArrayList<>();
    public static PieChart pieChart;

    public JolupPoint() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JolupPoint.
     */
    // TODO: Rename and change types and number of parameters
    public static JolupPoint newInstance(String param1, String param2) {
        JolupPoint fragment = new JolupPoint();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jolup_point, container, false);

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"이수 대상");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextSize(25);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setSliceSpace(3f);

        PieData pieData = new PieData(pieDataSet);

        pieChart = (PieChart) view.findViewById(R.id.PieChart);
        pieChart.setData(pieData);
        pieChart.setCenterTextSize(25);
        pieChart.setCenterTextColor(Color.DKGRAY);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        pieChart.getLegend().setTextSize(18);
        pieChart.setEntryLabelTextSize(18);
        if (mParam2.compareTo("2016") < 0) {
            pieChart.setCenterText(mParam2 + "년 교육과정\n졸업 학점 : " + db.GetMinCredits(mParam2)[14][1]);
        } else {
            pieChart.setCenterText(mParam2 + "년 교육과정\n졸업 학점 : " + db.GetMinCredits(mParam2)[8][1]);
        }

        pieChart.invalidate();
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

    private void ConnectDB(String year) {

        String[][] table = db.GetMinCredits(year);
        switch (year)
        {
            case "2012":
            case "2013":
            case "2014":
            case "2015":
                MinJopup = table[14][1];
                break;
            case "2016":
            case "2017":
            case "2018":
                MinJopup = table[8][1];
                break;
        }
    }

}
