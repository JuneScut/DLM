package com.example.a76952.login2.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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

import java.util.ArrayList;

/**
 * Created by 76952 on 2018/8/11.
 */

public class CourseTableFragment extends Fragment implements View.OnClickListener, View.OnTouchListener{
    private TextView tvShowWeeksList;
    private PopupWindow weeksListPpw;
    private Button btn_addCourse;
    private Integer courseListLength;
    private ArrayList<int[]> crNums;
    private ArrayList<String> courseTexts;
    private String api = "https://app.biketomotor.cn/course/GetCourseList";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.course_table_fragment, null);
        CourseContentView ccv = (CourseContentView) view.findViewById(R.id.add_course_rl);
        //获取初始课程列表
        getInitialList();
        //初始化课表视图
        ccv.setRowAndCulomnNum(crNums);
        ccv.setCourseText(courseTexts);
        //初始化课程的背景颜色
        int[] bgIds = {R.drawable.ic_course_bg_bohelv, R.drawable.ic_course_bg_lan, R.drawable.ic_course_bg_tao, R.drawable.ic_course_bg_zi, R.drawable.ic_course_bg_bohelv,R.drawable.ic_course_bg_tuhuang, R.drawable.ic_course_bg_tao,R.drawable.ic_course_bg_tuhuang};
        //调整课程视图
        for (int i = 0; i < courseListLength; i++) {
            TextView courseView = new TextView(getActivity());
            int randomIndex = (int)(Math.random()* 8);
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
                    showCourseDetail(view);
                }
            });
        }

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
        int[] arr1 = {1,1,2};int[] arr2 = {2,1,2};int[] arr3 = {3, 1, 2};int[] arr4 = {4, 1, 2};int[] arr5 = {5, 1, 2};int[] arr6 = {2, 3, 4};int[] arr7 = {1, 7, 8};
        crNums.add(arr1);crNums.add(arr2);crNums.add(arr3);crNums.add(arr4);crNums.add(arr5);crNums.add(arr6);crNums.add(arr7);
        String str1 = "数字信号处理@C3-503";String str2 =  "电机及应用技术@C3-503";String str3 =  "新技术专题II@C3-502";String str4 =  "自动控制原理@北教4#-503";String str5 = "软件架构原理@A1-431";String str6 = "IT项目管理@A1-431";String str7 = "数字信号处理@C3-503";
        courseTexts.add(str1);  courseTexts.add(str2);courseTexts.add(str3); courseTexts.add(str4); courseTexts.add(str5); courseTexts.add(str6); courseTexts.add(str7);
        courseListLength = crNums.size();
        // 拿到课表内容
        JSONObject courseJsonData = getJson();
        HttpsConnect.sendHttpRequest(api, "POST", courseJsonData, new HttpCallBackListener() {
            @Override
            public void success(String response) {
                System.out.println("result success");
                System.out.println("res="+response);
                catchResponse(response);//处理课程列表 crNums 和 courseTexts,courseListLength
            }

            @Override
            public void error(Exception exception) {
                System.out.println("result failed");
                exception.printStackTrace();
            }
        });

    }

    private void showCourseDetail(View view) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CourseDetailDialog courseDetailDialog = new CourseDetailDialog(getActivity());
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
                    for(int i=0;i<courseArray.length();i++){
                         //courseArray[i] is not an instance of JSONObject, courseItemObject is
                        JSONObject courseItemObject = courseArray.getJSONObject(i);
                        String courseData = courseItemObject.getString("courseData");
                        JSONObject courseDataObject = new JSONObject(courseData);
                        String courseName = courseDataObject.getString("courseName");
                        String classromm = courseDataObject.getString("classroom");
                        String teacher = courseDataObject.getString("teacher");
                        JSONArray weeks = courseDataObject.getJSONArray("weeks");
                        ArrayList<Integer> weeksArray = new ArrayList<>();
                        for(int j=0;j<weeks.length();j++){
                            weeksArray.add(weeks.getInt(j));
                        }
                        int weekDay = courseDataObject.getInt("weekDay");
                        int startTime = courseDataObject.getInt("startTime");
                        int endTime = courseDataObject.getInt("endTime");
                        int[] arr = {weekDay,startTime,endTime};
                        crNums.add(arr);
                        String str = courseName+"@"+classromm;
                        courseTexts.add(str);
                        courseListLength = crNums.size();
                        System.out.println("new crNums and courseTexts");
                        System.out.println(crNums);
                        System.out.println(courseTexts);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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


}
