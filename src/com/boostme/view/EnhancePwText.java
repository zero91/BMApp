package com.boostme.view;

import android.content.Context;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;

public class EnhancePwText extends EnhanceEditText {

	public EnhancePwText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public EnhancePwText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public EnhancePwText(Context context) {
		super(context);
	}

	@Override
	protected void custom() {
	     getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
	}
}
