package com.algorithm416.csjolup;

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
 * {@link TeachingPoint.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeachingPoint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeachingPoint extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private CurriculumDB db;

    private TextView teachingText[], teachingNum[];


    public TeachingPoint() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeachingPoint.
     */
    // TODO: Rename and change types and number of parameters
    public static TeachingPoint newInstance(String param1, String param2) {
        TeachingPoint fragment = new TeachingPoint();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teachingpoint, container, false);

        db = new CurriculumDB(getContext());

        teachingText = new TextView[3];
        teachingNum = new TextView[3];

        teachingText[0] = view.findViewById(R.id.TeachingText1);
        teachingNum[0] = view.findViewById(R.id.TeachingNum1);

        teachingText[1] = view.findViewById(R.id.TeachingText2);
        teachingNum[1] = view.findViewById(R.id.TeachingNum2);

        teachingText[2] = view.findViewById(R.id.TeachingText3);
        teachingNum[2] = view.findViewById(R.id.TeachingNum3);

        TeachingSort();

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

    public void TeachingSort() {
        String table[][] = db.getTeachingCourse();
        ArrayList<Lecture> list = Liberal_arts.getList();

        int 이론 = 0, 소양 = 0, 실습 = 0;

        if (!list.isEmpty()) {

            int type = 0, num = 0, name = 0, credit = 0;

            for (int i = 0; i < table.length; i++) {
                switch (table[i][0]) {
                    case "type":
                        type = i;
                        break;
                    case "lecture_num":
                        num = i;
                        break;
                    case "credit":
                        credit = i;
                        break;
                }
            }

            for (int i = 1; i < table[0].length; i++) {
                boolean bHit = false;
                for (int j = 0; j < list.size(); j++) {
                    Lecture lecture = list.get(j);
                    if (table[num][i].compareTo(lecture.getLectureNum()) == 0) {
                        switch (table[type][i]) {
                            case "이론":
                                bHit = true;
                                이론 += Integer.parseInt(table[credit][i]);
                                break;
                            case "소양":
                                bHit = true;
                                소양 += Integer.parseInt(table[credit][i]);
                                break;
                            case "실습":
                                bHit = true;
                                실습 += Integer.parseInt(table[credit][i]);
                                break;
                        }
                        if (bHit)
                            break;
                    }
                }
            }
        }

        table = db.getTeachingCourse(true);

        int theory = 0, refinement = 0, practice = 0;
        for (int i = 0; i < table.length; i++) {
            switch (table[i][0]) {
                case "theory":
                    theory = Integer.parseInt(table[i][1]);
                    break;
                case "refinement":
                    refinement = Integer.parseInt(table[i][1]);
                    break;
                case "practice":
                    practice = Integer.parseInt(table[i][1]);
                    break;
            }
        }

        teachingNum[0].setText(이론 + " / " + theory);
        teachingNum[1].setText(소양 + "/" + refinement);
        teachingNum[2].setText(실습 + "/" + practice);
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
