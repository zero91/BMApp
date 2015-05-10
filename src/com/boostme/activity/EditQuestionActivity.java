package com.boostme.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.qii.weiciyuan.support.imageutility.ImageUtility;
import org.qii.weiciyuan.support.lib.KeyboardControlEditText;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.boostme.bean.ResponseInfoEntity;
import com.boostme.send.SelectPictureDialog;
import com.boostme.send.TextNumLimitWatcher;
import com.boostme.util.BmAsyncHttpResponseHandler;
import com.boostme.util.BmHttpClientUtil;
import com.boostme.util.Logs;
import com.boostme.util.StringUtil;
import com.boostme.util.UIUtil;
import com.boostme.util.Utility;
import com.loopj.android.http.RequestParams;

public class EditQuestionActivity extends BMActivity implements DialogInterface.OnClickListener
{
	private static final int CAMERA_RESULT = 10;
    private static final int PIC_RESULT = 11;
    
	private String mPicPath = "";
    private ImageView mPreview = null;
    private EditText mTitle = null;
    private KeyboardControlEditText mContent = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_new);
		
		mTitle = ((EditText) findViewById(R.id.status_new_title));
		mContent = ((KeyboardControlEditText) findViewById(R.id.status_new_content));
		mContent.addTextChangedListener(
                new TextNumLimitWatcher((TextView) findViewById(R.id.menu_send), mContent, this));
		mContent.setDrawingCacheEnabled(true);
//        AutoCompleteAdapter adapter = new AutoCompleteAdapter(this, content,
//                (ProgressBar) title.findViewById(R.id.have_suggest_progressbar));
//        content.setAdapter(adapter);

        mPreview = (ImageView) findViewById(R.id.status_image_preview);

        View.OnClickListener onClickListener = new BottomButtonClickListener();
        findViewById(R.id.menu_add_pic).setOnClickListener(onClickListener);
        findViewById(R.id.menu_send).setOnClickListener(onClickListener);
	}
	
	@Override
	protected String getActivitiTitle()
	{
		return "提问";
	}

	public void deletePicture()
	{
		if (mPicPath != null) {
            //new File(mPicPath).delete();
			mPicPath = null;
        }

        if (mContent.getText().toString().equals(getString(R.string.share_pic))) {
        	mContent.setText("");
        }

        ((ImageButton) findViewById(R.id.menu_add_pic))
                .setImageDrawable(getResources().getDrawable(R.drawable.camera_light));

        mPreview.setVisibility(View.INVISIBLE);
        mPreview.setImageBitmap(null);
	}
	
	private void send() 
	{
		String title = mTitle.getText().toString();
        String content = mContent.getText().toString();
        if (StringUtil.isBlank(title) || StringUtil.isBlank(content)) {
			UIUtil.showToast(this, "请输入完整信息！");
		} else {
			RequestParams params = new RequestParams();
			params.put("title", title);
			params.put("content", content);
			if (mPicPath != null) {
				try {
					params.put("picture", new File(mPicPath));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			BmHttpClientUtil.getInstance(this).post("question/ajax_add", params, new BmAsyncHttpResponseHandler(this)
			{
				@Override
				public void onSuccessOper(int statusCode, Header[] headers, byte[] response)
				{
					try {
						String result = new String(response, "utf-8");
						JSONObject json = new JSONObject(result);
						ResponseInfoEntity responseInfo = ResponseInfoEntity.parse(json);
						if (responseInfo.isSuccess()) {
							Intent intent = new Intent(EditQuestionActivity.this, MainActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
						} else {
							UIUtil.showToast(EditQuestionActivity.this, "失败, error = " + responseInfo.getError());
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		}
    }
	
	private class BottomButtonClickListener implements View.OnClickListener 
	{
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.menu_add_pic:
                    if (TextUtils.isEmpty(mPicPath)) {
                    	SelectPictureDialog.newInstance().show(getFragmentManager(), "");
                    } else {
                    	SelectPictureDialog.newInstance().show(getFragmentManager(), "");
                    	//DeleteSelectedPictureDialog.newInstance().show(getFragmentManager(), "");
                    }
                    break;
                case R.id.menu_send:
                    send();
                    break;
            }
        }
    }
	
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) 
	{
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
        	return;
        }
        
        if (requestCode == PIC_RESULT) {
			//当选择的图片不为空的话，在获取到图片的途径
			Uri uri = intent.getData();
			try {
				String [] pojo = { MediaStore.Images.Media.DATA };
				@SuppressWarnings("deprecation")
				Cursor cursor = managedQuery(uri, pojo, null, null, null);
				if (cursor != null) {
					ContentResolver cr = this.getContentResolver();
					int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					String path = cursor.getString(colunm_index);
					/***
					 * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
					 * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
					 */
					if (path.endsWith("jpg") || path.endsWith("png")) {
						Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
						mPreview.setImageBitmap(bitmap);
						mPicPath = path;
					} else {
					}
					Logs.logd("path = " + path);
				} else {
					Logs.logd("error, cursor == null");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (requestCode == CAMERA_RESULT) {
			String sdStatus = Environment.getExternalStorageState();
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
				UIUtil.showToast(this, "SD卡不可用！");
				return;
			}
			
			Bundle extras = intent.getExtras();
			if (extras != null) {
				Bitmap bitmap = (Bitmap) extras.get("data");
				mPicPath = saveAndGetImg(bitmap);
				mPreview.setImageBitmap(bitmap);
			} else {
				UIUtil.showToast(this, "未找到图片");
			}
		}
        Logs.logd("mPicPath = " + mPicPath);
    }

	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		switch (which) {
        case 0: //最近照片
            Uri lastUri = Utility.getLatestCameraPicture(this);
            if (lastUri != null) {
                Bitmap bitmap = ImageUtility.decodeBitmapFromSDCard(lastUri.getPath(), Utility.getScreenWidth(), Utility.getScreenHeight());
        		if (bitmap != null) {
        			mPreview.setVisibility(View.VISIBLE);
        			//mPreview.setImageURI(lastUri);
        			mPreview.setImageBitmap(bitmap);
        		}
        		mPicPath = lastUri.getPath();
        		Logs.logd("mPicPath = " + mPicPath);
                return;
            } else {
            	Toast.makeText(this, getString(R.string.dont_have_the_last_picture), Toast.LENGTH_SHORT).show();
            }
            break;
        case 1: //拍照上传
        	Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            
			if (Utility.isIntentSafe(this, intent)) {
				startActivityForResult(intent, CAMERA_RESULT);
			} else {
				Toast.makeText(this, getString(R.string.dont_have_camera_app),
						Toast.LENGTH_SHORT).show();
			}
            break;
        case 2: //本地选择
        	Intent picIntent = new Intent();
        	picIntent.setType("image/*");
        	picIntent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(picIntent, PIC_RESULT);
            break;
		}
	}
	
	private String saveAndGetImg(Bitmap bitmap) 
	{
		String sdcardPath = Environment.getExternalStorageDirectory().toString(); // 获取SDCARD的路径
		String fileName = sdcardPath + "/" + this.getPackageName() + "/" + System.currentTimeMillis() + ".png";
		//fileName = "/storage/emulated/0/Pictures/Screenshots/" + System.currentTimeMillis() + ".png";
		//String fileName = getCacheDir() + "/" + System.currentTimeMillis() + ".png";
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
		  
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Logs.logd("error, " + e.getLocalizedMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Logs.logd("error, " + e.getLocalizedMessage());
		}

		return fileName;
	}
}
