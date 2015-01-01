package com.boostme.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.boostme.activity.R;

public class ButtonFragment extends Fragment{
	Button myButton;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View rootView = inflater.inflate(R.layout.guide_1, container, false);
		myButton = (Button)rootView.findViewById(R.id.mybutton);
		
		myButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "button is clicked", Toast.LENGTH_SHORT);
			}
		});
		
		return rootView;
	}
	
	

}
