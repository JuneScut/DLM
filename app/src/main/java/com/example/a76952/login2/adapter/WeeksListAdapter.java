package com.example.a76952.login2.adapter;

/**
 * Created by 76952 on 2018/8/11.
 */
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a76952.login2.R;

public class WeeksListAdapter extends BaseAdapter{
    private String[] weeks = {"第1周", "第2周", "第3周", "第4周", "第5周", "第6周", "第7周","第8周","第9周","第10周",
            "第11周","第12周","第13周","第14周","第15周","第16周","第17周","第18周","第19周","第20周","第21周","第22周","第23周","第24周"};
    private Context context;

    public WeeksListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return weeks.length;
    }

    @Override
    public Object getItem(int position) {
        return weeks[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView tvWeeks;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.weeks_listview_item, null);
            tvWeeks = (TextView) convertView.findViewById(R.id.tv_weeks);
            convertView.setTag(tvWeeks);
        } else {
            tvWeeks = (TextView) convertView.getTag();
        }
        tvWeeks.setText(weeks[position]);
//        if (position == 2) {
//            tvWeeks.setBackgroundResource(R.drawable.ic_dropdown_week_cur_select_bg);
//            tvWeeks.setTextColor(Color.WHITE);
//            tvWeeks.setText(weeks[position] + "（本周）");
//        } else {
//            tvWeeks.setBackgroundResource(R.drawable.btn_chooce_week_bg);
//        }
        tvWeeks.setBackgroundResource(R.drawable.btn_chooce_week_bg);
        return convertView;
    }
}
