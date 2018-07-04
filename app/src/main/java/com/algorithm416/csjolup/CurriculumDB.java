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
        DB_PATH = this.mycontext.getFilesDir().getPath() + mycontext.getApplicationContext().getPackageName()+"/databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
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

    private String[][] getTable(Cursor result) {
        if (result == null || !result.moveToFirst())
            return null;

        String[][] table = new String[result.getColumnCount()][result.getCount() + 1];

        for (int i = 0; i < result.getColumnCount(); i++) {
            table[i][0] = result.getColumnName(i);
        }

        for (int i = 1; result.moveToNext(); i++)
        {
            for (int j = 0; j < result.getColumnCount(); j++) {
                table[j][i] = result.getString(j);
            }
        }

        return table;
    }

    // 각 연도별 학년별 전공 필수 / 선택 과목 반환
    public String[][] getMajor(String year) {
        if (myDataBase.isOpen())
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
        if (myDataBase.isOpen())
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
                result = myDataBase.rawQuery("SELECT * FROM kcc" + year + " JOIN kcc_lectures USING (lecture_num) ORDER BY type ASC", null);
                break;
        }

        String[][] table = getTable(result);

        close();
        return table;
    }

    public String[][] SearchLecture(String lecture_num, int nListCount, int lectrueType) {
        if (myDataBase.isOpen())
            openDatabase();

        Cursor result = lectrueType != MAJOR ?
                myDataBase.rawQuery("SELECT * FROM liberal_arts WHERE lecture_num>='" + lecture_num + "' LIMIT " + nListCount, null) :
                myDataBase.rawQuery("SELECT * FROM lecture_cs WHERE lecture_num>='" + lecture_num + "' LIMIT " + nListCount, null);

        String[][] table = getTable(result);

        close();
        return table;
    }

    public String[][] GetMinCredits(String year) {
        if (myDataBase.isOpen())
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
}
