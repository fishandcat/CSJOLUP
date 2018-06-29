package com.algorithm416.csjolup;

import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.Instant;

/*
*   내일할일
*
*   어제 추가한 스피너 부분 변수 삭제
*   스피너 값 프레그먼트로 전달해서 확인
*   설정 뷰를 누를시 넘어온 정보 표기
*   스피너 부분 확인 하기
*
* */

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<CharSequence> adMajorSpin, adCurriculumSpin;

    private ConstraintLayout select;    // 레이어 변
    private Spinner MajorSpin;          // 전공 스피너
    private Spinner CurriculumSpin;     // 교육과정 스피너
    private String SaveMajor = "";           // 선택한 전공 저장
    private String SaveCurriculum = "";      // 선택한 교육과정 저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        select = (ConstraintLayout) findViewById(R.id.select);

        MajorSpin = (Spinner) findViewById(R.id.majorspinner);
        CurriculumSpin = (Spinner) findViewById(R.id.curriculumspinner);

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


    public void onClick (View v) {

        switch (v.getId()) {
            case R.id.savebtn:
                if(!SaveMajor.equals("") && !SaveCurriculum.equals("")) {
                    Toast.makeText(MainActivity.this, "저장완료!", Toast.LENGTH_SHORT).show();
                    select.setVisibility(View.GONE);
                    FragmentManager curriculumFragmentManager = getSupportFragmentManager();
                    FragmentTransaction curriculumFragmentTransaction = curriculumFragmentManager.beginTransaction();
                    curriculumFragmentTransaction.replace(R.id.fragment, curriculum.newInstance(SaveCurriculum, SaveMajor));
                    curriculumFragmentTransaction.commit();
                }
                else if (SaveMajor.equals("")){
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
                else if (SaveCurriculum.equals("")){
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
