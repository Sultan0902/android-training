package com.example.iremember;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class listviewadapter extends BaseAdapter{
	
	private static List<Story_item> storyList = new ArrayList<Story_item>();
	private List<Story_item> tempList = new ArrayList<Story_item>();
	private Context mcontext;
	
	
	public listviewadapter(Context context)
	{
		mcontext = context;
	}
	@Override
	public int getCount() {
		
		return storyList.size();
	}
	
	public Story_item getItemfromPos(int pos)
	{
		return storyList.get(pos);
	}

	@Override
	public Object getItem(int pos) {
		
		return storyList.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		
		Story_item item = storyList.get(pos);
		return item.getStoryid();
	}
	
	public void additem(Story_item story)
	{
		storyList.add(story);
		tempList.add(story);
		notifyDataSetChanged();
	}
	
	public void clearAll()
	{
		tempList.clear();
		storyList.clear();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Story_item item = (Story_item) getItem(position);
		LayoutInflater inflator = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final LinearLayout itemlayout = (LinearLayout) inflator.inflate(R.layout.story_item, null);
		TextView Story_Key = (TextView) itemlayout.findViewById(R.id.story_key);
		TextView Story_Name = (TextView) itemlayout.findViewById(R.id.story_name);
		TextView story_Time = (TextView) itemlayout.findViewById(R.id.story_time);
		
		Story_Key.setText("" + item.key_id);
		Story_Name.setText(item.title);
		Date date = new Date(item.storyTime);
		String storydate = Story_item.FORMAT.format(date);
		story_Time.setText(storydate);
		
		return itemlayout;
	}
	
	public void searchFilter(String searchText)
	{
		searchText = searchText.toLowerCase(Locale.getDefault());
		
		storyList.clear();
		if(searchText.length() == 0)
		{
			storyList.addAll(tempList);
		}
		else
		{
			for(Story_item item : tempList)
			{
				if(item.title.toLowerCase(Locale.getDefault()).contains(searchText))
				{
					storyList.add(item);
				}
			}
		}
		notifyDataSetChanged();
	}

}
