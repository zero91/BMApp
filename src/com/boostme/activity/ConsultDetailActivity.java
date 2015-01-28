package com.boostme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.boostme.bean.ConsultEntity;
import com.boostme.pay.PayActivity;
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

	ImageButton backBtn;
	Button getAllEstimatesBtn, messageBtn, contactBtn;

	ConsultEntity entity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consult_detail);
		initViews();
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

		backBtn = (ImageButton) findViewById(R.id.zx_detail_back);
		getAllEstimatesBtn = (Button) findViewById(R.id.zx_detail_getestimates);
		messageBtn = (Button) findViewById(R.id.zx_detail_message);
		contactBtn = (Button) findViewById(R.id.zx_detail_contact);
		
		contactBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ConsultDetailActivity.this, PayActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("consult", entity);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		initDatas();

	}

	public void initDatas() {

		imageView.setImageResource(R.drawable.konglang);
		descriptionText.setText(entity.getDescription());
		buyNumText.setText("100");
		browserNumText.setText("108");

		servicesText.setText(entity.getServiceCategoty());
		educationsText.setText("xxxx大学 xxx学院  xxx专业\nooo大学 ooo学院 ooo专业");

		priceText.setText(entity.getPrice());
		scoreText.setText("4.6分");

		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
