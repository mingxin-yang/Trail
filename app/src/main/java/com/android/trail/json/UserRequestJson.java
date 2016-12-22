package com.android.trail.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/12/7.
 */

public class UserRequestJson {

    /**
     * 获取网络中的JSON数据
     * @param path
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getJSONObject(String path)
            throws Exception {

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;
        URL url = new URL(path);
        // 利用HttpURLConnection对象，我们可以从网页中获取网页数据
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 单位为毫秒，设置超时时间为5秒
        conn.setConnectTimeout(15 * 1000);
        // HttpURLConnection对象是通过HTTP协议请求path路径的，所以需要设置请求方式，可以不设置，因为默认为get
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {// 判断请求码是否200，否则为失败
            InputStream is = conn.getInputStream(); // 获取输入流
            byte[] data = readStream(is); // 把输入流转换成字符串组
            String json = new String(data); // 把字符串组转换成字符串

            // 数据形式：{"totalRow":1,"pageNumber":1,"lastPage":true,"firstPage":true,"totalPage":1,"pageSize":15,"list":[{"id":1,"title":"汽车","content":"宝马"}]}
            JSONObject jsonObject = new JSONObject(json); // 返回的数据形式是一个Object类型，所以可以直接转换成一个Object
            //下面俩句话不太懂
//            int total = jsonObject.getInt("count");
//            String keywords = jsonObject.getString("keywords");

            // 里面有一个数组数据，可以用getJSONArray获取数组
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i); // 得到每个对象
                int id = item.getInt("id");
                String qq = item.getString("qq");
                String vchat = item.getString("vchat");
                String passward = item.getString("passward");
                String hobbies = item.getString("hobbies");
                String school = item.getString("school");
                String getdate = item.getString("getdate");
                String headsculpture = item.getString("headsculpture");
                String username = item.getString("username");
                String realname = item.getString("realname");
                String gone = item.getString("gone");
                map = new HashMap<String, String>();
                map.put("id", id + "");
                map.put("qq", qq);
                map.put("vchat",vchat);
                map.put("passward",passward);
                map.put("hobbies",hobbies);
                map.put("school",school);
                map.put("getdate",getdate);
                map.put("headsculpture",headsculpture);
                map.put("username",username);
                map.put("realname",realname);
                map.put("gone",gone);
                list.add(map);
            }
        }
        return list;
    }

    private static byte[] readStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            bout.write(buffer, 0, len);
        }
        bout.close();
        inputStream.close();
        return bout.toByteArray();
    }

}