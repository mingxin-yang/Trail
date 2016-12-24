package com.android.trail.adapter;

import android.app.Activity;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.trail.R;
import com.android.trail.view.MyGridView;
import com.android.trail.util.Detail;
import com.android.trail.util.SysUtils;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingx_000 on 2016/12/5 0005.
 */

public class DetailListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Activity context;
    private List<Detail> list;
    private FinalBitmap finalImageLoader ;
    private DetailGridviewAdapter nearByInfoImgsAdapter;
    private int wh;

    public DetailListViewAdapter(Activity context, List<Detail> list) {
        super();
        this.wh=(SysUtils.getScreenWidth(context)-SysUtils.Dp2Px(context, 99))/3;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        this.finalImageLoader=FinalBitmap.create(context);
        this.finalImageLoader.configLoadingImage(R.drawable.ic_launcher);
    }

    public List<Detail> getList() {
        return list;
    }

    public void setList(List<Detail> list) {
        this.list = list;
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list == null ? null : list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list == null ? null : i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (list.size() == 0) {
            return null;
        }
        final ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.map_detail_adapter, null);
            holder = new ViewHolder();
            holder.headphoto = (ImageView) view.findViewById(R.id.pic);//头像
            holder.disName = (TextView) view.findViewById(R.id.title);//昵称
            holder.content = (TextView) view.findViewById(R.id.info);//发布内容
            holder.rl4=(LinearLayout) view.findViewById(R.id.rl4);//图片布局
            holder.gv_images = (MyGridView) view.findViewById(R.id.gridview);//图片
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Detail bean = list.get(i);

        String name = null, content = null,headpath = null,  contentimage = null;
        if (bean != null) {
            name = bean.getUsername();
            content = bean.getContent();
            headpath = bean.getHeadphoto();
            contentimage = bean.getImages();
        }
        //昵称
        if (name!=null&&!name.equals("")) {
            holder.disName.setText(name);
        }
        //是否含有图片
        if (contentimage!=null&&!contentimage.equals("")) {
            initInfoImages(holder.gv_images,contentimage);
        }

        //内容
        if (content!=null&&!content.equals("")) {
            holder.content.setText(content);
            Linkify.addLinks(holder.content, Linkify.WEB_URLS);
        }
        //头像
        if (headpath!=null&&!headpath.equals("")) {
            finalImageLoader.display(holder.headphoto,headpath);
        } else {
            holder.headphoto.setImageResource(R.drawable.ic_launcher);
        }

        return view;
    }
    static class ViewHolder {
        ImageView headphoto;
        TextView disName;
        TextView time;
        TextView content;
        MyGridView gv_images;
        LinearLayout rl4;
    }
    /**
     * 加载信息中包含的图片内容
     * @param imgspath
     */
    public void initInfoImages(MyGridView gv_images,final String imgspath){
        if(imgspath!=null&&!imgspath.equals("")){
            String[] imgs=imgspath.split("#");
            ArrayList<String> list=new ArrayList<String>();
            for(int i=0;i<imgs.length;i++){
                list.add(imgs[i]);
            }
            int w=0;
            switch (imgs.length) {
                case 1:
                    w=wh;
                    gv_images.setNumColumns(1);
                    break;
                case 2:
                case 4:
                    w=2*wh+SysUtils.Dp2Px(context, 2);
                    gv_images.setNumColumns(2);
                    break;
                case 3:
                case 5:
                case 6:
                    w=wh*3+SysUtils.Dp2Px(context, 2)*2;
                    gv_images.setNumColumns(3);
                    break;
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, LinearLayout.LayoutParams.WRAP_CONTENT);
            gv_images.setLayoutParams(lp);
            nearByInfoImgsAdapter=new DetailGridviewAdapter(context, list);
            gv_images.setAdapter(nearByInfoImgsAdapter);

        }

    }
}
