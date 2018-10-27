package com.example.a76952.login2.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a76952.login2.Objects.Cur;
import com.example.a76952.login2.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 76952 on 2018/9/16.
 */

public class CourseDetailDialog extends Dialog {
    Activity context;
    private EditText et_courseName;
    private EditText et_classroom;
    private EditText et_teacher;
    private EditText et_weeks;
    private EditText et_weekDay;
    private EditText et_startLesson;
    private EditText et_endLesson;
    private View.OnClickListener  mClickListener;
    final String[] startLesson = {"第1节","第2节","第3节","第4节","第5节","第6节","第7节","第8节","第9节","第10节","第11节","第12节"};
    final String[] endLesson = {"第1节","第2节","第3节","第4节","第5节","第6节","第7节","第8节","第9节","第10节","第11节","第12节"};
    private Integer startLessonSelected = -1;
    private Integer endLessonSelected = -1;
    private android.support.v7.app.AlertDialog startLessonDialog;
    private android.support.v7.app.AlertDialog endLessonDialog;
    private String courseName;
    private String teacher;
    private String classromm;
    private String weeks;
    private String weekDay;
    private String startTime;
    private String endTime;

    public CourseDetailDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }

    public CourseDetailDialog(Activity context,String courseName, String teacher, String classromm, String weeks, String weekDay, String startTime, String endTime) {
        super(context);
        this.context = context;
        this.courseName = courseName;
        this.teacher = teacher;
        this.classromm = classromm;
        this.weeks = weeks;
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.endTime = endTime;

    }



    public CourseDetailDialog(Activity context,int theme,View.OnClickListener clickListener){
        super(context,theme);
        this.context = context;
        this.mClickListener = clickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.course_detail);
        et_courseName = (EditText) findViewById(R.id.detail_courseName);
        et_classroom = (EditText) findViewById(R.id.detail_classroom);
        et_teacher = (EditText) findViewById(R.id.detail_teacher);
        et_weeks = (EditText) findViewById(R.id.detail_weeks);
        et_weekDay = (EditText) findViewById(R.id.detail_weekday);
        et_startLesson = (EditText) findViewById(R.id.detail_start_lesson);
        et_endLesson = (EditText) findViewById(R.id.detail_end_lesson);

        et_courseName.setText(this.courseName);
        et_classroom.setText(this.classromm);
        et_teacher.setText(this.teacher);
        et_weeks.setText(this.weeks);
        et_weekDay.setText(this.weekDay);
        et_startLesson.setText(this.startTime);
        et_endLesson.setText(this.endTime);
       // et_startLesson.setOnClickListener(this);
        //et_endLesson.setOnClickListener(this);
        Window dialogWindow = this.getWindow();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();
        Point size = new Point();
        d.getSize(size);
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.width = (int)(size.x * 0.8);
        dialogWindow.setAttributes(p);

    }

    public void showStartLessonDialog (View view){
        android.support.v7.app.AlertDialog.Builder weekDayBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        weekDayBuilder.setTitle("选择开始节数")
                .setSingleChoiceItems(startLesson,startLessonSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startLessonSelected = which+1;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("startLesonSelected",startLessonSelected+"");
                        et_startLesson.setText(startLesson[startLessonSelected-1]);
                    }
                })
                .setNegativeButton("取消",null);
        startLessonDialog = weekDayBuilder.create();
        startLessonDialog.show();
    }

    public void showEndLessonDialog (View view){
        android.support.v7.app.AlertDialog.Builder weekDayBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        weekDayBuilder.setTitle("选择开始节数")
                .setSingleChoiceItems(endLesson,endLessonSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        endLessonSelected = which+1;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("endLesonSelected",endLessonSelected+"");
                        et_endLesson.setText(endLesson[endLessonSelected-1]);
                    }
                })
                .setNegativeButton("取消",null);
        endLessonDialog = weekDayBuilder.create();
        endLessonDialog.show();
    }

    private JSONObject getJson() {
        String courseName = et_courseName.getText().toString();
        String classroom = et_classroom.getText().toString();
        String teacher = et_teacher.getText().toString();
        JSONObject json = new JSONObject();
        try {
            json.put("stu_id", "1");
            JSONObject courseData = new JSONObject();
            courseData.put("courseName", courseName);
            courseData.put("courseName", courseName);
            courseData.put("classroom", classroom);
            courseData.put("teacher", teacher);
//            courseData.put("weeks",selections);
//            courseData.put("weekDay",weekDaySelected);
//            courseData.put("startTime",startLessonSelected);
//            courseData.put("endTime",endLessonSelected);
            json.put("courseData", courseData);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;

    }


}
