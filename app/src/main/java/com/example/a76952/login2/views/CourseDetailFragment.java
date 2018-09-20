package com.example.a76952.login2.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.a76952.login2.R;

/**
 * Created by 76952 on 2018/9/16.
 */

public class CourseDetailFragment extends DialogFragment {
    /**
     * 创建Fragment对话框实例
     *
     * @param title：指定对话框的标题。
     * @return：Fragment对话框实例。
     */
    public static CourseDetailFragment newInstance(String title) {
        CourseDetailFragment frag = new CourseDetailFragment();
        Bundle args = new Bundle();
        // 自定义的标题
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }
    /**
     * 覆写Fragment类的onCreateDialog方法，在DialogFragment的show方法执行之后， 系统会调用这个回调方法。
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 获取对象实例化时传入的窗口标题。
        //String title = getArguments().getString("title");
        // 用builder创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage(title);
//        builder.setPositiveButton("fire", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // FIRE ZE MISSILES!
//            }
//        });
//        builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // User cancelled the dialog
//            }
//        });
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.course_detail,null))
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CourseDetailFragment.this.getDialog().cancel();
                    }
                });

        // 创建一个dialog对象并返回
        return builder.create();
    }

}
