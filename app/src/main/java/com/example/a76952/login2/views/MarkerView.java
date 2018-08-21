package com.example.a76952.login2.views;

import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.a76952.login2.R;


/**
 * Created by 76952 on 2018/8/11.
 */

public class MarkerView extends View {
    private Paint mPaint;

    public MarkerView(Context context) {

        super(context);
        int colorGray = getResources().getColor(R.color.list_focus_color);
        mPaint = new Paint();
        mPaint.setColor(colorGray);
        mPaint.setStrokeWidth(2.5f);//设置画笔宽度

    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println("=========>onDraw<===========");
        canvas.drawLine(8, 0, 8, 16, mPaint);
        canvas.drawLine(0, 8, 16, 8, mPaint);

    }
}
