package com.android.trail.hongxue;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.trail.R;
import com.android.trail.homepage.MainActivity;
import com.android.trail.json.BBSRequestJson;
import com.android.trail.json.BusRequestJson;
import com.android.trail.wangyang.bstop;
import com.android.trail.xizheng.PersonalActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import qiu.niorgai.StatusBarCompat;


/**
 * Created by Loktar on 2016/11/22.
 */
public class Discuss extends AppCompatActivity {
    List<Map<String, String>> ist = new ArrayList<Map<String, String>>();
    private EditText DiscussEdt;
    private Button btn;
    private Button btn_put;
    //声明ListView控件
    private ListView mListView;
    // 声明数组链表，其装载的类型是ListItem(封装了一个Drawable和一个String的类)
    private ArrayList<ListItem> mList;

    private PullToRefreshListView disPullToRefreshListView;
    /**
     * Activity的入口方法
     */
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);
        DiscussEdt = (EditText)findViewById(R.id.discuss_edt);
        btn_put = (Button)findViewById(R.id.btn_put);
        //下拉刷新
        disPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview_dis);
        disPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String btsoplabel = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                new disGetDataTask().execute();
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(btsoplabel);
            }
        });

        btn = (Button)findViewById(R.id.btn_discuss);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Discuss.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //获取listview对象
        mListView = disPullToRefreshListView.getRefreshableView();
        //获取Resources对象
        Resources res = this.getResources();
        mList = new ArrayList<Discuss.ListItem>();
        //初始化data，装载数据到数组链表mList中
        /*ListItem item = new ListItem();
        item.setImage(res.getDrawable(R.drawable.logo));
        item.setTitle("我觉得写留言板这个人很帅");
        mList.add(item);*/


        // 获取MainListAdapter对象
        MainListViewAdapter adapter = new MainListViewAdapter();

        // 将MainListAdapter对象传递给ListView视图
        mListView.setAdapter(adapter);
        //给listview注册上下文菜单
        registerForContextMenu(mListView);
        getData();
        btn_put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putData();
            }
        });
    }
    private void putData(){
        String str = DiscussEdt.getText().toString();

            Resources res = this.getResources();
            ListItem item = new ListItem();
            item.setImage(res.getDrawable(R.drawable.logo));
            item.setTitle(str);
            mList.add(item);

    }
    private void getData(){
        final String path = "http://10.7.88.94:8992/bbs/json";
        try {
            ist = BBSRequestJson.getJSONObject(path);
            if (mList.size() != 0) {
                mList.clear();
            }
            for(int i = 0;i < mList.size();i++){
                String BitmapUrl = ist.get(i).get("headsculpture");
                Bitmap bp = getBitmap(BitmapUrl);
                Drawable da = new BitmapDrawable(bp);
                ListItem item = new ListItem();
                item.setTitle(ist.get(i).get("bulletin"));
                item.setImage(da);
                mList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private class disGetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // TODO 自动生成的方法存根
            super.onPostExecute(result);
            // 加载完成后停止刷新
            disPullToRefreshListView.onRefreshComplete();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.discuss, menu);
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        //加载xml中的上下文菜单
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.discuss_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Toast.makeText(Discuss.this, "查看个人资料", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(Discuss.this,PersonalActivity.class);
                startActivity(intent);
                break;
            case R.id.delete:
                Toast.makeText(Discuss.this, "举报成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
    /**
     * 定义ListView适配器MainListViewAdapter
     */
    class MainListViewAdapter extends BaseAdapter {
        /**
         * 返回item的个数
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mList.size();
        }
        /**
         * 返回item的内容
         */
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mList.get(position);
        }
        /**
         * 返回item的id
         */
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        /**
         * 返回item的视图
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListItemView listItemView;

            // 初始化item view
            if (convertView == null) {
                // 通过LayoutInflater将xml中定义的视图实例化到一个View中
                convertView = LayoutInflater.from(Discuss.this).inflate(
                        R.layout.list_layout, null);

                // 实例化一个封装类ListItemView，并实例化它的两个域
                listItemView = new ListItemView();
                listItemView.imageView = (ImageView) convertView
                        .findViewById(R.id.image);
                listItemView.textView = (TextView) convertView
                        .findViewById(R.id.title);

                // 将ListItemView对象传递给convertView
                convertView.setTag(listItemView);
            } else {
                // 从converView中获取ListItemView对象
                listItemView = (ListItemView) convertView.getTag();
            }

            // 获取到mList中指定索引位置的资源
            Drawable img = mList.get(position).getImage();
            String title = mList.get(position).getTitle();

            // 将资源传递给ListItemView的两个域对象
            listItemView.imageView.setImageDrawable(img);
            listItemView.textView.setText(title);

            // 返回convertView对象
            return convertView;
        }
    }
    /**
     * 封装两个视图组件的类
     */
    class ListItemView {
        ImageView imageView;
        TextView textView;
    }
    /**
     * 封装了两个资源的类
     */
    class ListItem {
        private Drawable image;
        private String title;

        public Drawable getImage() {
            return image;
        }

        public void setImage(Drawable image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }
    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }
}
