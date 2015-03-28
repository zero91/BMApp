package com.boostme.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.boostme.activity.R;
import com.boostme.util.Logs;
import com.squareup.picasso.Picasso;

public class HorizontalImageViewAdapter extends ArrayAdapter<String> 
{
    private LayoutInflater mInflater;
    private Activity mContext;

    public HorizontalImageViewAdapter(Activity activity, String[] values) 
    {
        super(activity, R.layout.question_img_view, values);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
        Holder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.question_img_view, parent, false);

            holder = new Holder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        String imgUrl = getItem(position);
        holder.imageView.invalidate();
        //UIUtil.setImageFromNet(mContext, imgUrl, holder.imageView, R.drawable.default_image);
        //int width = mContext.getWindowManager().getDefaultDisplay().getWidth();
        //int subWidth = (int)(width / (float)this.getCount());
        Picasso.with(mContext).load(imgUrl).fit().tag(mContext).placeholder(R.drawable.default_image)	
        	.error(R.drawable.error_portrait).into(holder.imageView);
        return convertView;
    }

    private static class Holder 
    {
        public ImageView imageView;
    }
}