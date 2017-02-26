package com.ansen.multithread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final int UPDATE_TEXTVIEW=1;
    private TextView textview;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==UPDATE_TEXTVIEW){
                textview.setText("当前值是:"+msg.obj);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview= (TextView) findViewById(R.id.textview);
        new Thread(new Runnable(){
            @Override
            public void run(){
                for(int i=1;i<=100;i++){
                    Log.i("MainActivity","当前值是:"+i);

                    Message message=handler.obtainMessage();//获取Message对象
                    message.obj=i;//要传递过去的值
                    message.what=UPDATE_TEXTVIEW;//用来区别发送者
                    message.sendToTarget();//发送一个消息给自己

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
