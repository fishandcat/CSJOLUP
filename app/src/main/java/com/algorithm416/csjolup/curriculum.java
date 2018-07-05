package com.algorithm416.csjolup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link curriculum.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link curriculum#newInstance} factory method to
 * create an instance of this fragment.
 */
public class curriculum extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Button Majorbtn;            // 전공 버튼
    private Button LiberalArtsbtn;      // 교양 버튼
    private Button Jolupbtn;

    private String mParam1;
    private String mParam2;

    private Major major;                // 전공 프래그먼트
    private Liberal_arts liberalArts;   // 교양 프래그먼트
    private JolupRequirements jolupRequirements;    // 졸업인증 프래그먼트

    private ViewPager viewPager;

    private PagerAdapter ViewAdapter;

    public static RelativeLayout Changefrag;  // 프래그먼트 변환용 레이아웃
    public static FrameLayout CurriView;      // 졸업관리 프래그먼트 UI 레이아웃

    private OnFragmentInteractionListener mListener;

    public curriculum() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment curriculum.
     */
    // TODO: Rename and change types and number of parameters
    public static curriculum newInstance(String param1, String param2) {
        curriculum fragment = new curriculum();
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
        major = Major.newInstance(mParam1, mParam2);
        liberalArts = Liberal_arts.newInstance(mParam1, mParam2);
        jolupRequirements = JolupRequirements.newInstance(mParam1,mParam2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_curriculum, container, false);


        Majorbtn = (Button) view.findViewById(R.id.majorButton);
        LiberalArtsbtn = (Button) view.findViewById(R.id.liberalArtButton);
        Jolupbtn = (Button) view.findViewById(R.id.jolupButton);

        Changefrag = (RelativeLayout) view.findViewById(R.id.fragment);
        CurriView = (FrameLayout) view.findViewById(R.id.CurriView);

        viewPager = (ViewPager) view.findViewById(R.id.pager);

        viewPager.setAdapter(ViewAdapter);
        viewPager.setCurrentItem(0);

        viewPager.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int tag = (int)view.getTag();
                viewPager.setCurrentItem(tag);
            }
        });

        // 전공 버튼 클릭시 프레그먼트 변경
        Majorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tag = (int)v.getTag();
                viewPager.setCurrentItem(tag);
                CurriView.setVisibility(View.GONE);
                Changefrag.setVisibility(View.VISIBLE);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, major).commit();
            }
        });

        // 교양 버튼 클릭시 프레그먼트 변경
        LiberalArtsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tag = (int)v.getTag();
                viewPager.setCurrentItem(tag);
                CurriView.setVisibility(View.GONE);
                Changefrag.setVisibility(View.VISIBLE);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, liberalArts).commit();
            }
        });

        Jolupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tag = (int)v.getTag();
                viewPager.setCurrentItem(tag);
                CurriView.setVisibility(View.GONE);
                Changefrag.setVisibility(View.VISIBLE);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, jolupRequirements).commit();
            }
        });

        Majorbtn.setTag(0);
        LiberalArtsbtn.setTag(1);
        Jolupbtn.setTag(2);

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
        }
        else {
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
}
