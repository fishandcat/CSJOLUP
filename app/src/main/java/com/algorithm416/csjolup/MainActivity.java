package com.algorithm416.csjolup;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.Instant;

/*
*   내일할일
*
*   스피너 값 프레그먼트로 전달해서 확인
*   설정 뷰를 누를시 넘어온 정보 표기
*   스피너 부분 확인 하기
*
* */

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<CharSequence> adMajorSpin, adCurriculumSpin;   // 스피너 어뎁터
    private ArrayAdapter<String> adDraList;                             // 드로우 리스트 어뎁터
    private String [] MenuItem = new String[]{"졸업관리", "학점관리"};      // 네비게이션 메뉴 이름

    private ConstraintLayout ChangeView;        // 레이어
    private Spinner MajorSpin;                  // 전공 스피너
    private Spinner CurriculumSpin;             // 교육과정 스피너
    private String SaveMajor = "";              // 선택한 전공 저장
    private String SaveCurriculum = "";         // 선택한 교육과정 저장
    private boolean checkstate = false;

    private DrawerLayout DrLay;                 // 드로우 레이아웃
    private ActionBarDrawerToggle ActBarDraTog; // 엑션바 드로우 토클
    private ListView listView;                  // 리스트뷰

    private curriculum currfragment;            // 커리큘럼 프래그먼트
    private Major majorfragment;                // 전공 프래그먼트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChangeView = (ConstraintLayout) findViewById(R.id.screenView);

        MajorSpin = (Spinner) findViewById(R.id.majorspinner);
        CurriculumSpin = (Spinner) findViewById(R.id.curriculumspinner);

        currfragment = curriculum.newInstance(SaveCurriculum,SaveMajor);
        majorfragment = Major.newInstance("ttt", "no");

        DrLay = (DrawerLayout) findViewById(R.id.DrawListView);
        listView = (ListView) findViewById(R.id.listview);
        /*// 프레그먼트 첫 화면 셋팅 (지금 안씀)
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, currfragment).commit();
        */

        DrLay.setVisibility(View.GONE);

        // 리스트뷰
        adDraList = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, MenuItem);
        listView.setAdapter(adDraList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ChangeView.setVisibility(View.GONE);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout, currfragment).commit();
                        break;
                    case 1:
                        ChangeView.setVisibility(View.GONE);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout, majorfragment).commit();
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
        adMajorSpin = ArrayAdapter.createFromResource(this, R.array.major_names, android.R.layout.simple_spinner_dropdown_item);
        adMajorSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MajorSpin.setAdapter(adMajorSpin);

        adCurriculumSpin = ArrayAdapter.createFromResource(MainActivity.this, R.array.stu_num, android.R.layout.simple_spinner_dropdown_item);
        adCurriculumSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CurriculumSpin.setAdapter(adCurriculumSpin);

        MajorSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    SaveMajor = "";
                    SaveCurriculum = "";
                }
                else{
                    SaveMajor = adMajorSpin.getItem(position).toString();

                    CurriculumSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if(position == 0) {
                                SaveCurriculum = "";
                            }
                            else {
                                SaveCurriculum = adCurriculumSpin.getItem(position).toString();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            CurriculumSpin.setSelection(0);
                            SaveCurriculum = "";
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SaveMajor = "";
                SaveCurriculum = "";
            }
        });
    }

    /*// 액션바 적용안됨!?
    private void hidenActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.hide();
        }
    }*/

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
                Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick (View v) {

        switch (v.getId()) {
            case R.id.savebtn:
                if (!SaveMajor.equals("") && !SaveCurriculum.equals("")) {
                    checkstate = true;
                    DrLay.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "저장완료!", Toast.LENGTH_SHORT).show();
                    ChangeView.setVisibility(View.GONE);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout, currfragment).commit();
                }
                else if (SaveMajor.equals("")) {
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
                else if (SaveCurriculum.equals("")) {
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

    

}
