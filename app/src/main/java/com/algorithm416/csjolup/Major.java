package com.algorithm416.csjolup;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Major.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Major#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Major extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listView;
    private LectureAdapter lectureAdapter;
    private static ArrayList<Lecture> list = new ArrayList<>();
    private ArrayAdapter<CharSequence> adapterCurriculum;
    private Spinner curriculum_cs;
    static boolean bReload = false;
    private CurriculumDB db;

    public Major() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Major.
     */
    // TODO: Rename and change types and number of parameters
    public static Major newInstance(String param1, String param2) {
        Major fragment = new Major();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_major, container, false);

        curriculum_cs = (Spinner) view.findViewById(R.id.curriculum_cs);
        adapterCurriculum = ArrayAdapter.createFromResource(getContext(), R.array.stu_num, R.layout.support_simple_spinner_dropdown_item);
        curriculum_cs.setAdapter(adapterCurriculum);
        curriculum_cs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (view == null) {
                    bReload = true;
                    return;
                }
                if (!bReload) {
                    // 새로 불러올때 체크한 부분 제외 삭제함
                    if (list.size() > 0) {
                        for (int n = 0; n < list.size(); n++) {
                            if (list.get(n).isLecture()) {
                                if (!list.get(n).getItemCheck()) {
                                    list.remove(n--);
                                }
                            } else {
                                list.remove(n--);
                            }
                        }
                    }

                    if (i == 0) {
                        i = adapterCurriculum.getPosition(mParam2);
                        curriculum_cs.setSelection(i);
                    }

                    ConnectDB(adapterCurriculum.getItem(i).toString());
                }
                bReload = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                curriculum_cs.setSelection(adapterCurriculum.getPosition(mParam2));
            }
        });

        curriculum_cs.setSelection(adapterCurriculum.getPosition(mParam2));

        listView = (ListView) view.findViewById(R.id.major_list);
        lectureAdapter = new LectureAdapter(getContext(), R.layout.listview_lecture, list);
        listView.requestFocus();
        listView.setAdapter(lectureAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                list.get(i).setItemCheck(!list.get(i).getItemCheck());
                lectureAdapter.notifyDataSetChanged();
            }
        });

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

    // DB에서 자료를 갖고와 리스트에 등록한 뒤 어댑터를 갱신한다.
    private void ConnectDB(String year){
        String[][] table = db.getMajor(year);
        int grade = 0, type = 0, num = 0, name = 0, credit = 0, exist = 0;

        int grade_count = 0;

        for (int i = 0; i < table.length; i++) {
            switch(table[i][0]){
                case "lecture_type":
                    type = i;
                    break;
                case "lecture_num":
                    num = i;
                    break;
                case "lecture_name":
                    name = i;
                    break;
                case "credit":
                    credit = i;
                    break;
                case "grade":
                    grade = i;
                    break;
                case "is_exist":
                    exist = i;
            }
        }

        for (int i = 1; i < table[0].length; i++) {
            if (grade_count != Integer.parseInt(table[grade][i])) {
                grade_count = Integer.parseInt(table[grade][i]);
                int temp = grade_count % 10;
                String str = String.valueOf(grade_count / 10) + "학년 " + (temp % 2 != 0 ? String.valueOf(temp / 3 + 1) + "학기" : (temp / 2 == 1 ? "하계" : "동계"));
                list.add(new Lecture(str));
            }

            // 본 함수가 다시 불러질 때 체크되어 있는 항목이 이미 존재하고 있으므로
            // 그 부분을 확인하여 리스트뷰에 등록하지 않는다.
            boolean bReload = false;
            for (int j = 0; j < list.size(); j++){
                if (list.get(j).isLecture()) {
                    if (list.get(j).getLectureNum().equals(table[num][i]) && list.get(j).getGrade().equals(table[grade][i])) {
                        bReload = true;
                        break;
                    }
                }
            }

            if (!bReload) {
                if (table[exist][i].equals("N"))
                    list.add(new Lecture(table[grade][i], table[type][i] + " (사라짐)", table[num][i], table[name][i], table[credit][i]));
                else
                    list.add(new Lecture(table[grade][i], table[type][i], table[num][i], table[name][i], table[credit][i]));
            }

        }
        lectureAdapter.notifyDataSetChanged();
    }

    public static ArrayList<Lecture> getList() {
        return list;
    }
}
