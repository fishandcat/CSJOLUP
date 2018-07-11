package com.algorithm416.csjolup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Grades.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Grades#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Grades extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String 전필최소;
    private String 전선최소;

    private int ListSize;
    private int 전필 = 0;
    private int 전선 = 0;
    private int 교직 = 0;

    private TextView 전필텍스트;
    private TextView 전선텍스트;

    private Lecture lecture;
    private Major major;

    private ArrayList<Lecture> ArList;

    private CurriculumDB db;

    public Grades() {
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
     * @return A new instance of fragment Grades.
     */
    // TODO: Rename and change types and number of parameters
    public static Grades newInstance(String param1, String param2) {
        Grades fragment = new Grades();
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

        db  = new CurriculumDB(getContext());

        ConnectDB(mParam2);

        Majorpointsum();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_grades, container, false);

        전필텍스트 = (TextView) view.findViewById(R.id.전필텍스트);
        전선텍스트 = (TextView) view.findViewById(R.id.전선텍스트);

        전필텍스트.setText(""+ 전필 + " / " + 전필최소);
        전필 = 0;

        전선텍스트.setText(""+ 전선 + " / " + 전선최소);
        전선 = 0;

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
            //throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
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

    public void Majorpointsum(){
        ArList = major.getList();
        ListSize = ArList.size();

        for(int i = 0; i < ListSize; i++) {

            if(ArList.get(i).getItemCheck()) {

                if(ArList.get(i).getLectureType().equals("전필")) {

                    전필 += Integer.parseInt(ArList.get(i).getLectureCredit());

                }
                else if(ArList.get(i).getLectureType().equals("전선")){

                    전선 += Integer.parseInt(ArList.get(i).getLectureCredit());

                }
            }
        }
    }

    private void ConnectDB(String year) {

        String[][] table = db.GetMinCredits(year);
        전필최소 = table[1][1];
        전선최소 = table[2][1];

    }
}
