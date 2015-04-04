package com.boostme.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boostme.activity.R;
import com.boostme.util.UIUtil;

public class EnhanceEditText extends RelativeLayout implements OnFocusChangeListener, TextWatcher, OnClickListener {
	private EditText mEditText;
	private TextView mHintText, mInfoText;
	private ImageView mBottomLine;

	private String mText;
	private String mHint;
	private boolean mIsLimied = false;
	private int maxLength = -1;
	private boolean mIsSingleLine = true;
	private String moreInfo;
	private int mHighlightColor = 0x03a9f4;
	private Context mContext;

	public EnhanceEditText(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initial();
		mContext = context;

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BMSelfView);
		mHint = a.getString(R.styleable.BMSelfView_hint);
		if (TextUtils.isEmpty(mHint)) {
			mHint = "请输入内容";
		}
		mHighlightColor = a.getColor(R.styleable.BMSelfView_highlightColor, getResources().getColor(R.color.global_highlight));
		maxLength = a.getInt(R.styleable.BMSelfView_maxLength, -1);
		mIsLimied = a.getBoolean(R.styleable.BMSelfView_isLimited, false) || maxLength > 0;
		mIsSingleLine = a.getBoolean(R.styleable.BMSelfView_singleLine, true);
		int textSize = a.getDimensionPixelSize(R.styleable.BMSelfView_textSize, getResources().getDimensionPixelSize(R.dimen.text_body));
		moreInfo = a.getString(R.styleable.BMSelfView_moreinfo);
		int inputType = a.getInt(R.styleable.BMSelfView_android_inputType, InputType.TYPE_CLASS_TEXT);
		mEditText.setInputType(inputType);
		a.recycle();

		mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		mEditText.setSingleLine(mIsSingleLine);
		mEditText.setHintTextColor(getResources().getColor(R.color.text_body));
		mEditText.setHint(mHint);
		mEditText.setOnFocusChangeListener(this);
		mEditText.addTextChangedListener(this);
		mInfoText.setVisibility(View.INVISIBLE);
		mInfoText.setTextColor(mHighlightColor);
		if (mIsLimied && maxLength > 0) {
			mEditText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength),  UIUtil.getEmojiFilter(mContext)});
		}
		mHintText.setVisibility(View.GONE);
		mHintText.setText(mHint);
		refreshInfo();

		custom();
		//UIUtils.filterEmoji(mEditText);
	}

	public EnhanceEditText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public EnhanceEditText(Context context) {
		this(context, null);
	}

	/**
	 * child class override this method to do what they want
	 */
	protected void custom() {

	}

	protected EditText getEditText() {
		return mEditText;
	}

	private void initial() {
		LayoutInflater.from(getContext()).inflate(R.layout.enhance_edittext, this);
		mEditText = (EditText) findViewById(R.id.md_et_text);
		mHintText = (TextView) findViewById(R.id.md_et_hint);
		mInfoText = (TextView) findViewById(R.id.md_et_info);
		mBottomLine = (ImageView) findViewById(R.id.md_et_line);

		setOnClickListener(this);

	}

	private void refreshInfo() {
		if (!TextUtils.isEmpty(moreInfo)) {
			mInfoText.setText(moreInfo);
			mInfoText.setTextColor(getResources().getColor(R.color.text_hint));
			mInfoText.setVisibility(View.VISIBLE);
		} else {
			if (mIsLimied && maxLength > 0) {
				mInfoText.setText("输入长度限制：" + (mText == null ? 0 : mText.length()) + "/" + maxLength);
			}
		}
	}

	private void warnLength() {
		if (TextUtils.isEmpty(mText) || !mIsLimied || maxLength <= 0) {
			return;
		}

		if (mText.length() >= maxLength) {
			Toast.makeText(getContext(), "达到输入的最大长度", Toast.LENGTH_SHORT).show();
			mInfoText.setTextColor(Color.parseColor("#e51c23"));
			mBottomLine.setBackgroundColor(Color.parseColor("#e51c23"));
		} else {
			mInfoText.setTextColor(mHighlightColor);
			mBottomLine.setBackgroundColor(mHighlightColor);
		}
	}

	public String getText() {
		if (mText == null) {
			return "";
		} else {
			return mText.trim();
		}
	}
	
	public String getRawText() {
		if (mText == null) {
			return "";
		} else {
			return mText;
		}
	}

	public void setText(String text) {
		this.mEditText.setText(text);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			mBottomLine.setBackgroundColor(mHighlightColor);
			if (mIsLimied) {
				mInfoText.setVisibility(View.VISIBLE);
			}
			warnLength();
		} else {
			mBottomLine.setBackgroundColor(Color.parseColor("#a8a8a8"));
			if (mIsLimied) {
				mInfoText.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
	}

	@Override
	public void afterTextChanged(Editable s) {
		mText = s.toString();
		if (s.length() > 0) {
			mHintText.setVisibility(View.VISIBLE);
		} else {
			mHintText.setVisibility(View.GONE);
		}
		refreshInfo();
		warnLength();
	}

	@Override
	public void onClick(View v) {
		if (!mEditText.hasFocus()) {
			mEditText.requestFocus();
			UIUtil.displaySoftInput(getContext(), mEditText);
		}
	}
}
