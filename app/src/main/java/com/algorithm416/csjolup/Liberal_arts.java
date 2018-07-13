package com.algorithm416.csjolup;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Liberal_arts.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Liberal_arts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Liberal_arts extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listView;
    private LectureAdapter lectureAdapter;
    private static ArrayList<Lecture> list = new ArrayList<>();
    private ArrayAdapter<String> adapterLiberalArts;
    private Spinner spinner_kcc;
    private String[] menu = {"교양부분을 선택하세요", "KCC 비인증", "교양과목"};
    private boolean isKCC;

    private CurriculumDB curriculumDB;

    private OnFragmentInteractionListener mListener;

    public Liberal_arts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Liberal_arts.
     */
    // TODO: Rename and change types and number of parameters
    public static Liberal_arts newInstance(String param1, String param2) {
        Liberal_arts fragment = new Liberal_arts();
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
        isKCC = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_liberal_arts, container, false);

        listView = view.findViewById(R.id.liberal_arts_list);
        lectureAdapter = new LectureAdapter(getContext(), R.layout.listview_lecture, list);
        listView.setAdapter(lectureAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list.get(i).isLecture()){
                    boolean b = !list.get(i).getItemCheck();
                    list.get(i).setItemCheck(b);
                    if (b){
                        Lecture temp = new Lecture(list.get(i));
                        list.add(0, temp);
                        lectureAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


        // 검색 조건 필터 kcc/일반적인 교양
        spinner_kcc = (Spinner)view.findViewById(R.id.spinner_kcc);
        adapterLiberalArts = new ArrayAdapter<String> (getContext(), android.R.layout.simple_list_item_1, menu);
        spinner_kcc.setAdapter(adapterLiberalArts);
        spinner_kcc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 1:
                        isKCC = true;
                        break;
                    case 2:
                        isKCC = false;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinner_kcc.setSelection(0);
            }
        });
        //curriculumDB = new CurriculumDB(getContext());

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
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
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
