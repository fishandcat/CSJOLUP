package com.algorithm416.csjolup;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.jar.Attributes;

import static android.view.View.GONE;


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
    private String mParam1;
    private String mParam2;

    private InputMethodManager inputMethodManager;

    private ViewPager viewPager;
    private SectionsPagerAdapter pagerAdapter;

    private TabLayout tabLayout;

    public FrameLayout Changefrag;  // 프래그먼트 변환용 레이아웃
    public RelativeLayout CurriView;      // 졸업관리 프래그먼트 UI 레이아웃

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

        // 키보드 숨기기 위한 관리변수
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_curriculum, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            // 뒤로가기 버튼 기능
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (inputMethodManager.isActive())
                            return true;
//                        if (CurriView.getVisibility() == View.VISIBLE) {
//                            getActivity().onBackPressed();
//                            return false;
//                        } else {
//                            Changefrag.setVisibility(View.GONE);
//                            CurriView.setVisibility(View.VISIBLE);
//                        }
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });

        Changefrag = (FrameLayout) view.findViewById(R.id.fragment);
        CurriView = (RelativeLayout) view.findViewById(R.id.CurriView);

        Changefrag.setVisibility(View.VISIBLE);
        CurriView.setVisibility(View.GONE);

        pagerAdapter = new SectionsPagerAdapter(getFragmentManager(), mParam1, mParam2);
        pagerAdapter.insertItem("Major");
        pagerAdapter.insertItem("Liberal_arts");
        pagerAdapter.insertItem("JolupRequirements");

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                viewPager.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.destroyDrawingCache();
        pagerAdapter.notifyDataSetChanged();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.textView:
                        Changefrag.setVisibility(View.VISIBLE);
                        CurriView.setVisibility(View.GONE);
                        break;
                }
            }
        };

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
