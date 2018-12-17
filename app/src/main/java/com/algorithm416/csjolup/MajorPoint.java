package com.algorithm416.csjolup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MajorPoint.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MajorPoint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MajorPoint extends Fragment {
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
    private String [] listJolup1;
    private String [] listJolup2;
    private String [] listJolup3;
    private String [] listJolup4;

    private boolean bJolup = false;

    private TextView 전필텍스트;
    private TextView 전선텍스트;
    private TextView 졸업텍스트;
    private TextView 꿈미래텍스트;

    private ArrayList<Lecture> ArList;

    private CurriculumDB db;

    private JolupRequirements jolupRequirements;

    public MajorPoint() {
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
     * @return A new instance of fragment MajorPoint.
     */
    // TODO: Rename and change types and number of parameters
    public static MajorPoint newInstance(String param1, String param2) {
        MajorPoint fragment = new MajorPoint();
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

        listJolup1 = getResources().getStringArray(R.array.jolup1);
        listJolup2 = getResources().getStringArray(R.array.jolup2);
        listJolup3 = getResources().getStringArray(R.array.jolup3);
        listJolup4 = getResources().getStringArray(R.array.jolup4);

        ConnectDB(mParam2);

        Majorpointsum();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_major_point, container, false);

        전필텍스트 = (TextView) view.findViewById(R.id.전필텍스트);
        전선텍스트 = (TextView) view.findViewById(R.id.전선텍스트);
        졸업텍스트 = (TextView) view.findViewById(R.id.졸업텍스트);
        꿈미래텍스트 = (TextView) view.findViewById(R.id.꿈미래텍스트);

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

    public void Majorpointsum(){
        ArList = Major.getList();
        ListSize = ArList.size();

        for(int i = 0; i < ListSize; i++) {
            if (ArList.get(i).isLecture()) {
                if (ArList.get(i).getItemCheck()) {
                    if (ArList.get(i).getLectureType().contains("전필")) {
                        전필 += Integer.parseInt(ArList.get(i).getLectureCredit());
                    } else if (ArList.get(i).getLectureType().contains("전선")) {
                        전선 += Integer.parseInt(ArList.get(i).getLectureCredit());
                    }
                }
            }
        }
    }

    private void ConnectDB(String year) {

        String[][] table = db.GetMinCredits(year);
        전필최소 = table[1][1];
        전선최소 = table[2][1];
    }

    public void SettingCurriculum(){
        boolean jolup1 = false;
        boolean jolup2 = false;
        boolean jolup3 = false;
        boolean jolup4 = false;

        전필텍스트.setText(""+ 전필 + " / " + 전필최소);

        전선텍스트.setText(""+ 전선 + " / " + 전선최소);

        switch (JolupRequirements.selection[0]){
            case 0:
                break;
            case 1:
                졸업텍스트.setText(listJolup1[1]);
                jolup1 = true;
                break;
            case 2:
                졸업텍스트.setText(listJolup1[2]);
                jolup1 = true;
                break;
            case 3:
                졸업텍스트.setText(listJolup1[3]);
                jolup1 = true;
                break;
        }

        switch (JolupRequirements.selection[1]){
            case 0:
                break;
            case 1:
                졸업텍스트.setText(listJolup2[1]);
                jolup2 = true;
                break;
            case 2:
                졸업텍스트.setText(listJolup2[2]);
                jolup2 = true;
                break;
            case 3:
                졸업텍스트.setText(listJolup2[3]);
                jolup2 = true;
                break;
            case 4:
                졸업텍스트.setText(listJolup2[4]);
                jolup2 = true;
                break;
            case 5:
                졸업텍스트.setText(listJolup2[5]);
                jolup2 = true;
                break;
        }

        switch (JolupRequirements.selection[2]){
            case 0:
                break;
            /*case 1:
                졸업텍스트.setText(listJolup3[1]);
                break;
            case 2:
                졸업텍스트.setText(listJolup3[2]);
                break;
            case 3:
                졸업텍스트.setText(listJolup3[3]);
                break;
            case 4:
                졸업텍스트.setText(listJolup3[4]);
                break;
            case 5:
                졸업텍스트.setText(listJolup3[5]);
                break;
            case 6:
                졸업텍스트.setText(listJolup3[6]);
                break;
            case 7:
                졸업텍스트.setText(listJolup3[7]);
                break;*/
            case 8:
                졸업텍스트.setText("독서인증완료!!");
                jolup3 = true;
                break;
        }

        switch (JolupRequirements.selection[3]){
            case 0:
                break;
            case 1:
                졸업텍스트.setText(listJolup4[1]);
                jolup4 = true;
                break;
            case 2:
                졸업텍스트.setText(listJolup4[2]);
                jolup4 = true;
                break;
        }

        if(!jolup1 && !jolup2 && !jolup3 && !jolup4){
            졸업텍스트.setText("졸업조건 이수가 필요합니다!!");
        }

        if((jolup1 && jolup2) || (jolup1 && jolup3) || (jolup1 && jolup4) || (jolup2 && jolup3) || (jolup2 && jolup4) || (jolup3 && jolup4)) {
            졸업텍스트.setText("졸업조건 이수완료!!");
        }

        switch (mParam2){
            case "2012":
                꿈미래텍스트.setText(JolupRequirements.selection[4] + " /  1");
                break;
            case "2013":
                꿈미래텍스트.setText(JolupRequirements.selection[4] + " /  2");
                break;
            case "2014":
                꿈미래텍스트.setText(JolupRequirements.selection[4] + " /  3");
                break;
                default:
                    꿈미래텍스트.setText(JolupRequirements.selection[4] +" /  4");
                    break;
        }
    }

    public ArrayList<String> getPoints(){
        ArrayList<String> temp = new ArrayList<>();

        temp.add("" + 전필);
        temp.add("전필");
        temp.add("" + 전선);
        temp.add("전선");

        return temp;
    }
}
