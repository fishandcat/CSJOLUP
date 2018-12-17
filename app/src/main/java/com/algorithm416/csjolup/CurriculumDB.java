package com.algorithm416.csjolup;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CurriculumDB extends SQLiteOpenHelper {
    private Context mycontext;

    public static final int MAJOR = 0;
    public static final int LIBERAL_ARTS = 1;

    private String DB_PATH;
    private static String DB_NAME = "Curriculum.db";  //the extension may be .sqlite or .db
    private SQLiteDatabase myDataBase;

    public CurriculumDB(Context context) {
        super(context, DB_NAME, null, 1);
        this.mycontext = context;
        DB_PATH = "/data/data/"+mycontext.getPackageName()+"/databases/";

        boolean dbexist = checkDatabase();
        if (dbexist) {
            //System.out.println("Database exists");
            openDatabase();
        } else {
            System.out.println("Database doesn't exist");
            try {
                createDatabase();
            }
            catch(IOException e) {
                Log.e("CurriculumDB", e.getMessage());
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void createDatabase() throws IOException {
        boolean dbexist = checkDatabase();
        if(dbexist) {
            Log.d("CurriculumDB", "DB Exist");
        } else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch(IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDatabase() {
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copyDatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = mycontext.getAssets().open("db/" + DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    private void openDatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    // String[][]로 테이블을 만들어 반환해준다.
    private String[][] getTable(Cursor result) {
        if (result == null || !result.moveToFirst())
            return null;

        String[][] table = new String[result.getColumnCount()][result.getCount() + 1];

        for (int i = 0; i < result.getColumnCount(); i++) {
            table[i][0] = result.getColumnName(i);
        }
        result.moveToFirst();
        int i = 1;
        do
        {
            for (int j = 0; j < result.getColumnCount(); j++) {
                table[j][i] = result.getString(j);
            }
            i++;
        }while (result.moveToNext());

        return table;
    }

    // 각 연도별 학년별 전공 필수 / 선택 과목 반환
    public String[][] getMajor(String year) {
        if (myDataBase == null)
            openDatabase();
        else if (!myDataBase.isOpen())
            openDatabase();

        Cursor result = null;

        switch (year) {
            case "2012":
                result = myDataBase.rawQuery("SELECT * FROM curriculum_cs12 JOIN lecture_cs USING (lecture_num) ORDER BY grade ASC", null);
                break;
            case "2013":
                result = myDataBase.rawQuery("SELECT * FROM curriculum_cs13 JOIN lecture_cs USING (lecture_num) ORDER BY grade ASC", null);
                break;
            case "2014":
                result = myDataBase.rawQuery("SELECT * FROM curriculum_cs14 JOIN lecture_cs USING (lecture_num) ORDER BY grade ASC", null);
                break;
            case "2015":
                result = myDataBase.rawQuery("SELECT * FROM curriculum_cs15 JOIN lecture_cs USING (lecture_num) ORDER BY grade ASC", null);
                break;
            case "2016":
                result = myDataBase.rawQuery("SELECT * FROM curriculum_cs16 JOIN lecture_cs USING (lecture_num) ORDER BY grade ASC", null);
                break;
            case "2017":
                result = myDataBase.rawQuery("SELECT * FROM curriculum_cs17 JOIN lecture_cs USING (lecture_num) ORDER BY grade ASC", null);
                break;
            case "2018":
                result = myDataBase.rawQuery("SELECT * FROM curriculum_cs18 JOIN lecture_cs USING (lecture_num) ORDER BY grade ASC", null);
                break;
        }

        String[][] table = getTable(result);

        close();
        return table;
    }

    // 연도별 공학인증.
    public String[][] getKCC(String year) {
        if (myDataBase == null)
            openDatabase();
        else if (!myDataBase.isOpen())
            openDatabase();

        Cursor result = null;

        switch (year) {
            case "2012":
                result = myDataBase.rawQuery("SELECT * FROM kcc2010_" + year + " JOIN kcc_lectures USING (lecture_num) ORDER BY type ASC", null);
                break;
            case "2013":
                result = myDataBase.rawQuery("SELECT * FROM kcc2010_" + year + " JOIN kcc_lectures USING (lecture_num) ORDER BY type ASC", null);
                break;
            case "2014":
                result = myDataBase.rawQuery("SELECT * FROM kcc2010_" + year + " JOIN kcc_lectures USING (lecture_num) ORDER BY type ASC", null);
                break;
            case "2015":
                result = myDataBase.rawQuery("SELECT * FROM kcc2015 JOIN kcc_lectures USING (lecture_num) ORDER BY type ASC", null);
                break;
        }

        String[][] table = getTable(result);

        close();
        return table;
    }

    // 강의 명을 이용한 강의 검색기능
    // 강의 명, 표시할 리스트 수, 전공/교양
    public String[][] SearchLecture(String lecture, int nListCount, int lectrueType) {
        if (myDataBase == null)
            openDatabase();
        else if (!myDataBase.isOpen())
            openDatabase();

        Cursor result = lectrueType != MAJOR ?
                myDataBase.rawQuery("SELECT * FROM liberal_arts WHERE lecture_name>='" + lecture + "' ORDER BY lecture_name ASC LIMIT " + nListCount, null) :
                myDataBase.rawQuery("SELECT * FROM lecture_cs WHERE lecture_name>='" + lecture + "' ORDER BY lecture_name ASC LIMIT " + nListCount, null);

        String[][] table = getTable(result);

        close();
        return table;
    }

    /* 연도별 최소이수학점 반환
     *  16년도 이상과 15년도 이하는 각각 다른 과정으로 반환
     *  15년도 이하는 KCC과정을 만족해야 하며 그에 맞는 과목으로 반환됨
     *  16년도 이상 과정은 일반 과정을 따라가며 각기 다른 강의 타입으로 10학점만 넘기면 됨
     *
     *  ~ 2015 [4][15]
     *  연도, 전필, 전선, A, B, C, D, E, F, G(2015년도는 없음), (A ~ G)합, M, S, (M, S)합, 졸업최소
     *
     *  2016 ~ [3][9]
     *  연도, 전필, 전선, (공통,역량)필수, (공통,역량)선택, (핵심, 통합), 개척, 기초, 졸업최소
     */
    public String[][] GetMinCredits(String year) {
        if (myDataBase == null)
            openDatabase();
        else if (!myDataBase.isOpen())
            openDatabase();

        Cursor result = null;

        switch (year) {
            case "2012":
            case "2013":
            case "2014":
            case "2015":
                result = myDataBase.rawQuery("SELECT * FROM curriculum_credits_under15 WHERE year='" + year + "'", null);
                break;
            case "2016":
            case "2017":
            case "2018":
                result = myDataBase.rawQuery("SELECT * FROM curriculum_credits_over16 WHERE year='" + year + "'", null);
                break;
        }

        String[][] table = getTable(result);

        close();
        return table;
    }

    // 교직 관련 전공 과목 반환
    public String[][] getTeachingCourse(String major)
    {
        if (myDataBase == null)
            openDatabase();
        else if (!myDataBase.isOpen())
            openDatabase();

        Cursor result = null;

        switch (major)
        {
            case "cs":
                result = myDataBase.rawQuery("SELECT * FROM teaching_course_" + major + "JOIN lecture_" + major + "USING (lecture_num)", null);
                break;
        }

        String[][] table = getTable(result);

        close();
        return table;
    }
    // 교직과정별 과목 반환 이론, 실습, 소양 순서로 반환됨
    public String[][] getTeachingCourse() {
        return getTeachingCourse(false);
    }

    /* 교직과정 과목 혹은 최소이수 학점 반환.
     *  isMinCredits 가 true 면 최소이수학점 false 면 교직과정 과목 반환
     *  교직과정 최소이수학점 반환
     *  theory        = 이론
     *  refinement    = 소양
     *  practice      = 실습
     */
    public String[][] getTeachingCourse(boolean isMinCredits) {
        if (myDataBase == null)
            openDatabase();
        else if (!myDataBase.isOpen())
            openDatabase();

        Cursor result = isMinCredits ?
                myDataBase.rawQuery("SELECT * FROM teaching_course_credit", null) :
                myDataBase.rawQuery("SELECT * FROM teaching_course JOIN liberal_arts USING (lecture_num) ORDER BY type DESC", null);

        String[][] table = getTable(result);

        close();
        return table;
    }
}

// TODO: 전공 과목 v, 강의검색 v, 교직과목 v, KCC v, 최소이수학점 v, 교양과목 => 프래그먼트에서 처리?