package com.example.a76952.login2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import com.example.a76952.login2.network.HttpCallBackListener;
import com.example.a76952.login2.network.HttpsConnect;
import com.example.a76952.login2.views.EditTextLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewCourse extends AppCompatActivity  {
    private EditText et_courseName;
    private EditTextLayout etl_classroom;
    private EditTextLayout etl_teacher;
    final String[] items = {"第1周", "第2周", "第3周", "第4周","第5周","第6周","第7周","第8周","第9周","第10周",
            "第11周", "第12周", "第13周", "第14周","第15周","第16周","第17周","第18周","第19周","第20周","第21周","第22周","第23周","第24周","第25周"};
    private AlertDialog multiSelectDialog;
    private boolean[] defaultSelectedStatus = {false,false,false,false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
    final ArrayList<Integer> selections = new ArrayList<>();
    final String[] weekDays = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
    private Integer weekDaySelected = -1;
    private AlertDialog weekDayDialog;
    TextView tv_selectedWeekDay;
    final String[] startLesson = {"第1节","第2节","第3节","第4节","第5节","第6节","第7节","第8节","第9节","第10节","第11节","第12节"};
    final String[] endLesson = {"第1节","第2节","第3节","第4节","第5节","第6节","第7节","第8节","第9节","第10节","第11节","第12节"};
    private Integer startLessonSelected = -1;
    private Integer endLessonSelected = -1;
    private AlertDialog startLessonDialog;
    private AlertDialog endLessonDialog;
    TextView tv_startLesson;
    TextView tv_endLesson;
    private Button btn_add;
    final String api = "https://app.biketomotor.cn/course/AddCourse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);
        et_courseName = (EditText) findViewById(R.id.et_course_name);
        etl_classroom = (EditTextLayout) findViewById(R.id.etl_classroom);
        etl_teacher = (EditTextLayout) findViewById(R.id.etl_teacher);
        tv_selectedWeekDay = (TextView)findViewById(R.id.week_day);
        tv_startLesson = (TextView) findViewById(R.id.start_lesson);
        tv_endLesson = (TextView) findViewById(R.id.end_lesson);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click");
                wrapData();
            }
        });


    }



    private void wrapData(){
        System.out.println("click");
        String courseName = et_courseName.getText().toString();
        String classroom = etl_classroom.getText().toString();
        String teacher = etl_teacher.getText().toString();
        Log.i("courseName",courseName);
        Log.i("classroom",classroom);
        Log.i("teacher",teacher);
        System.out.println(selections);
        Log.i("weekDay",weekDaySelected+"");
        Log.i("startLesson",startLessonSelected+"");
        Log.i("endLesson",endLessonSelected+"");
        final Intent intent = new Intent(NewCourse.this,MainActivity.class);
        intent.putExtra("courseName",courseName);
        intent.putExtra("classroom",classroom);
        intent.putExtra("teacher",teacher);
        intent.putExtra("selections",selections);
        intent.putExtra("weekDaySelected",weekDaySelected);
        intent.putExtra("startLessonSelected",startLessonSelected);
        intent.putExtra("endLessonSelected",endLessonSelected);
        if(!isDataValid()){
            Log.i("isValid","inValid");
            return;
        }
        else{
            Log.i("isValid","valid");
            JSONObject jsonData = getJson();
            HttpsConnect.sendHttpRequest(api, "POST", jsonData, new HttpCallBackListener() {
                @Override
                public void success(String response) {
                    //catchResponse(response);
                    Toast.makeText(NewCourse.this,"添加成功", Toast.LENGTH_SHORT).show();
                    setResult(10,intent);
                    finish();
                }
                @Override
                public void error(Exception exception) {
                    exception.printStackTrace();
//                    Toast.makeText(NewCourse.this,"添加失败", Toast.LENGTH_SHORT).show();
                    Log.i("add result","failed");
                    setResult(10,intent);
                    finish();
                }
            });

        }

    }
    public void showMultiAlertDialog(View view){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择上课周数");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(items, defaultSelectedStatus, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                defaultSelectedStatus[which] = isChecked;
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i=0;i<defaultSelectedStatus.length;i++){
                    if(defaultSelectedStatus[i]){
                        selections.add(i+1);
                    }
                }
                System.out.println("selections");
                System.out.println(selections);
            }
        }).setNegativeButton("取消",null);
        multiSelectDialog = alertBuilder.create();
        multiSelectDialog.show();
    }

    public void showSelectWeekDay(View view){
        AlertDialog.Builder weekDayBuilder = new AlertDialog.Builder(this);
        weekDayBuilder.setTitle("选择周几上课")
                .setSingleChoiceItems(weekDays,weekDaySelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        weekDaySelected = which+1;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("weekDaySelected",weekDaySelected+"");
                        tv_selectedWeekDay.setText(weekDays[weekDaySelected-1]);
                    }
                })
                .setNegativeButton("取消",null);
        weekDayDialog = weekDayBuilder.create();
        weekDayDialog.show();
    }

    public void showStartLessonDialog (View view){
        AlertDialog.Builder weekDayBuilder = new AlertDialog.Builder(this);
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
                        tv_startLesson.setText(startLesson[startLessonSelected-1]);
                    }
                })
                .setNegativeButton("取消",null);
        startLessonDialog = weekDayBuilder.create();
        startLessonDialog.show();
    }

    public void showEndLessonDialog (View view){
        AlertDialog.Builder weekDayBuilder = new AlertDialog.Builder(this);
        weekDayBuilder.setTitle("选择结束节数")
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
                        tv_endLesson.setText(endLesson[endLessonSelected-1]);
                    }
                })
                .setNegativeButton("取消",null);
        endLessonDialog = weekDayBuilder.create();
        endLessonDialog.show();
    }

    private boolean isDataValid(){
        boolean result = false;
        String courseName = et_courseName.getText().toString();
        String classroom = etl_classroom.getText().toString();
        String teacher = etl_teacher.getText().toString();
        if(!courseName.isEmpty() && !classroom.isEmpty() && !teacher.isEmpty() && selections.size()!=0 && weekDaySelected!=-1 && startLessonSelected!=-1 && endLessonSelected!=-1 ){
            if(startLessonSelected < endLessonSelected){
                result = true;
            }else{
                Log.i("inValid","startLessonSelected should earlier than endLessonSelected");
            }
        }
        return result;
    }

    private JSONObject getJson(){
        String courseName = et_courseName.getText().toString();
        String classroom = etl_classroom.getText().toString();
        String teacher = etl_teacher.getText().toString();
        JSONObject json = new JSONObject();
        try {
            json.put("stu_id", "1");
            JSONObject courseData = new JSONObject();
            courseData.put("courseName", courseName);
            courseData.put("courseName", courseName);
            courseData.put("classroom", classroom);
            courseData.put("teacher", teacher);
            courseData.put("weeks",selections);
            courseData.put("weekDay",weekDaySelected);
            courseData.put("startTime",startLessonSelected);
            courseData.put("endTime",endLessonSelected);
            json.put("courseData", courseData);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
