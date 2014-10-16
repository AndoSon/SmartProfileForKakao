package com.hanyang.swengineering.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartprofileforkakao.R;
import com.hanyang.swengineering.smartprofileforkakao.MainActivity;

public class ChatListFragment extends Fragment {
	
	private MainActivity rootActivity;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chat_list, null);

		rootActivity = (MainActivity) getActivity();

		getViewsComponent(view);
		setViewsComponent();

		return view;
	}

	private void getViewsComponent(View view) {
	}

	private void setViewsComponent() {
	}

}
