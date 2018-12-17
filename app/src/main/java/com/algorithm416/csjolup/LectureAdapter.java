package com.algorithm416.csjolup;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class LectureAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private int mLayoutId;
    private ArrayList<Lecture> list;

    public LectureAdapter(Context context, int layout_id, ArrayList<Lecture> arrayList){
        mContext = context;
        mLayoutId = layout_id;
        list = arrayList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Lecture getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        if (list.size() > position && 0 <= position) {
            return position;
        }
        else {
            return -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LectureViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutId, null);
            viewHolder = new LectureViewHolder();
            viewHolder.lecture_type = (TextView)convertView.findViewById(R.id.lecture_type);
            viewHolder.lecture_name = (TextView)convertView.findViewById(R.id.lecture_name);
            viewHolder.credit = (TextView)convertView.findViewById(R.id.lecture_credit);
            viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (LectureViewHolder)convertView.getTag();
        }

        if (getItem(position).isLecture()) {
            viewHolder.lecture_type.setText(getItem(position).getLectureType());
            viewHolder.lecture_name.setText(getItem(position).getLectureName());
            viewHolder.credit.setText(getItem(position).getLectureCredit() + " 학점");
            viewHolder.checkBox.setClickable(false);
            viewHolder.checkBox.setFocusable(false);
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setChecked(getItem(position).getItemCheck());
        }
        else{
            viewHolder.lecture_type.setText("");
            viewHolder.credit.setText("");
            viewHolder.checkBox.setVisibility(View.GONE);
            viewHolder.lecture_name.setText(getItem(position).getGrade());

        }

        return convertView;
    }

}
class LectureViewHolder{
    public TextView lecture_type;
    public TextView lecture_name;
    public TextView credit;
    public CheckBox checkBox;
}