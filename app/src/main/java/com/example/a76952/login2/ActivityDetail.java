package com.example.a76952.login2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityDetail extends AppCompatActivity {
    private TextView actiName;
    private TextView actiTime;
    private TextView actiPlace;
    private TextView actiSponsor;
    private TextView actiMonitor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        actiName=(TextView)findViewById(R.id.activity_name);
        actiTime=(TextView)findViewById(R.id.activity_time);
        actiPlace=(TextView) findViewById(R.id.activity_place);
        actiSponsor=(TextView)findViewById(R.id.activity_sponsor);
        actiMonitor=(TextView)findViewById(R.id.activity_monitor);
        Intent intent=getIntent();
        String activityName=intent.getStringExtra("activityName");
        String activityTime=intent.getStringExtra("activityTime");
        actiName.setText(activityName);
        actiTime.setText(activityTime);
        actiPlace.setText("xxx");
        actiSponsor.setText("xxx");
        actiMonitor.setText("xxx");

    }
}
