package com.android.trail.hongxue;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.trail.R;
import com.android.trail.json.BBSRequestJson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Loktar on 2016/11/22.
 */
public class Discuss extends AppCompatActivity {

    private EditText DiscussEdt;
    private ListView discuss_list;
    private Button btn;
    private Button btn_put;
    private discussAdapter discuss_Adapter;
    //声明ListView控件
    private ListView mListView;
    // 声明数组链表，其装载的类型是ListItem(封装了一个Drawable和一个String的类)
    List<Map<String, String>> list;
    private List<discussAdata> lb = new ArrayList<>();

    /**
     * Activity的入口方法
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        DiscussEdt = (EditText) findViewById(R.id.discuss_edt);
        btn_put = (Button) findViewById(R.id.btn_put);
        discuss_list=(ListView)findViewById(R.id.discuss_list) ;

        new Thread(networkTask).start();

    }



    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果为-->" + val);
            // UI界面的更新等相关操作
            discuss_Adapter = new discussAdapter(getBaseContext(),lb);
            discuss_list.setAdapter(discuss_Adapter);
        }
    };
    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            //获取网络资源
            final String path = "http://10.7.88.94:8992/bbs/json";
            try {
                list = BBSRequestJson.getJSONObject(path);
                if (lb.size() != 0) {
                    lb.clear();
                }
                for(int i = 0;i < list.size();i++){
                    lb.add(new discussAdata(Integer.valueOf(
                            list.get(i).get("id")).intValue(),
                            list.get(i).get("headsculpture"),
                            list.get(i).get("bulletin"),
                            list.get(i).get("username")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 在这里进行 http request.网络请求相关操
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", "请求结果");
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };
}
