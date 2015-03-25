package com.boostme.adapter;

import java.io.IOException;
import java.util.List;

import za.co.immedia.pinnedheaderlistview.SectionedBaseAdapter;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boostme.activity.R;
import com.boostme.bean.AnswerEntity;
import com.boostme.bean.QuestionEntity;
import com.boostme.util.BMImageGetter;
import com.boostme.util.BMTagHandler;
import com.boostme.util.Logs;
import com.boostme.util.StringUtil;
import com.boostme.util.TimeUtils;
import com.squareup.picasso.Picasso;

public class QuestionDetailListAdapter extends SectionedBaseAdapter
{
	private List<QuestionEntity> mSectionList;
	private List<AnswerEntity> mItemList;
	private Context mContext;

	public QuestionDetailListAdapter(Context context, List<QuestionEntity> sectionList, List<AnswerEntity> itemList)
	{
		super();
		this.mSectionList = sectionList;
		this.mItemList = itemList;
		this.mContext = context;
	}

	@Override
	public Object getItem(int section, int position)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int section, int position)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSectionCount()
	{
		return mSectionList.size();
	}

	@Override
	public int getCountForSection(int section)
	{
		return mItemList.size();
	}

	@Override
	public View getItemView(int section, int position, View convertView, ViewGroup parent)
	{
		ViewHold hold=null;
		if (convertView==null) {
			hold=new ViewHold();
			convertView=LinearLayout.inflate(mContext, R.layout.question_detail_reply_item, null);
			hold.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			hold.tvPublishTime = (TextView) convertView.findViewById(R.id.tv_publish_time);
			hold.tvLikeNum = (TextView) convertView.findViewById(R.id.tv_likenum);
			convertView.setTag(hold);
		} else {
			hold=(ViewHold) convertView.getTag();
		}
		
		AnswerEntity entity = mItemList.get(position);
		Logs.loge("position = " + position + ", mItemList.size() = " + mItemList.size() + " mItemList.get(position) = " + entity.toString());
		Logs.loge("hold = " + hold);
		hold.tvTitle.setText(Html.fromHtml(entity.getContent()));
		
		String timeStr = TimeUtils.getDateDistanceToNowBefore(entity.getTime() * 1000, TimeUtils.MMDD_HHMM);
		hold.tvPublishTime.setText(entity.getAuthor() + " · " + timeStr + " · ");
		hold.tvLikeNum.setText(entity.getSupports() + "个赞");
		return convertView;
	}

	@Override
	public View getSectionHeaderView(int section, View convertView, ViewGroup parent)
	{
		ViewHold hold = null;
		if (convertView == null) {
			hold = new ViewHold();
			convertView = LinearLayout.inflate(mContext, R.layout.question_detail_main_item, null);
			hold.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			hold.tvPublishTime = (TextView) convertView.findViewById(R.id.tv_publish_time);
			hold.tvReplyNum = (TextView) convertView.findViewById(R.id.tv_reply_num);
			hold.tvLikeNum = (TextView) convertView.findViewById(R.id.tv_likenum);
			convertView.setTag(hold);
		} else {
			hold = (ViewHold) convertView.getTag();
		}
		QuestionEntity entity = mSectionList.get(section);
		
		Spanned content = null;
		String htmlstr = "<p>" + entity.getTitle() + "</p>";
		if (!StringUtil.isBlank(entity.getDescription())) { 
			htmlstr += entity.getDescription();
		}
		content = Html.fromHtml(htmlstr, new BMImageGetter(mContext, hold.tvTitle), new BMTagHandler(mContext));
		//content = Html.fromHtml(HTML_CONTENT, new BMImageGetter(mContext, hold.tvTitle), new BMTagHandler(mContext));
		hold.tvTitle.setText(content);
		
		hold.tvPublishTime.setText(entity.getAuthor() + " · " + TimeUtils.getDateDistanceToNowBefore(entity.getTime() * 1000, TimeUtils.MMDD_HHMM));
		hold.tvReplyNum.setText(entity.getReplyNum() + "");
		hold.tvLikeNum.setText(entity.getFavourNum() + "");
		return convertView;
	}
	
	private final static String HTML_CONTENT = "这是一个测试APP，<p>这个文本是HTML格式的</p>，有链接<a href=\"http://zhangqian.me\">唏嘘一世</a>，有图片:<br /><img src=\"http://www.boostme.cn:9507/public/data/attach/1502/NrPvhtEj.png\">";
    
	class ViewHold
	{
		TextView tvPublishTime;
		TextView tvTitle;

		TextView tvReplyNum;
		TextView tvLikeNum;
	}
}
