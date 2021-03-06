package com.algorithm416.csjolup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JolupRequirements.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JolupRequirements#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JolupRequirements extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayAdapter<CharSequence> adjoup1, adjoup2, adjoup3, adjoup4, adjoup5;   // 스피너 어뎁터

    private Spinner jolsp1;         // 사회봉사 스피너
    private Spinner jolsp2;         // 글로벌 리더쉽 스피너
    private Spinner jolsp3;         // 독서 스피너
    private Spinner jolsp4;         // GNU인성 스피너
    private Spinner jolsp5;         // 꿈/미래개척

    private curriculum curriculum;

    static public int[] selection = new int[5];

    public JolupRequirements() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JolupRequirements.
     */
    // TODO: Rename and change types and number of parameters
    public static JolupRequirements newInstance(String param1, String param2) {
        JolupRequirements fragment = new JolupRequirements();
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
        curriculum = curriculum.newInstance(mParam1, mParam2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jolup_requirements, container, false);

        jolsp1 = (Spinner) view.findViewById(R.id.Jolupsp1);
        jolsp2 = (Spinner) view.findViewById(R.id.Jolupsp2);
        jolsp3 = (Spinner) view.findViewById(R.id.Jolupsp3);
        jolsp4 = (Spinner) view.findViewById(R.id.Jolupsp4);
        jolsp5 = (Spinner) view.findViewById(R.id.Jolupsp5);

        adjoup1 = ArrayAdapter.createFromResource(this.getActivity(), R.array.jolup1, android.R.layout.simple_spinner_item);
        adjoup1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        jolsp1.setAdapter(adjoup1);

        jolsp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selection[0] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adjoup2 = ArrayAdapter.createFromResource(this.getActivity(), R.array.jolup2, android.R.layout.simple_spinner_item);
        adjoup2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        jolsp2.setAdapter(adjoup2);

        jolsp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selection[1] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adjoup3 = ArrayAdapter.createFromResource(this.getActivity(), R.array.jolup3, android.R.layout.simple_spinner_item);
        adjoup3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        jolsp3.setAdapter(adjoup3);

        jolsp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selection[2] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adjoup4 = ArrayAdapter.createFromResource(this.getActivity(), R.array.jolup4, android.R.layout.simple_spinner_item);
        adjoup4.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        jolsp4.setAdapter(adjoup4);
        jolsp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selection[3] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adjoup5 = ArrayAdapter.createFromResource(this.getActivity(), R.array.jolup5, android.R.layout.simple_spinner_item);
        adjoup5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        jolsp5.setAdapter(adjoup5);
        jolsp5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selection[4] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        jolsp1.setSelection(selection[0]);
        jolsp2.setSelection(selection[1]);
        jolsp3.setSelection(selection[2]);
        jolsp4.setSelection(selection[3]);
        jolsp5.setSelection(selection[4]);

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
