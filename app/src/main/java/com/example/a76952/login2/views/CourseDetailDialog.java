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

import com.example.a76952.login2.R;

/**
 * Created by 76952 on 2018/9/16.
 */

public class CourseDetailDialog extends Dialog {
    Activity context;
    private EditText et_startLesson;
    private EditText et_endLesson;
    private View.OnClickListener  mClickListener;
    final String[] startLesson = {"第1节","第2节","第3节","第4节","第5节","第6节","第7节","第8节","第9节","第10节","第11节","第12节"};
    final String[] endLesson = {"第1节","第2节","第3节","第4节","第5节","第6节","第7节","第8节","第9节","第10节","第11节","第12节"};
    private Integer startLessonSelected = -1;
    private Integer endLessonSelected = -1;
    private android.support.v7.app.AlertDialog startLessonDialog;
    private android.support.v7.app.AlertDialog endLessonDialog;

    public CourseDetailDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
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
        et_startLesson = (EditText) findViewById(R.id.start_time);
        et_endLesson = (EditText) findViewById(R.id.end_lesson);
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



}
