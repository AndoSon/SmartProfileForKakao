package com.hanyang.swengineering.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.smartprofileforkakao.R;
import com.hanyang.swengineering.item.FriendListItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendListAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	private int groupResourceId;
	private int friendResourceId;
	private ArrayList<FriendListItem> groupItems;
	private ArrayList<ArrayList<FriendListItem>> childItemsList;
	
	private LayoutInflater inflater;
	
	public FriendListAdapter(Context context, int groupResourceId, int friendResourceId, ArrayList<FriendListItem> groupItems, ArrayList<ArrayList<FriendListItem>> childItemsList) {
		this.context = context;
		this.groupResourceId = groupResourceId;
		this.friendResourceId = friendResourceId;
		this.groupItems = groupItems;
		this.childItemsList = childItemsList;
		
		this.inflater = LayoutInflater.from(context);
		
	}

	@Override
	public FriendListItem getChild(int groupPosition, int childPosition) {
		return childItemsList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = convertView;
		
		FriendListItem item = getChild(groupPosition, childPosition);
		ChildViewHolder childViewHolder;
		
		if ( view == null ) {
			view = inflater.inflate(friendResourceId, parent, false);
			
			childViewHolder = new ChildViewHolder();
			childViewHolder.name = (TextView) view.findViewById(R.id.name);
			childViewHolder.profile = (ImageView) view.findViewById(R.id.profile_image);
			
			view.setTag(childViewHolder);
		} else {
			childViewHolder = (ChildViewHolder) view.getTag();
		}
		
		childViewHolder.name.setText( item.getName() );
		childViewHolder.profile.setImageResource( item.getImageResourceId() );
		
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childItemsList.get(groupPosition).size();
	}
	
	
	
	
	
	

	@Override
	public FriendListItem getGroup(int groupPosition) {
		return groupItems.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groupItems.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = convertView;
		
		FriendListItem item = getGroup(groupPosition);
		GroupHeaderViewHolder groupHeaderHolder;
		
		if ( view == null ) {
			view = inflater.inflate(groupResourceId, parent, false);
			
			groupHeaderHolder = new GroupHeaderViewHolder();
			groupHeaderHolder.title = (TextView) view.findViewById(R.id.group_header_title);
			
			view.setTag(groupHeaderHolder);
		} else {
			groupHeaderHolder = (GroupHeaderViewHolder) view.getTag();
		}
		
		groupHeaderHolder.title.setText( item.getGroupTitle() );
		
		return view;
	}
	
	class GroupHeaderViewHolder {
		TextView title;
	}
	
	class ChildViewHolder {
		ImageView profile;
		TextView name;
	}
	

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
}
