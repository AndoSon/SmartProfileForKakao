package com.hanyang.swengineering.smartprofileforkakao;

import android.app.Activity;
import android.os.Bundle;

import com.example.smartprofileforkakao.R;

public class GroupSettingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_setting);
		
		setTitle("그룹 설정하기");
	}

}
