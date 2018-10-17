package com.algorithm416.csjolup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Setting extends AppCompatActivity {

    private Intent mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Intent intent = this.getIntent();
        mainActivity = (Intent)intent.getExtras().get("MainActivity");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                SaveXML xml = new SaveXML(this);

                Intent intent = new Intent(mainActivity).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Major.getList().clear();
                Liberal_arts.getList().clear();
                for (int i = 0; i < JolupRequirements.selection.length; i++)
                    JolupRequirements.selection[i] = 0;
                MainActivity.bBtnSave = false;
                xml.clear();
                startActivity(intent);
                finish();
                break;
        }
    }
}
