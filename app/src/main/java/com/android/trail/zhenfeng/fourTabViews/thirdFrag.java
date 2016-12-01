package com.android.trail.zhenfeng.fourTabViews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.trail.R;
import com.android.trail.zhenfeng.hzfLei.AndroidShare;

/**
 * Created by 20160316 on 2016/11/29.
 */

public class thirdFrag extends Fragment{


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scency_share, container, false);


        view.findViewById(R.id.buttonShare).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AndroidShare as = new AndroidShare(
                        getContext(),
                        "哈哈---超方便的分享！！！来自allen",
                        "http://img6.cache.netease.com/cnews/news2012/img/logo_news.png");
                as.show();
            }
        });

        return view;
    }

}
