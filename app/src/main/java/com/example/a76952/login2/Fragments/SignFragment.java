package com.example.a76952.login2.Fragments;

/**
 * Created by 76952 on 2018/3/27.
 */

        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.support.v4.app.Fragment;
        import android.text.format.DateFormat;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;

        import com.example.a76952.login2.R;


/**
 * Created by 76952 on 2018/3/24.
 */

public class SignFragment extends Fragment {
    private Button timeButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_page_layout,container,false);
        //timeButton=(Button)view.findViewById(R.layout.fragment_main.timeButton);
        timeButton = (Button) view.findViewById(R.id.timeButton);
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

    //在主线程里面处理消息并更新UI界面
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long sysTime = System.currentTimeMillis();//获取系统时间
                    CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);//时间显示格式
                    timeButton.setText(sysTimeStr); //更新时间
                    break;
                default:
                    break;

            }
        }
    };


}

