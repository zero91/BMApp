package com.boostme.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boostme.activity.R;
import com.boostme.bean.QuestionEntity;
import com.boostme.util.Logs;
import com.boostme.util.TimeUtils;
import com.boostme.util.UIUtil;
import com.boostme.view.HorizontalListView;

public class QuestionListAdapter extends BaseAdapter 
{
	private LayoutInflater mInflater;
	private ArrayList<QuestionEntity> mQuestionList;
	private Activity mContext;
	private final int MAX_TITLE_LEN = 15;
	private final int MAX_CONTENT_LEN = 75;

	public QuestionListAdapter(Activity activity, ArrayList<QuestionEntity> list) 
	{
		mContext = activity;
		mQuestionList = list;
		mInflater = LayoutInflater.from(activity);
	}

	@Override
	public int getCount() 
	{
		return mQuestionList == null ? 0 : mQuestionList.size();
	}

	@Override
	public QuestionEntity getItem(int position) 
	{
		if (mQuestionList != null && mQuestionList.size() != 0) {
			return mQuestionList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) 
	{
		if (mQuestionList != null && mQuestionList.size() != 0) {
			return mQuestionList.get(position).getQid();
		}
		return -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder iHolder;
		View view = convertView;
		if (view == null) {
			view = mInflater.inflate(R.layout.question_list_item, null);

			iHolder = new ViewHolder();
			iHolder.headIcon = (ImageView) view.findViewById(R.id.jl_head_icon);
			iHolder.authorName = (TextView) view.findViewById(R.id.jl_post_name);
			iHolder.postTime = (TextView) view.findViewById(R.id.jl_post_time);
			iHolder.title = (TextView) view.findViewById(R.id.jl_title);
			iHolder.postContent = (TextView) view.findViewById(R.id.jl_post_content);
			iHolder.favourIcon = (ImageView) view.findViewById(R.id.jl_favour_icon);
			iHolder.favourNum = (TextView) view.findViewById(R.id.jl_favour_num);
			iHolder.replyIcon = (ImageView) view.findViewById(R.id.jl_reply_icon);
			iHolder.replyNum = (TextView) view.findViewById(R.id.jl_reply_num);
			iHolder.headIcon.setImageResource(R.drawable.portrait_holder);
			iHolder.imageList = (HorizontalListView) view.findViewById(R.id.jl_img_list);
			view.setTag(iHolder);
		} else {
			iHolder = (ViewHolder) view.getTag();
		}
		
		QuestionEntity entity = getItem(position);
		iHolder.authorName.setText(entity.getAuthor());
		iHolder.postTime.setText(TimeUtils.getDateDistanceToNowBefore(entity.getTime() * 1000, TimeUtils.MMDD_HHMM));
		iHolder.postContent.setText(entity.getStripDescription().length() <= MAX_CONTENT_LEN ? 
				entity.getStripDescription(): entity.getStripDescription().substring(0, MAX_CONTENT_LEN) + "......");
		iHolder.favourNum.setText(entity.getFavourNum() + "");
		iHolder.replyNum.setText(entity.getReplyNum() + "");
		iHolder.title.setText(entity.getTitle().length() <= MAX_TITLE_LEN ? entity.getTitle(): entity.getTitle().substring(0, MAX_TITLE_LEN) + "......");
		
		iHolder.imageList.setAdapter(new HorizontalImageViewAdapter(mContext, getImages(entity.getImages())));
		iHolder.imageList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				String imgUrl = (String) parent.getItemAtPosition(position);
				UIUtil.showToast(mContext, imgUrl);
			}
		});
		UIUtil.setImageFromNet(mContext, entity.getAvatar(), iHolder.headIcon);
		return view;
	}
	
	public String [] getImages(String [] imgInfos)
	{
		String [] imgs = new String[imgInfos.length];
		for (int i = 0; i < imgInfos.length; i++) {
			imgs[i] = getImgUrl(imgInfos[i]);
		}
		return imgs;
	}
	
	private String getImgUrl(String imgUrlInfo)
	{
		int start = imgUrlInfo.indexOf("src=\"") + 5;
		int end = imgUrlInfo.indexOf("\"", start);
		String imgUrl = imgUrlInfo.substring(start, end);
		Logs.logd(imgUrl);
		return imgUrl;
	}

	static public class ViewHolder 
	{
		ImageView headIcon;
		TextView authorName;
		TextView postTime;
		TextView title;

		TextView postContent;
		HorizontalListView imageList;

		ImageView favourIcon;
		TextView favourNum;
		ImageView replyIcon;
		TextView replyNum;
	}
}