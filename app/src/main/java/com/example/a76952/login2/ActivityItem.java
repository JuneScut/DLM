package com.example.a76952.login2;

/**
 * Created by 76952 on 2018/8/11.
 */

public class ActivityItem {
    private String activityName;
    private String activityTime;

    public ActivityItem(String name, String time){
        this.activityName=name;
        this.activityTime=time;
    }

    public String getActivityName(){
        return activityName;
    }
    public String getActivityTime(){
        return activityTime;
    }
}
