package com.algorithm416.csjolup;

public class Lecture {
    private String grade;
    private String type;
    private String num;
    private String name;
    private String credit;
    private boolean bChecked;
    private boolean bLecture;

    public Lecture(String grade, String lecture_type, String lecture_num, String lecture_name, String lecture_credit){
        this.grade = grade;
        type = lecture_type;
        num = lecture_num;
        name = lecture_name;
        credit = lecture_credit;
        bChecked = false;
        bLecture = true;
    }

    public Lecture(String grade){
        this.grade = grade;
        type = null;
        num = null;
        name = null;
        credit = null;
        bChecked = false;
        bLecture = false;
    }

    public java.lang.String getGrade() {
        return grade;
    }

    public String getLectureType() {
        return type;
    }

    public String getLectureNum(){
        return num;
    }

    public String getLectureName(){
        return name;
    }

    public String getLectureCredit(){
        return credit;
    }

    public void setItemCheck(boolean bChecked) {
        this.bChecked = bChecked;
    }

    public boolean getItemCheck(){
        return bChecked;
    }

    public boolean isLecture(){
        return bLecture;
    }
}
