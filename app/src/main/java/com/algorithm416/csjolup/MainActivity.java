package com.algorithm416.csjolup;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.time.Instant;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    select = (ConstraintLayout) findViewById(R.id.select);
    }


    public void onClick (View v) {

        switch (v.getId()) {
            case R.id.savebtn:
                select.setVisibility(View.GONE);
                FragmentManager curriculumFragmentManager = getSupportFragmentManager();
                FragmentTransaction curriculumFragmentTransaction = curriculumFragmentManager.beginTransaction();
                curriculumFragmentTransaction.replace(R.id.fragment, curriculum.newInstance("ttt1", "ttt2"));
                curriculumFragmentTransaction.commit();
                break;

        }
    }

}
