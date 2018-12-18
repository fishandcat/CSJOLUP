package com.algorithm416.csjolup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



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
        JolupPoint.OnFragmentInteractionListener,
        TeachingPoint.OnFragmentInteractionListener
{

    private ArrayAdapter<CharSequence> adMajorSpin, adCurriculumSpin;   // 스피너 어뎁터
    private ArrayAdapter<String> adDraList;                             // 드로우 리스트 어뎁터
    private String [] MenuItem = new String[]{"졸업관리", "학점관리"};      // 네비게이션 메뉴 이름

    private ConstraintLayout ScreenView;        // 컨스트레인 레이아웃
    private Spinner MajorSpin;                  // 전공 스피너
    private Spinner CurriculumSpin;             // 교육과정 스피너

    private Toolbar myToolbar;

    private CheckBox checkBox;

    private String SaveMajor = "";              // 선택한 전공 저장
    private String SaveCurriculum = "";         // 선택한 교육과정 저장

    private boolean spinerCheck1 = false;
    private boolean spinerCheck2 = false;
    static public boolean bBtnSave = false;
    static public boolean bSaveLoad = false;
    static public boolean bTeachingCheck = false;

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

    private Handler m_close_handler;
    private boolean m_close_flag = false;

    public MainActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        m_close_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 0) {
                    m_close_flag = false;
                }
                super.handleMessage(msg);
            }
        };

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

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        checkBox = (CheckBox) findViewById(R.id.TeachingCheck);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bTeachingCheck = b;
            }
        });

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

        // 저장된 걸 불러오는 기능
        ArrayList<String> temp;
        SaveXML xml = new SaveXML(this);
        temp = xml.getData("CurriculumYear");

        if (temp != null && !bSaveLoad) {
            bSaveLoad = true;
            bBtnSave = true;
            SaveMajor = temp.get(0);
            SaveCurriculum = temp.get(1);
            spinerCheck1 = spinerCheck2 = true;

            ArrayList<Lecture> forReplace;
            ArrayList<Lecture> list = Major.getList();
            temp = xml.getData("Major");
            forReplace = ConnectDB(SaveCurriculum, "Major", temp);
            list.addAll(forReplace);

            temp = xml.getData("Teaching");
            if (temp != null)
                bTeachingCheck = Boolean.parseBoolean(temp.get(0));

            list = Liberal_arts.getList();
            temp = xml.getData("Liberal_Arts");
            forReplace = ConnectDB(SaveCurriculum, "Liberal_Arts", temp);
            list.addAll(forReplace);

            int []nJolupRequirement = JolupRequirements.selection;
            temp = xml.getData("JolupRequirements");

            for (int i = 0; i < temp.size(); i++) {
                nJolupRequirement[i] = Integer.parseInt(temp.get(i));
            }

            bBtnSave = true;

            ScreenView.setVisibility(View.GONE);
            DrLay.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "불러오기 완료!", Toast.LENGTH_SHORT).show();
            MajorText.setText(SaveMajor);
            CurriculumText.setText(SaveCurriculum + " 교육과정");
            currfragment = curriculum.newInstance(SaveMajor,SaveCurriculum);
            grades = Grades.newInstance(SaveMajor,SaveCurriculum);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Fragment, currfragment).commit();
        }

        // 툴바 메뉴 추가
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menuicon_w);

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
                settingintent.putExtra("MainActivity", this.getIntent());
                startActivity(settingintent);
                return true;
            case android.R.id.home:
                if(bBtnSave) {
                    if (!DrLay.isDrawerOpen(Gravity.LEFT))
                        DrLay.openDrawer(Gravity.LEFT);
                    else
                        DrLay.closeDrawer(Gravity.LEFT);
                }
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
//                    if(checkBox.isChecked()) {
//                        bTeachingCheck = true;
//                        checkBox.setChecked(true);
//                    }
//                    else{
//                        bTeachingCheck = false;
//                        checkBox.setChecked(false);
//                    }
                    MajorText.setText(SaveMajor);
                    CurriculumText.setText(SaveCurriculum + " 교육과정");
                    currfragment = curriculum.newInstance(SaveMajor,SaveCurriculum);
                    grades = Grades.newInstance(SaveMajor,SaveCurriculum);
                    bBtnSave = true;
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

    private ArrayList<Lecture> ConnectDB(String year, String tag,ArrayList<String> saveString){
        CurriculumDB db = new CurriculumDB(this);
        String[][] table;

        ArrayList<Lecture> list = new ArrayList<>();

        if (tag.equals("Major")) { // 전공 부분
            table = db.getMajor(year);

            int grade = 0, type = 0, num = 0, name = 0, credit = 0, exist = 0;

            int grade_count = 0;
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

                if (table[exist][i].equals("N"))
                    list.add(new Lecture(table[grade][i], table[type][i] + " (사라짐)", table[num][i], table[name][i], table[credit][i]));
                else
                    list.add(new Lecture(table[grade][i], table[type][i], table[num][i], table[name][i], table[credit][i]));

                for (int j = 0; j < saveString.size(); j++) {
                    if (table[num][i].equals(saveString.get(j))) {
                        list.get(list.size() - 1).setItemCheck(true);
                        break;
                    }
                }
            }

        } else { // 교양부분

            for (int k = Integer.parseInt(year); k < Integer.parseInt("2018"); k++) {
                switch (Integer.toString(k)) {
                    case "2012":
                    case "2013":
                    case "2014":
                    case "2015":
                        table = db.getKCC(year);
                        break;
                    default:
                        table = db.SearchLecture("*", 300, CurriculumDB.LIBERAL_ARTS);
                        break;
                }

                int type = 0, num = 0, name = 0, credit = 0;

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
                if (saveString.size() > 0) {
                    for (int i = 1; i < table[0].length; i++) {
                        for (int j = 0; j < saveString.size(); j++) {
                            if (table[num][i].equals(saveString.get(j))) {
                                list.add(new Lecture("", table[type][i], table[num][i], table[name][i], table[credit][i]));
                                list.get(list.size() - 1).setItemCheck(true);
                                saveString.remove(j);
                                break;
                            }
                        }
                    }
                }

            }
        }
        db.close();

        return list;
    }

    public void onSave() {
        SaveXML xml = new SaveXML(this);

        if (bBtnSave) {
            xml.clear();
            String[] CurriculumYear = new String[2];
            CurriculumYear[0] = SaveMajor;
            CurriculumYear[1] = SaveCurriculum;

            xml.saveData("CurriculumYear", CurriculumYear);
            xml.saveData("Teaching", bTeachingCheck);
            xml.saveData("Major", Major.getList());
            xml.saveData("Liberal_Arts", Liberal_arts.getList());
            xml.saveData("JolupRequirements", JolupRequirements.selection);
        }
    }

    @Override
    protected void onPause() {
        onSave();

        super.onPause();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(event.getKeyCode()==KeyEvent.KEYCODE_MENU) {
            return super.onKeyUp(keyCode, event);
        } else {
            if(event.getKeyCode()==KeyEvent.KEYCODE_BACK) {
                if(!m_close_flag) {
                    Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
                    m_close_flag = true;
                    m_close_handler.sendEmptyMessageDelayed(0, 500);
                    return false;
                } else {
                    onSave();

                    Major.getList().clear();
                    Liberal_arts.getList().clear();
                    for (int i = 0; i < JolupRequirements.selection.length; i++)
                        JolupRequirements.selection[i] = 0;

                    bSaveLoad = false;
                    bBtnSave = false;

                    finish();
                }
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }
}