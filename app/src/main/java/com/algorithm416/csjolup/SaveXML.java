package com.algorithm416.csjolup;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SaveXML {

    final private String filename = "CSJolup.dat" ;

    private Context context;

    SaveXML(Context context) {
        this.context = context;
    }

    public ArrayList<String> getData(String tag) {
        ArrayList<String> list = new ArrayList<>();
        String string;
        String[] data;

        SharedPreferences pref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        string = pref.getString(tag, null);

        if (string == null)
            return null;

        data = string.split(",");

        if (data.length == 0)
            return null;

        for (String temp : data) {
            list.add(temp);
        }

        return list;
    }

    public void saveData(String tag, ArrayList<Lecture> list) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Lecture temp : list) {
            if (temp.isLecture() && temp.getItemCheck()) {
                stringBuilder.append(temp.getLectureNum());
                stringBuilder.append(",");
            }
        }

        SharedPreferences pref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(tag, stringBuilder.toString());
        //editor.commit(); 대신 apply 로
        editor.apply();
    }

    public void saveData(String tag, int[] array) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i : array) {
            stringBuilder.append(i);
            stringBuilder.append(",");
        }

        SharedPreferences pref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(tag, stringBuilder.toString());
        //editor.commit(); 대신 apply 로
        editor.apply();
    }

    public void saveData(String tag, String[] array) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String str : array) {
            stringBuilder.append(str);
            stringBuilder.append(",");
        }

        SharedPreferences pref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(tag, stringBuilder.toString());
        //editor.commit(); 대신 apply 로
        editor.apply();
    }

    public void clear() {
        SharedPreferences pref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit(); //대신 apply 로
        editor.apply();
    }
}
