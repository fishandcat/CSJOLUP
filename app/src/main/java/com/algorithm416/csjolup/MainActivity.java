package com.algorithm416.csjolup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Instant;

/*
*   TODO: 내일할일
*
*   설정 엑티비티 구성하기 (학점관리 학점 텍스트로 표시, 그래프 표시 설정, 초기화 버튼 구현)
*   엑션바에 메뉴 토글 구현하기
*   학점관리 페이지구성
*   디자인구성하기
*
*   졸업관리뷰 프레임 레이아웃 수정하기
*
* */

public class MainActivity extends AppCompatActivity implements
        Major.OnFragmentInteractionListener,
        Liberal_arts.OnFragmentInteractionListener,
        JolupRequirements.OnFragmentInteractionListener,
        Grades.OnFragmentInteractionListener,
        curriculum.OnFragmentInteractionListener,
        MajorPoint.OnFragmentInteractionListener,
        LecturePoint.OnFragmentInteractionListener,
        JolupPoint.OnFragmentInteractionListener
{

    private ArrayAdapter<CharSequence> adMajorSpin, adCurriculumSpin;   // 스피너 어뎁터
    private ArrayAdapter<String> adDraList;                             // 드로우 리스트 어뎁터
    private String [] MenuItem = new String[]{"졸업관리", "학점관리"};      // 네비게이션 메뉴 이름

    private ConstraintLayout ScreenView;        // 컨스트레인 레이아웃
    private Spinner MajorSpin;                  // 전공 스피너
    private Spinner CurriculumSpin;             // 교육과정 스피너
    private String SaveMajor = "";              // 선택한 전공 저장
    private String SaveCurriculum = "";         // 선택한 교육과정 저장

    private boolean spinerCheck1 = false;
    private boolean spinerCheck2 = false;

    private Intent settingintent;               // 세팅 엑티비티 인텐트

    private DrawerLayout DrLay;                 // 네비게이션 메인 레이아웃
    private ActionBarDrawerToggle ActBarDraTog; // 엑션바 드로우 토클
    private ListView listView;                  // 리스트뷰 레이아웃
    private FrameLayout FragmentLayout;         // 프레그먼트 변경 레이아웃
    private View Heder;                         // 네비게이션 헤더 뷰

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private curriculum currfragment;            // 커리큘럼 프래그먼트
    private Grades grades;                      // 학점관리 프래그먼트

    private TextView MajorText;                 // 전공 텍스트
    private TextView CurriculumText;            // 교육과정 텍스트

    public MainActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        MajorSpin = (Spinner) findViewById(R.id.majorspinner);
        CurriculumSpin = (Spinner) findViewById(R.id.curriculumspinner);

        ScreenView = (ConstraintLayout) findViewById(R.id.screenView);
        DrLay = (DrawerLayout) findViewById(R.id.DrawListView);
        listView = (ListView) findViewById(R.id.listview);
        FragmentLayout = (FrameLayout) findViewById(R.id.Fragment);
        Heder = getLayoutInflater().inflate(R.layout.listview_header, null, false);

        settingintent = new Intent(MainActivity.this, Setting.class);

        MajorText = (TextView) Heder.findViewById(R.id.majortext);
        CurriculumText = (TextView) Heder.findViewById(R.id.curriculumtext);

        sp = getSharedPreferences("savefile", MODE_PRIVATE);
        editor = sp.edit();


        DrLay.setVisibility(View.GONE);

        // 네비게이션 리스트뷰
        listView.addHeaderView(Heder);
        adDraList = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, MenuItem);
        listView.setAdapter(adDraList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 1:
                        Toast.makeText(MainActivity.this, "졸업관리", Toast.LENGTH_SHORT).show();
                        currfragment = curriculum.newInstance(SaveMajor,SaveCurriculum);
                        //currfragment.CurriView.setVisibility(View.VISIBLE);
                        //currfragment.Changefrag.setVisibility(View.GONE);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.Fragment, currfragment).commit();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "학점관리", Toast.LENGTH_SHORT).show();
                        grades =  Grades.newInstance(SaveMajor,SaveCurriculum);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.Fragment, grades).commit();
                        break;
                }
                DrLay.closeDrawer(listView);
            }
        });

        /*// 네비게이션 드로우 토글 아이콘
        DrLay = (DrawerLayout) findViewById(R.id.screenView);
        ActBarDraTog = new ActionBarDrawerToggle(MainActivity.this, DrLay, R.drawable, R.string.app_name, R.string.app_name);
        DrLay.setDrawerListener(ActBarDraTog);
        */

        // 전공 스피너
        adMajorSpin = ArrayAdapter.createFromResource(MainActivity.this, R.array.major_names, android.R.layout.simple_spinner_dropdown_item);
        adMajorSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MajorSpin.setAdapter(adMajorSpin);

        adCurriculumSpin = ArrayAdapter.createFromResource(MainActivity.this, R.array.stu_num, android.R.layout.simple_spinner_dropdown_item);
        adCurriculumSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CurriculumSpin.setAdapter(adCurriculumSpin);

        MajorSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    spinerCheck1 = false;
                    SaveMajor = "";
                    SaveCurriculum = "";
                }
                else {
                    SaveMajor = adMajorSpin.getItem(position).toString();
                    spinerCheck1 = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                CurriculumSpin.setSelection(0);
                SaveMajor = "";
                SaveCurriculum = "";
                spinerCheck1 = false;
            }
        });

        CurriculumSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    spinerCheck2 = false;
                    SaveCurriculum = "";
                }
                else {
                    SaveCurriculum = adCurriculumSpin.getItem(position).toString();
                    spinerCheck2 = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                CurriculumSpin.setSelection(0);
                SaveCurriculum = "";
                spinerCheck2 = false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.action_setting:
                startActivity(settingintent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick (View v) {

        switch (v.getId()) {
            case R.id.savebtn:
                if (spinerCheck1 && spinerCheck2) {
                    ScreenView.setVisibility(View.GONE);
                    DrLay.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "저장완료!", Toast.LENGTH_SHORT).show();
                    MajorText.setText(SaveMajor);
                    CurriculumText.setText(SaveCurriculum + " 교육과정");
                    currfragment = curriculum.newInstance(SaveMajor,SaveCurriculum);
                    grades = Grades.newInstance(SaveMajor,SaveCurriculum);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.Fragment, currfragment).commit();
                }
                else if (!spinerCheck1) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                    // 제목셋팅
                    alertDialogBuilder.setTitle("전공이 비어있습니다!");

                    // AlertDialog 셋팅
                    alertDialogBuilder
                            .setMessage("전공을 선택해주세요")
                            .setCancelable(false)
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            // 다이얼로그를 취소한다
                                            dialog.cancel();
                                        }
                                    });

                    // 다이얼로그 생성
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // 다이얼로그 보여주기
                    alertDialog.show();
                }
                else if (!spinerCheck2) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                    // 제목셋팅
                    alertDialogBuilder.setTitle("교육과정이 비어있습니다!");

                    // AlertDialog 셋팅
                    alertDialogBuilder
                            .setMessage("교육과정을 선택해주세요")
                            .setCancelable(false)
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            // 다이얼로그를 취소한다
                                            dialog.cancel();
                                        }
                                    });

                    // 다이얼로그 생성
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // 다이얼로그 보여주기
                    alertDialog.show();
                }
                break;

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }

}