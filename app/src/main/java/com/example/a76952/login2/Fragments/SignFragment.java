package com.example.a76952.login2.Fragments;

/**
 * Created by 76952 on 2018/3/27.
 */

        import android.content.Context;
        import android.graphics.Color;
        import android.graphics.drawable.GradientDrawable;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.support.v4.app.Fragment;
        import android.text.format.DateFormat;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.a76952.login2.MainActivity;
        import com.example.a76952.login2.Objects.Cur;
        import com.example.a76952.login2.R;
        import com.example.a76952.login2.network.HttpCallBackListener;
        import com.example.a76952.login2.network.HttpConnect;

        import org.json.JSONException;
        import org.json.JSONObject;


/**
 * Created by 76952 on 2018/3/24.
 */

public class SignFragment extends Fragment {
    private Button timeButton;
    private Context mContext;//传递活动
    private final String api = "http://biketomotor.cn:3000/api/UrgentActivity";
    private TextView tv_urgentAtyName;
    private TextView tv_urgentAtyTime;
    private TextView tv_hintText;
    private Long urgentAtyTime;
    private String timeDistance;
    int colorFlag;
    private static final Integer RED=0;
    private static final Integer BLUE=1;
    private static final Integer GREEN=2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_page_layout,container,false);
        //timeButton=(Button)view.findViewById(R.layout.fragment_main.timeButton);
        timeButton = (Button) view.findViewById(R.id.timeButton);
        tv_urgentAtyName=(TextView)view.findViewById(R.id.urgent_aty_name);
        tv_hintText=(TextView)view.findViewById(R.id.hint);
        colorFlag=BLUE;
        mContext=getActivity();

        JSONObject jsonData = getJson();

        HttpConnect.sendHttpRequest(api, "POST", jsonData, new HttpCallBackListener() {
            @Override
            public void success(String response) {
                catchResponse(response);
                System.out.println(response);
            }

            @Override
            public void error(Exception exception) {
                exception.printStackTrace();
            }
        });
        new TimeThread().start();
        return view;
    }
    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }


    private JSONObject getJson() {
        JSONObject json = new JSONObject();
        String account = Cur.getAccount();
        try {
            json.put("participant", account);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private void catchResponse(final String response) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                //解析出 {"result":"true","reason":"紧急活动获取成功","name":"校会","time":"1535146200431"}这种json格式 拿到name和time
                try{
                    JSONObject json = new JSONObject(response);
                    String result = json.getString("result");
                    String reason = json.getString("reason");
                    if (result.compareTo("true") == 0) {
                        String name = json.getString("name");
                        String time = json.getString("time");
                        tv_urgentAtyName.setText(name);
                        urgentAtyTime=Long.parseLong(time);
                        //urgentAtyTime=Long.parseLong("1535958630");
                        System.out.println(time);
                    } else {
                        Toast.makeText(mContext, reason, Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private String getDistanceTime(long  time1,long time2 ) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff ;
        String flag;
        if(time1<time2) {
            diff = time2 - time1;
            flag="前";
        } else {
            diff = time1 - time2;
            flag="后";
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        if(day!=0){
            if(flag=="后") colorFlag=GREEN;
            return day+"天"+hour+"小时"+flag;
        }
        if(hour!=0){
            if(flag=="后") colorFlag=GREEN;
            return hour+"小时"+min+"分钟"+sec+"秒"+flag;
        }
        if(min!=0){
            if(flag=="后"){
                if(min>30) colorFlag=GREEN;
                else if(min>10) colorFlag=BLUE;
                else colorFlag=RED;
            }
            return min+"分钟"+min+"秒"+flag;
        }
        if(sec!=0) {
            colorFlag=RED;
            return sec+"秒"+flag;
        }
        return "刚刚";
    }


    //在主线程里面处理消息并更新UI界面
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long sysTime = System.currentTimeMillis();//获取系统时间
                    timeDistance=getDistanceTime(urgentAtyTime,sysTime);//获取时间差
                    //CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);//时间显示格式
                    timeButton.setText(timeDistance); //更新时间
                    GradientDrawable mm= (GradientDrawable)timeButton.getBackground();
                    if(colorFlag==RED) {
                        mm.setColor(Color.parseColor("#FF4081"));
                        tv_hintText.setText("紧急紧急紧急紧急");
                    }
                    else if(colorFlag==BLUE) {
                        mm.setColor(Color.parseColor("#6495ED"));
                        tv_hintText.setText("还行还行还行还行");
                    }
                    else if(colorFlag==GREEN) {
                        mm.setColor(Color.parseColor("#3CB371"));
                        tv_hintText.setText("充裕充裕充裕充裕充裕");
                    }
                    break;
                default:
                    break;

            }
        }
    };





}

