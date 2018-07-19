package com.algorithm416.csjolup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.PieEntry;

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

    private SectionsPagerAdapter pagerAdapter;

    private ViewPager viewPager;

    public Grades() {
        // Required empty public constructor
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

        pagerAdapter = new SectionsPagerAdapter(getFragmentManager(), mParam1, mParam2);

        pagerAdapter.insertItem("JolupPoint");
        pagerAdapter.insertItem("MajorPoint");
        pagerAdapter.insertItem("LecturePoint");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_grades, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.GradesPager);

        ArrayList<String> temp = new ArrayList<>();
        MajorPoint mp = new MajorPoint();
        mp.Majorpointsum();
        LecturePoint lp = new LecturePoint();
        lp.Lecturepointsum(mParam2);

        temp.addAll(mp.getPoints());
        temp.addAll(lp.getPoints(mParam2, getContext()));

        JolupPoint.pieEntries.clear();
        int entry[] = new int[3];
        for (int i = 0, k = 0; i < temp.size() - 1; i += 2) {
            entry[k] = Integer.parseInt(temp.get(i));
            JolupPoint.pieEntries.add(new PieEntry(entry[k++], temp.get(i + 1)));
        }

        final int graduate = Integer.parseInt(temp.get(temp.size() - 1));
        int sum = graduate - (entry[0] + entry[1] + entry[2]);
        if (sum > 0) {
            JolupPoint.pieEntries.add(new PieEntry(sum, "남은 학점"));
        }

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);

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
