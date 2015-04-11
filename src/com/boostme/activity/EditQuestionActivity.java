package com.boostme.activity;

import android.os.Bundle;

public class EditQuestionActivity extends BMActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_detail);
	}
	
	@Override
	protected String getActivitiTitle()
	{
		return "提问";
	}
}
