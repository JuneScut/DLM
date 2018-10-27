package com.example.a76952.login2.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a76952.login2.MainActivity;
import com.example.a76952.login2.NewCourse;
import com.example.a76952.login2.Objects.Cur;
import com.example.a76952.login2.R;
import com.example.a76952.login2.adapter.WeeksListAdapter;
import com.example.a76952.login2.network.HttpCallBackListener;
import com.example.a76952.login2.network.HttpsConnect;
import com.example.a76952.login2.views.CourseContentView;
import com.example.a76952.login2.views.CourseDetailDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by 76952 on 2018/8/11.
 */

public class CourseTableFragment extends Fragment implements View.OnClickListener, View.OnTouchListener{
    View view;
    private TextView tvShowWeeksList;
    private PopupWindow weeksListPpw;
    private Button btn_addCourse;
    private Integer courseListLength = 0;
    private ArrayList<int[]> crNums;
    private ArrayList<String> courseTexts;
    private ArrayList<Integer> crIds;
    private ArrayList<int[]> crWeeks;
    private ArrayList<String> courseNames;
    private ArrayList<String> courseClassrooms;
    private ArrayList<String> courseTeachers;
    private ArrayList<String> courseWeeks;
    private ArrayList<String> courseWeekDays;
    private ArrayList<String> courseStartTimes;
    private ArrayList<String> courseEndTimes;
    private CourseContentView ccv;
    private String api = "https://app.biketomotor.cn/course/GetCourseList";
    private String deleteApi = "https://app.biketomotor.cn/course/DeleteCourse";
    private static int IS_FINISH = 1;
    String[] weekDayDict = {"周一","周二","周三","周四","周五","周六","周日"};
    String[] timeDict={"第1节","第2节","第3节","第4节","第5节","第6节","第7节","第8节","第9节","第10节","第11节","第12节"};
    private  Handler handler = new Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message msg) {
            // 判断消息码是否为1
            if(msg.what==IS_FINISH){
                adjustCourseView(view);
            }
        }
    };
    //课程的背景颜色
    int[] bgIds = {R.drawable.ic_course_bg_bohelv, R.drawable.ic_course_bg_lan, R.drawable.ic_course_bg_tao, R.drawable.ic_course_bg_zi, R.drawable.ic_course_bg_bohelv,R.drawable.ic_course_bg_tuhuang, R.drawable.ic_course_bg_tao,R.drawable.ic_course_bg_tuhuang};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("message","it is a new fragment created here");
        view = inflater.inflate(R.layout.course_table_fragment, null);
        ccv = (CourseContentView) view.findViewById(R.id.add_course_rl);

        //获取初始课程列表 在getInitialList发送网络请求，在sucess中调用handlerMessage，再调用adjustCourseView来调整课表视图
        getInitialList();

        // 新增课程
        btn_addCourse = (Button) view.findViewById(R.id.add_course);
        btn_addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("新增课程");
                Intent intent = new Intent(getActivity(), NewCourse.class);
                startActivityForResult(intent,1);//带请求码1 到newCourse
            }
        });

        // 顶部选择周数的弹窗
        /**
         * popupWindow部分
         */
        // View类中的inflate方法内部包裹了LayoutInflater类的inflate方法，这个方法是一个静态方法，不需要创建View类的对象
        View contentView = View.inflate(getActivity(), R.layout.week_list_dropwindow, null);
        weeksListPpw = new PopupWindow(contentView);
        weeksListPpw.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        weeksListPpw.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        weeksListPpw.setFocusable(true);
        weeksListPpw.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸

        // 这句话必须有，否则按返回键  popwindow不能消失
        weeksListPpw.setBackgroundDrawable(new BitmapDrawable(getResources()));
        tvShowWeeksList = (TextView) view.findViewById(R.id.title_select_week);
        tvShowWeeksList.setOnClickListener(this);
        contentView.setOnTouchListener(this);

        //ppw里的ListView
        final ListView ppwListView = (ListView) contentView.findViewById(R.id.weeks_list);
        ppwListView.setDividerHeight(0);//隐藏分隔线
        WeeksListAdapter wlAdapter = new WeeksListAdapter(getActivity());
        ppwListView.setAdapter(wlAdapter);
        ppwListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("position","="+position);
            }
        });
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate");
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onPause() {
        System.out.println("onPause");
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_select_week:
                if (weeksListPpw.isShowing()) {
                    weeksListPpw.dismiss();
                } else {
                    weeksListPpw.showAsDropDown(tvShowWeeksList);
                }
                break;
            default:
                break;
        }
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            //设置外部点击关闭window
            case R.id.ppw_layout:
                if (weeksListPpw.isShowing()) {
                    weeksListPpw.dismiss();
                }
                break;
        }
        return false;
    }

    private void getInitialList(){
        crNums = new ArrayList<int[]>();
        courseTexts = new ArrayList<String>();
        crIds = new ArrayList<Integer>();
        crWeeks = new ArrayList<int[]>();
        courseNames = new ArrayList<String>();
        courseClassrooms = new ArrayList<String>();
        courseTeachers = new ArrayList<String>();
        courseWeekDays = new ArrayList<String>();
        courseWeeks = new ArrayList<String>();
        courseStartTimes = new ArrayList<String>();
        courseEndTimes = new ArrayList<String>();
// 初始化的数据
//        int[] arr1 = {1,1,2};int[] arr2 = {2,1,2};int[] arr3 = {3, 1, 2};int[] arr4 = {4, 1, 2};int[] arr5 = {5, 1, 2};int[] arr6 = {2, 3, 4};int[] arr7 = {1, 7, 8};
//        crNums.add(arr1);crNums.add(arr2);crNums.add(arr3);crNums.add(arr4);crNums.add(arr5);crNums.add(arr6);crNums.add(arr7);
//        String str1 = "数字信号处理@C3-503";String str2 =  "电机及应用技术@C3-503";String str3 =  "新技术专题II@C3-502";String str4 =  "自动控制原理@北教4#-503";String str5 = "软件架构原理@A1-431";String str6 = "IT项目管理@A1-431";String str7 = "数字信号处理@C3-503";
//        courseTexts.add(str1);  courseTexts.add(str2);courseTexts.add(str3); courseTexts.add(str4); courseTexts.add(str5); courseTexts.add(str6); courseTexts.add(str7);
//        courseListLength = crNums.size();

// 拿到课表内容,用HttpsConnect.sendHttpRequest方式
        JSONObject courseJsonData = getJson();
        HttpsConnect.sendHttpRequest(api, "POST", courseJsonData, new HttpCallBackListener() {
            @Override
            public void success(String response) {
                System.out.println("getCourseList success");
                System.out.println("res="+response);
                catchResponse(response);//处理课程列表 crNums 和 courseTexts,courseListLength
            }

            @Override
            public void error(Exception exception) {
                System.out.println("getCourseList failed");
                exception.printStackTrace();
            }
        });

    }

    private void showCourseDetail(View view, final int position) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CourseDetailDialog courseDetailDialog = new CourseDetailDialog( getActivity(),courseNames.get(position), courseTeachers.get(position),
                                                        courseClassrooms.get(position),courseWeeks.get(position),courseWeekDays.get(position),
                                                        courseStartTimes.get(position), courseEndTimes.get(position));
                courseDetailDialog.show();
            }
        });
    }

    private JSONObject getJson(){
        JSONObject json = new JSONObject();
        String stuId = Cur.getAccount();
        int currentWeek = 1;
        try {
            json.put("stu_id", stuId);
            json.put("week",currentWeek);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("json",json.toString());
        return json;
    }

    private void catchResponse(final String response){
        //课程列表
        //参数：周数、开始节数、结束节数
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("response of courseList",response);
                    JSONObject getCourseResponse = new JSONObject(response);
                    String courseList = getCourseResponse.getString("courseList");
                    JSONArray courseArray = new JSONArray(courseList);
                    Log.i("courseArry length",courseArray.length()+"");
                    courseListLength = courseArray.length();
                    for(int i=0;i<courseArray.length();i++){
                         //courseArray[i] is not an instance of JSONObject, courseItemObject is
                        JSONObject courseItemObject = courseArray.getJSONObject(i);
                        int courseId = courseItemObject.getInt("courseId");
                        crIds.add(courseId);
                        Log.i("courseId",courseId+"");
                        String courseData = courseItemObject.getString("courseData");
                        JSONObject courseDataObject = new JSONObject(courseData);
                        String courseName = courseDataObject.getString("courseName");
                        String classromm = courseDataObject.getString("classroom");
                        String teacher = courseDataObject.getString("teacher");
                        JSONArray weeks = courseDataObject.getJSONArray("weeks");
                        int[] weeksArray = new int[30];
                        String weeksStr = "第";
                        for(int j=0;j<(weeks.length()-1);j++){
                            Log.i("265",weeks.getInt(j)+"");
                            weeksArray[j] = (weeks.getInt(j));
                            weeksStr = weeksStr + weeks.getInt(j) + ",";
                        }
                        weeksStr = weeksStr + weeks.getInt(weeks.length()-1) + "周";
                        Log.i("270",weeksStr);
                        int weekDay = courseDataObject.getInt("weekDay");
                        int startTime = courseDataObject.getInt("startTime");
                        int endTime = courseDataObject.getInt("endTime");
                        int[] arr = {weekDay,startTime,endTime};
                        crNums.add(arr);
                        String str = courseName+"@"+classromm+"@"+teacher;
                        courseTexts.add(str);
                        crWeeks.add(weeksArray);
                        courseNames.add(courseName);
                        courseClassrooms.add(classromm);
                        courseTeachers.add(teacher);
                        courseWeekDays.add(weekDayDict[weekDay-1]);
                        courseWeeks.add(weeksStr);
                        courseStartTimes.add(timeDict[startTime-1]);
                        courseEndTimes.add(timeDict[endTime-1]);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    Message msg = Message.obtain();
                    msg.what = IS_FINISH;
                    // 发送这个消息到消息队列中
                    handler.sendMessage(msg);
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1 && resultCode == 10){
            MainActivity mainActivity = (MainActivity)getActivity();
            FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.course_table,new CourseTableFragment());
            fragmentTransaction.commit();

        }
    }

    private void adjustCourseView(final View view){
        //初始化课表视图
        ccv.setRowAndCulomnNum(crNums);
        ccv.setCourseText(courseTexts);
        ccv.setViewNumber(courseListLength);
        for (int i = 0; i < courseListLength; i++) {
            TextView courseView = new TextView(getActivity());
            int randomIndex = (int)(Math.random()* 8);
            courseView.setId(crIds.get(i));
            courseView.setBackgroundResource(bgIds[randomIndex]);
            courseView.setGravity(Gravity.CENTER);
            courseView.setTextColor(Color.WHITE);
            courseView.setTextSize(12);
            ccv.addView(courseView);
            final int finalI = i;
            courseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), finalI + "click", Toast.LENGTH_SHORT).show();
                    Log.i("click postion",finalI+"");
                    Log.i("判断为","点击");
                    showCourseDetail(view,finalI);
                }
            });
            courseView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.i("判断为","长按");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("删除课程");
                    builder.setMessage("是否确认删除该课程?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i("选择","确定");
                            // 发送删除请求
                            deleteCourse(finalI);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i("选择","取消");
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }
            });
        }
    }

    private void deleteCourse( final int courseId ){
        JSONObject deletedJson = new JSONObject();
        String stuId = Cur.getAccount();
        try {
            deletedJson.put("stu_id", stuId);
            deletedJson.put("courseId",courseId);
            Log.i("courseId",courseId+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpsConnect.sendHttpRequest(deleteApi, "POST", deletedJson, new HttpCallBackListener() {
            @Override
            public void success(String response) {
                System.out.println("delete success");
                System.out.println("res="+ response);
                //更新课程表
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //courseListLength -= 1;
                            //ccv.setViewNumber(courseListLength);
                            Log.i("deleting courseId=",courseId+"");
                            ccv.removeViews(85+courseId,1);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

            @Override
            public void error(Exception exception) {
                System.out.println("result failed");
                exception.printStackTrace();
            }
        });


    }




}
