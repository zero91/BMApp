package com.boostme.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.boostme.bean.ConsultEntity;
import com.boostme.constants.Constants;
import com.boostme.pay.PayActivity;
import com.boostme.util.UIUtil;
import com.boostme.view.CircleImageView;

public class ConsultDetailActivity extends Activity {
	CircleImageView imageView;

	TextView descriptionText;
	TextView buyNumText;
	TextView browserNumText;
	TextView servicesText;
	TextView educationsText;
	TextView priceText;
	TextView scoreText;

	Button getAllCommentsBtn, messageBtn, contactBtn;

	ConsultEntity entity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consult_detail);
		initialActionBar();
		initViews();
	}

	private void initialActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("详情");
		actionBar.setIcon(R.drawable.ic_back);
		actionBar.setHomeButtonEnabled(true);
		int padding = (int) getResources().getDimension(
				R.dimen.ab_title_padding);
		ImageView view = (ImageView) findViewById(android.R.id.home);
		view.setPadding(padding * 2, padding, padding * 2, padding);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void initViews() {
		Bundle bundle = getIntent().getExtras();
		entity = (ConsultEntity) bundle.get("consult");

		imageView = (CircleImageView) findViewById(R.id.zx_detail_picture);
		descriptionText = (TextView) findViewById(R.id.zx_detail_description);
		buyNumText = (TextView) findViewById(R.id.zx_detail_buynum);
		browserNumText = (TextView) findViewById(R.id.zx_detail_browsenum);
		servicesText = (TextView) findViewById(R.id.zx_detail_services);
		educationsText = (TextView) findViewById(R.id.zx_detail_educations);
		priceText = (TextView) findViewById(R.id.zx_detail_price);
		scoreText = (TextView) findViewById(R.id.zx_detail_score);

		getAllCommentsBtn = (Button) findViewById(R.id.zx_detail_getcomments);
		messageBtn = (Button) findViewById(R.id.zx_detail_message);
		contactBtn = (Button) findViewById(R.id.zx_detail_contact);

		contactBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ConsultDetailActivity.this,
						PayActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("consult", entity);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		initDatas();

	}

	public void initDatas() {
		//System.out.println(entity.toString());

		UIUtil.setImageFromNet(this,
				Constants.BASE_URL + entity.getHeadImageUrl(), imageView);

		descriptionText.setText(entity.getDescription());
		buyNumText.setText(entity.getServiceNum());
		browserNumText.setText(entity.getViewNum());

		servicesText.setText(entity.getServiceCategoty());
		educationsText.setText("xxxx大学 xxx学院  xxx专业\nooo大学 ooo学院 ooo专业");

		priceText.setText(entity.getPrice());
		scoreText.setText(entity.getAvgScore());

		int num = Integer.parseInt(entity.getCommentNum());
		if (num > 0)
			getAllCommentsBtn.setText("查看所有评价(共" + num + "条)");
		else
			getAllCommentsBtn.setText("暂无评价.");

	}

}
