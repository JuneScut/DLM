package com.example.a76952.login2.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a76952.login2.NewCourse;
import com.example.a76952.login2.R;
import com.example.a76952.login2.views.CourseContentView;
import com.example.a76952.login2.views.CourseDetailFragment;

/**
 * Created by 76952 on 2018/8/11.
 */

public class CourseTableFragment extends Fragment implements View.OnClickListener, View.OnTouchListener{
    //private TextView tvShowWeeksList;
    private PopupWindow weeksListPpw;
    private Button btn_addCourse;
    private Integer courseListLength;
    private int[][] crNums;
    private  String[] courseTexts;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_table_fragment, null);
        CourseContentView ccv = (CourseContentView) view.findViewById(R.id.add_course_rl);
        //获取初始课程列表
        getInitialList();
        //初始化课表视图
        ccv.setRowAndCulomnNum(crNums);
        ccv.setCourseText(courseTexts);
        //初始化课程的背景颜色
        int[] bgIds = {R.drawable.ic_course_bg_bohelv, R.drawable.ic_course_bg_lan, R.drawable.ic_course_bg_tao, R.drawable.ic_course_bg_zi, R.drawable.ic_course_bg_tuhuang, R.drawable.ic_course_bg_bohelv,R.drawable.ic_course_bg_tuhuang};
        //调整课程视图
        for (int i = 0; i < courseListLength; i++) {
            TextView courseView = new TextView(getActivity());
            courseView.setBackgroundResource(bgIds[i]);
            courseView.setGravity(Gravity.CENTER);
            courseView.setTextColor(Color.WHITE);
            courseView.setTextSize(12);
            ccv.addView(courseView);
            final int finalI = i;
            courseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), finalI + "click", Toast.LENGTH_SHORT).show();
                    showCourseDetail(finalI);
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
                startActivity(intent);
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
        //课程列表
        //参数：周数、开始节数、结束节数
        crNums = new int[][]{{1, 1, 2}, {2, 1, 2}, {3, 1, 2},
                {4, 1, 2}, {5, 1, 2}, {2, 3, 4},{1,7,8}};
        courseTexts = new String[]{"数字信号处理@C3-503", "电机及应用技术@C3-503", "新技术专题II@C3-502",
                "自动控制原理@北教4#-503", "电力电子技术@C3-402", "软件架构原理@A1-431","IT项目管理@A1-431"};
        courseListLength = crNums.length;
    }

    private void showCourseDetail(final int id) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CourseDetailFragment fire = CourseDetailFragment.newInstance("fire_missiles?");
                fire.show(getFragmentManager(), "dialog");
            }
        });
    }

}
