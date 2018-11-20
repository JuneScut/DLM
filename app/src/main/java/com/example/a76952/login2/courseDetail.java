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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class courseDetail extends AppCompatActivity {
    private TextView et_courseName;
    private EditText et_classroom;
    private EditText et_teacher;
    final String[] items = {"第1周", "第2周", "第3周", "第4周","第5周","第6周","第7周","第8周","第9周","第10周",
            "第11周", "第12周", "第13周", "第14周","第15周","第16周","第17周","第18周","第19周","第20周","第21周","第22周","第23周","第24周","第25周"};
    private AlertDialog multiSelectDialog;
    private boolean[] defaultSelectedStatus = {false,false,false,false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
    final ArrayList<Integer> selections = new ArrayList<>(0);
    private TextView et_weeks;
    final String[] weekDays = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
    private Integer weekDaySelected = -1;
    private AlertDialog weekDayDialog;
    private TextView et_weekDay;
    final String[] startLesson = {"第1节","第2节","第3节","第4节","第5节","第6节","第7节","第8节","第9节","第10节","第11节","第12节"};
    final String[] endLesson = {"第1节","第2节","第3节","第4节","第5节","第6节","第7节","第8节","第9节","第10节","第11节","第12节"};
    private Integer startLessonSelected = -1;
    private Integer endLessonSelected = -1;
    private AlertDialog startLessonDialog;
    private AlertDialog endLessonDialog;
    private TextView et_startLesson;
    private TextView et_endLesson;
    private Button confirmBtn;
    final String api = "https://app.biketomotor.cn/course/EditCourse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        init();
    }
    private void init(){
        et_courseName = (EditText) findViewById(R.id.course_name);
        et_classroom = (EditText) findViewById(R.id.course_classroom);
        et_teacher = (EditText) findViewById(R.id.course_teacher);
        et_weeks = (TextView) findViewById(R.id.course_weeks);
        et_weekDay = (TextView) findViewById(R.id.course_weekdays);
        et_startLesson = (TextView) findViewById(R.id.course_start_time);
        et_endLesson = (TextView) findViewById(R.id.course_end_time);
        confirmBtn = (Button) findViewById(R.id.btn_confirm);
        Intent intent = getIntent();
        if (intent != null) {
            et_courseName.setText(intent.getStringExtra("courseName"));
            et_classroom.setText(intent.getStringExtra("courseClassroom"));
            et_teacher.setText(intent.getStringExtra("courseTeacher"));
            et_weeks.setText(intent.getStringExtra("courseWeek"));
            et_weekDay.setText(intent.getStringExtra("courseWeekDay"));
            et_startLesson.setText(intent.getStringExtra("courseStartTime"));
            et_endLesson.setText(intent.getStringExtra("courseEndTime"));
        }
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click");
                modifyCourse();
            }
        });
    }

    public void showMultiAlertDialog(View view){
        selections.clear();
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
                String selectedWeeks = "";
                if (selections.size() > 0){
                    selectedWeeks = selectedWeeks + "第" + selections.get(0);
                    for (int i=1;i<selections.size();i++){
                        selectedWeeks = selectedWeeks + "、" + selections.get(i);
                    }
                    selectedWeeks += "周";
                }
                et_weeks.setText(selectedWeeks);
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
                        et_weekDay.setText(weekDays[weekDaySelected-1]);
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
                        et_startLesson.setText(startLesson[startLessonSelected-1]);
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
                        et_endLesson.setText(endLesson[endLessonSelected-1]);
                    }
                })
                .setNegativeButton("取消",null);
        endLessonDialog = weekDayBuilder.create();
        endLessonDialog.show();
    }

    private JSONObject getJson(){
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

    public void modifyCourse() {
        String courseName = et_courseName.getText().toString();
        String classroom = et_classroom.getText().toString();
        String teacher = et_teacher.getText().toString();
        final Intent intent = new Intent(courseDetail.this,MainActivity.class);
        intent.putExtra("courseName",courseName);
        intent.putExtra("classroom",classroom);
        intent.putExtra("teacher",teacher);
        intent.putExtra("selections",selections);
        intent.putExtra("weekDaySelected",weekDaySelected);
        intent.putExtra("startLessonSelected",startLessonSelected);
        intent.putExtra("endLessonSelected",endLessonSelected);
        JSONObject jsonData = getJson();
        HttpsConnect.sendHttpRequest(api, "POST", jsonData, new HttpCallBackListener() {
            @Override
            public void success(String response) {
                //catchResponse(response);
                // Toast.makeText(courseDetail.this,"修改成功", Toast.LENGTH_SHORT).show();
                Log.i("modify result","success");
                setResult(10,intent);
                finish();
            }
            @Override
            public void error(Exception exception) {
                exception.printStackTrace();
//                    Toast.makeText(NewCourse.this,"添加失败", Toast.LENGTH_SHORT).show();
                Log.i("modify result","failed");
                setResult(10,intent);
                finish();
            }
        });
    }
}
