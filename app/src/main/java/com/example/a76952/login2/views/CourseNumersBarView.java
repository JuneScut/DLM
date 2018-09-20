package com.example.a76952.login2.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a76952.login2.R;

/**
 * Created by 76952 on 2018/8/11.
 */
//左边栏 显示每天的节数
public class CourseNumersBarView extends LinearLayout {
    public CourseNumersBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setOrientation(HORIZONTAL);

        LinearLayout ll = new LinearLayout(context);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        ll.setOrientation(VERTICAL);
        this.addView(ll,llParams);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CourseNumersBarView);
        float textSzie = ta.getDimension(R.styleable.CourseNumersBarView_numTextSize, 0);
        int textViewWitdh = (int) ta.getDimension(R.styleable.CourseNumersBarView_numTextWidth, 0);
        int textViewHeight = (int) ta.getDimension(R.styleable.CourseNumersBarView_numTextHeight, 0);
        int textColor = ta.getColor(R.styleable.CourseNumersBarView_numTextColor, 0);
//        int colorDarkBlue = getResources().getColor(R.color.colorDarkBlue);
        int colorGray = getResources().getColor(R.color.colorGray);

        LayoutParams tvParams = new LayoutParams(textViewWitdh, textViewHeight);
        LayoutParams tvRowLineParams = new LayoutParams(textViewWitdh, 1);

        for (int i = 0; i < 12; i++) {

            TextView tv = new TextView(context);
            TextView tvLine = new TextView(context);

            tv.setTextSize(textSzie);
            tv.setTextColor(textColor);
            tv.setGravity(Gravity.CENTER);
            tv.setText("" + (++i));
            i--;

            tvLine.setBackgroundColor(colorGray);

            ll.addView(tv, tvParams);
            ll.addView(tvLine, tvRowLineParams);
        }

        View culomnLine = new View(context);
        culomnLine.setBackgroundColor(colorGray);
        LayoutParams tclParams = new LayoutParams(1,LayoutParams.MATCH_PARENT);
        this.addView(culomnLine,tclParams);
    }
}
