package com.algorithm416.csjolup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


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

    // 체크된 강의들
    private ListView listView;
    private LectureAdapter lectureAdapter;
    private static ArrayList<Lecture> list = new ArrayList<>();
    private int isDelete = -1;

    // 검색시 출력되는 강의들
    private ArrayList<Lecture> listSearch;
    private ListView searchView;
    private LectureAdapter searchAdapter;

    // 검색을 위한 보관용 강의들
    private ArrayList<Lecture> listLectures;

    private Spinner spinner_kcc;
    private String[] menu;
    private ArrayAdapter<String> adapterLiberalArts;
    private boolean isKCC;

    private CustomEditText searchText;

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

        listLectures = new ArrayList<>();
        curriculumDB = new CurriculumDB(getContext());

        // 체크된 강의들
        listView = view.findViewById(R.id.liberal_arts_list);
        lectureAdapter = new LectureAdapter(getContext(), R.layout.listview_lecture, list);
        listView.setAdapter(lectureAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list.get(i).isLecture()) {
                    isDelete = i;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    alertDialogBuilder
                            .setTitle("리스트에서 즉시 삭제됩니다.")
                            .setMessage("정말로 삭제하시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (isDelete >= 0) {
                                        list.get(isDelete).setItemCheck(!list.get(isDelete).getItemCheck());
                                        list.remove(isDelete);
                                        lectureAdapter.notifyDataSetChanged();
                                        isDelete = -1;
                                    }
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                    // 다이얼로그 생성
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // 다이얼로그 보여주기
                    alertDialog.show();
                }
            }
        });

        // 스피너에 들어갈 텍스트
        ArrayList<String> temp = new ArrayList<>();
        temp.add("선택하세요");
        switch (mParam2) {
            case "2012":
                temp.add("2012 KCC");
            case "2013":
                temp.add("2013 KCC");
            case "2014":
                temp.add("2014 KCC");
            case "2015":
                temp.add("2015 KCC");
            default:
                temp.add("교양과목");
        }
        menu = temp.toArray(new String[temp.size()]);

        // 검색 조건 필터 kcc/일반적인 교양
        spinner_kcc = (Spinner) view.findViewById(R.id.spinner_kcc);
        if (mParam2.compareTo("2016") < 0) {
            adapterLiberalArts = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, menu);
            spinner_kcc.setAdapter(adapterLiberalArts);
            spinner_kcc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    listLectures.clear();
                    listSearch.clear();
                    if (i > 0) {
                        if (adapterLiberalArts.getCount() - 1 > i) {
                            isKCC = true;
                            char[] temp = mParam2.toCharArray();
                            temp[temp.length - 1] += (i - 1);
                            ConnectDB(String.copyValueOf(temp));
                        } else {
                            isKCC = false;
                            ConnectDB();
                        }
                    }

                    if (i != 0) {
                        CheckText(searchText.getText().toString());
                    }

                    searchAdapter.notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    spinner_kcc.setSelection(0);
                    adapterLiberalArts.notifyDataSetChanged();
                }
            });
        } else {
            isKCC = false;
            spinner_kcc.setVisibility(View.GONE);
            ConnectDB();
        }

        // 검색 리스트뷰
        searchView = view.findViewById(R.id.search_list);
        listSearch = new ArrayList<>();
        searchAdapter = new LectureAdapter(getContext(), R.layout.listview_lecture, listSearch);
        searchView.setAdapter(searchAdapter);
        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listSearch.get(i).setItemCheck(!listSearch.get(i).getItemCheck());
                if (listSearch.get(i).getItemCheck()) {
                    list.add(list.size(), listSearch.get(i));
                } else {
                    list.remove(listSearch.get(i));
                }
                searchAdapter.notifyDataSetChanged();
            }
        });
        searchView.setFocusable(true);

        // 검색바
        searchText = (CustomEditText) view.findViewById(R.id.liberal_arts_search);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                CheckText(searchText.getText().toString());
            }
        });
        searchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    listView.setVisibility(View.GONE);
                    searchView.setVisibility(View.VISIBLE);
                    if (searchText.getText().toString().length() == 0) {
                        listSearch.clear();
                        listSearch.addAll(listLectures);
                        searchAdapter.notifyDataSetChanged();
                    }
                } else {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    listSearch.clear();
                    searchAdapter.notifyDataSetChanged();
                    searchView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }

            }
        });
        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    //InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    //inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    listSearch.clear();
                    searchAdapter.notifyDataSetChanged();
                    searchText.clearFocus();
                    searchView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        searchView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

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

    private void CheckText(String text) {
        listSearch.clear();
        if (text.length() == 0) {
            listSearch.addAll(listLectures);
            searchAdapter.notifyDataSetChanged();
            return;
        }

        if (listLectures.size() > 0) {
            for (int j = 0, k = 0; j < listLectures.size(); j++) {
                if (listLectures.get(j).getLectureName().toLowerCase().contains(text)) {
                    listSearch.add(listLectures.get(j));
                    if (++k > 15)
                        break;
                }
            }
        }
        searchAdapter.notifyDataSetChanged();
    }

    private void ConnectDB() {
        ConnectDB(mParam2);
    }

    private void ConnectDB(String year) {
        String[][] table = null;
        int type = 0, num = 0, name = 0, credit = 0;

        if (isKCC) {
            table = curriculumDB.getKCC(year);
        } else {
            table = curriculumDB.SearchLecture("*", 300, CurriculumDB.LIBERAL_ARTS);
        }

        for (int i = 0; i < table.length; i++) {
            switch (table[i][0]) {
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
            }
        }
        for (int i = 1; i < table[0].length; i++) {
            // 본 함수가 다시 불러질 때 체크되어 있는 항목이 이미 존재하고 있으므로
            // 그 부분을 확인하여 리스트뷰에 등록하지 않는다.
            boolean bReload = true;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).isLecture()) {
                    if (list.get(j).getLectureNum().equals(table[num][i])) {
                        bReload = false;
                        listLectures.add(list.get(j));
                        break;
                    }
                }
            }

            if (bReload) {
                listLectures.add(new Lecture("", table[type][i], table[num][i], table[name][i], table[credit][i]));
            }
        }

        lectureAdapter.notifyDataSetChanged();
    }

    private void ErrorKCC() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder
                .setTitle("공학인증 교육과정이 아닙니다.")
                .setMessage(mParam2 + "교육과정은 KCC 공학 비인증 대상 교육과정이 아닙니다.")
                .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();
    }

    public static ArrayList<Lecture> getList() {
        return list;
    }
}

class CustomEditText extends AppCompatEditText {

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        clearFocus();
        return super.onKeyPreIme(keyCode, event);
    }
}