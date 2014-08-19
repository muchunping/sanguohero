package com.sg.sghero.util;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CBaseAdapter<T> extends BaseAdapter {
	private List<T> datas;

	public CBaseAdapter() {
		datas = new ArrayList<T>();
	}
	
	public CBaseAdapter(List<T> list) {
		datas = list;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public T getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = newView();
		bindView(position, convertView, datas.get(position));
		return convertView;
	}

	protected abstract View newView();
	protected abstract void bindView(int position, View convertView, T t);
}
