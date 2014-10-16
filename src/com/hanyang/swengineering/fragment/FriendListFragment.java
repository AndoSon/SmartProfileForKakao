package com.hanyang.swengineering.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smartprofileforkakao.R;
import com.hanyang.swengineering.adapter.FriendListAdapter;
import com.hanyang.swengineering.item.FriendListItem;
import com.hanyang.swengineering.smartprofileforkakao.GroupSettingActivity;
import com.hanyang.swengineering.smartprofileforkakao.MainActivity;

public class FriendListFragment extends Fragment implements OnClickListener {

	private MainActivity rootActivity;
	private Button btnAddGroup;

	private ExpandableListView friendListView;
	private ImageView myProfileImage;
	private FriendListAdapter friendListAdapter;
	private ArrayList<FriendListItem> friendListItems;
	private ArrayList<ArrayList<FriendListItem>> childItemsList;

	// private ArrayList<>

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_friend_list, null);

		rootActivity = (MainActivity) getActivity();

		getViewsComponent(view);
		setViewsComponent();

		return view;
	}

	private void getViewsComponent(View view) {
		btnAddGroup = (Button) view.findViewById(R.id.btn_add_group);
		myProfileImage = (ImageView) view.findViewById(R.id.my_profile_image);
		friendListView = (ExpandableListView) view
				.findViewById(R.id.friend_list);
		friendListItems = new ArrayList<FriendListItem>();
		childItemsList = new ArrayList<ArrayList<FriendListItem>>();
	}

	private void setViewsComponent() {
		btnAddGroup.setOnClickListener(this);
		myProfileImage.setOnClickListener(this);
		friendListAdapter = new FriendListAdapter(rootActivity,
				R.layout.friend_list_group_header_item,
				R.layout.friend_list_item, friendListItems, childItemsList);
		friendListView.setAdapter(friendListAdapter);
		friendListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				int itemType = ExpandableListView.getPackedPositionType(id);
				if ( itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP ) {
					startActivity(new Intent(rootActivity, GroupSettingActivity.class) );
					return true;
				} else if ( itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD ) {
					showUserSettingDialog();
					return true;
				}
				return false;
			}
		});
		refreshFriendsList();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_group:
			showAddGroupDialog();
			break;
		case R.id.my_profile_image:
			rootActivity.setPrefValue("group_list", "");
			refreshFriendsList();
			break;
		default:
			break;
		}
	}
	
	private void showUserSettingDialog() {
		new AlertDialog.Builder(rootActivity)
		.setTitle("User Setting")
		.setItems(R.array.user_setting_dialog_items, null)
		.show();
	}

	private void showAddGroupDialog() {

		final EditText group_name_input = new EditText(rootActivity);

		new AlertDialog.Builder(rootActivity)
				.setTitle("Add group")
				.setMessage("추가하실 그룹명을 입력해주세요.")
				.setView(group_name_input)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String value = group_name_input.getText().toString();
						String groupListString = rootActivity
								.getPrefValue("group_list");
						rootActivity.setPrefValue("group_list",
								groupListString.concat("@" + value));
						refreshFriendsList();
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						}).show();
	}

	private void refreshFriendsList() {

		String groupListString = rootActivity.getPrefValue("group_list");
		String[] groupList = groupListString.split("@");
		Boolean[] addFlag = new Boolean[3];
		addFlag[0] = new Boolean(false);
		addFlag[1] = new Boolean(false);
		addFlag[2] = new Boolean(false);

		friendListItems.clear();
		childItemsList.clear();
		
		for (String item : groupList) {
			if (item == groupList[0])
				continue;
			friendListItems.add(new FriendListItem(true, item));
			ArrayList<FriendListItem> childItems = new ArrayList<FriendListItem>();
			if (item.equals("가족")) {
				childItems.add(new FriendListItem(false, "아버지",
						R.drawable.profile_male_sample));
				childItems.add(new FriendListItem(false, "어머니",
						R.drawable.profile_female_sample));
				childItems.add(new FriendListItem(false, "형",
						R.drawable.profile_male_sample2));
				addFlag[0] = true;
			} else if (item.equals("대학교")) {
				childItems.add(new FriendListItem(false, "유인경 교수님",
						R.drawable.profile_male_sample3));
				childItems.add(new FriendListItem(false, "손광훈",
						R.drawable.profile_male_sample2));
				childItems.add(new FriendListItem(false, "박주람",
						R.drawable.profile_male_sample));
				addFlag[1] = true;
			} else if (item.equals("직장")) {
				childItems.add(new FriendListItem(false, "유 부장님",
						R.drawable.profile_male_sample));
				childItems.add(new FriendListItem(false, "김 차장님",
						R.drawable.profile_male_sample2));
				childItems.add(new FriendListItem(false, "박 대리님",
						R.drawable.profile_male_sample3));
				addFlag[2] = true;
			}
			childItemsList.add(childItems);
		}
		friendListItems.add(new FriendListItem(true, "일반"));
		ArrayList<FriendListItem> childItems = new ArrayList<FriendListItem>();
		if ( addFlag[0] == false ) {
			childItems.add(new FriendListItem(false, "아버지",
					R.drawable.profile_male_sample));
			childItems.add(new FriendListItem(false, "어머니",
					R.drawable.profile_female_sample));
			childItems.add(new FriendListItem(false, "형",
					R.drawable.profile_male_sample2));
		}
		if ( addFlag[1] == false ) {
			childItems.add(new FriendListItem(false, "유인경 교수님",
					R.drawable.profile_male_sample3));
			childItems.add(new FriendListItem(false, "손광훈",
					R.drawable.profile_male_sample2));
			childItems.add(new FriendListItem(false, "박주람",
					R.drawable.profile_male_sample));
		}
		if ( addFlag[2] == false ) {
			childItems.add(new FriendListItem(false, "유 부장님",
					R.drawable.profile_male_sample));
			childItems.add(new FriendListItem(false, "김 차장님",
					R.drawable.profile_male_sample2));
			childItems.add(new FriendListItem(false, "박 대리님",
					R.drawable.profile_male_sample3));
		}
		childItemsList.add(childItems);
		friendListAdapter.notifyDataSetChanged();
	}
}
