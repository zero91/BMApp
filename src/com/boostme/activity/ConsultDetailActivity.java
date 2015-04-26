package com.boostme.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.boostme.bean.ResponseInfoEntity;
import com.boostme.fragment.AreaPopupDataLoader;
import com.boostme.util.BmAsyncHttpResponseHandler;
import com.boostme.util.BmHttpClientUtil;
import com.boostme.util.Logs;
import com.boostme.util.UIUtil;
import com.boostme.view.CircleImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ConsultDetailActivity extends BMActivity {

	private static String splitChar = "-";

	private CircleImageView imageView;

	private TextView descriptionText;
	private TextView buyNumText;
	private TextView browserNumText;
	private TextView servicesText;
	private TextView educationsText;
	private TextView priceText;
	private TextView scoreText;

	private Button getAllCommentsBtn, messageBtn, contactBtn;

	private ProgressBar progressBar;

	private Map<String, Object> consultMap;

	private AreaPopupDataLoader areasHandle;

	private String serviceId;

	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// areasHandle.getSchoolsMap();
				break;
			case 1:
				// areasHandle.getDeptsMap();
				break;
			case 2:
				// areasHandle.getMajorsMap();
				break;
			case 3:
				getConsultDetail(serviceId);
				break;
			default:
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consult_detail);
		initViews();
	}

	public void initViews() {

		serviceId = getIntent().getStringExtra("serviceId");

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

		progressBar = (ProgressBar) findViewById(R.id.zx_detail_progressbar);

		contactBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(ConsultDetailActivity.this,
				// PayActivity.class);
				// Bundle bundle = new Bundle();
				// bundle.putSerializable("consult", entity);
				// intent.putExtras(bundle);
				// startActivity(intent);
			}
		});

		getAllCommentsBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ConsultDetailActivity.this,
						ConsultCommentActivity.class);

				intent.putExtra("serviceId", serviceId);
				startActivity(intent);

			}

		});

		areasHandle = new AreaPopupDataLoader(this, handler);

		progressBar.setVisibility(View.VISIBLE);

		if (AreaPopupDataLoader.majorsMap != null)
			getConsultDetail(serviceId);
		else {
			areasHandle.getRegionsMap();
			areasHandle.getSchoolsMap();
			areasHandle.getDeptsMap();
			areasHandle.getMajorsMap();
		}

	}

	public void setDatas() {
		UIUtil.setImageFromNet(this, (String) consultMap.get("avatar"),
				imageView);

		descriptionText.setText((String) consultMap.get("service_content"));
		buyNumText.setText((String) consultMap.get("service_num"));
		browserNumText.setText((String) consultMap.get("view_num"));

		String serviceTypeString = getServiceTypeString((List) consultMap
				.get("cid_list"));
		servicesText.setText(serviceTypeString);
		String eduString = getEduString((List) consultMap.get("edu_list"));
		educationsText.setText(eduString);

		String price = (String) consultMap.get("price") + "元/"
				+ (String) consultMap.get("service_time") + "分钟";

		priceText.setText(price);
		scoreText.setText((String) consultMap.get("avg_score"));

		int num = Integer.parseInt((String) consultMap.get("comment_num"));
		if (num > 0)
			getAllCommentsBtn.setText("查看所有评价(共" + num + "条)");
		else
			getAllCommentsBtn.setText("暂无评价.");
		progressBar.setVisibility(View.GONE);
	}

	public void getConsultDetail(String servicdId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("service_id", servicdId);

		BmHttpClientUtil.getInstance(this).get("/service/ajax_fetch_info",
				params, new BmAsyncHttpResponseHandler(this) {

					@Override
					public void onSuccessOper(int statusCode, Header[] headers,
							byte[] response) {
						// TODO Auto-generated method stub
						try {

							String result = new String(response, "utf-8");
							Logs.logd(result, "Bm Consult Detail");
							JSONObject json = new JSONObject(result);
							ResponseInfoEntity responseInfo = ResponseInfoEntity
									.parse(json);
							if (responseInfo.isSuccess()) {
								consultMap = (new Gson()).fromJson(
										json.getString("service"),
										new TypeToken<Map<String, Object>>() {
										}.getType());

								setDatas();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
	}

	public String getServiceTypeString(List serviceTypeIDList) {

		StringBuilder sb = new StringBuilder();
		for (Object item : serviceTypeIDList) {
			Map temp = (Map) item;
			Map tempArea;

			if (sb.length() > 0)
				sb.append("\n");

			String rid = (String) temp.get("region_id");
			if (!rid.equals("")) {
				tempArea = (Map) AreaPopupDataLoader.regionsMap.get(rid);
				sb.append(tempArea.get("name"));
			}

			String sid = (String) temp.get("school_id");
			if (!sid.equals("")) {
				tempArea = (Map) AreaPopupDataLoader.schoolsMap.get(sid);
				sb.append(splitChar);
				sb.append(tempArea.get("name"));
			}

			String did = (String) temp.get("dept_id");
			if (!did.equals("")) {
				tempArea = (Map) AreaPopupDataLoader.deptsMap.get(did);
				sb.append(splitChar);
				sb.append(tempArea.get("name"));
			}

			String mid = (String) temp.get("major_id");
			if (!mid.equals("")) {
				tempArea = (Map) AreaPopupDataLoader.majorsMap.get(mid);
				sb.append(splitChar);
				sb.append(tempArea.get("name"));
			}

		}
		return sb.toString();
	}

	public String getEduString(List eduList) {
		StringBuilder sb = new StringBuilder();
		for (Object item : eduList) {
			Map temp = (Map) item;
			sb.append(temp.get("start_time"));
			sb.append("~");
			sb.append(temp.get("end_time"));
			sb.append("  ");
			sb.append(temp.get("school"));
			sb.append(splitChar);
			sb.append(temp.get("department"));
			sb.append(splitChar);
			sb.append(temp.get("major"));
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	protected String getActivitiTitle() {
		// TODO Auto-generated method stub
		return "详情";
	}

}
