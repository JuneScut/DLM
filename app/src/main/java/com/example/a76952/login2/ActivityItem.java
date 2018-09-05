package com.example.a76952.login2;

/**
 * Created by 76952 on 2018/8/11.
 */

public class ActivityItem {
//    private String activityName;
//    private String activityTime;
//
//    public ActivityItem(String name, String time){
//        this.activityName=name;
//        this.activityTime=time;
//    }
//
//    public String getActivityName(){
//        return activityName;
//    }
//    public String getActivityTime(){
//        return activityTime;
//    }
private int    image;
    private String name;
    private String id;
    private String creater;
    private String info;

//    public Activity (String name, int image, String organizer, String info) {
//        this.image = image;
//        this.name = name;
//        this.organizer = organizer;
//        this.info = info;
//    }

    public ActivityItem(int image, String name, String id) {
        this.image = image;
        this.name = name;
        this.id = id;
        this.creater = "none";
        this.info = "This is " + name;
    }

    public ActivityItem(int image, String name, String id, String creater) {
        this.image = image;
        this.name = name;
        this.id = id;
        this.creater = creater;
        this.info = "This is " + name;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setCreater(String account) {
        this.creater = account;
    }
    public String getCreater() {
        return creater;
    }

    public String getInfo() {
        return info;
    }
}
