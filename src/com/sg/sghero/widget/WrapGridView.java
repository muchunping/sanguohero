package com.sg.sghero.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.AdapterView;
import android.widget.ListAdapter;

public class WrapGridView extends AdapterView<ListAdapter> {
	private ListAdapter mAdapter;
	private int mSelectPos;
	private int mNumColumns;

	public WrapGridView(Context context) {
		super(context);
	}

	public WrapGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WrapGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public ListAdapter getAdapter() {
		return mAdapter;
	}

	@Override
	public void setSelection(int position) {
		mSelectPos = position;
	}

	@Override
	public View getSelectedView() {
		return null;
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		mAdapter = adapter;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        if(mAdapter == null) return;
        final View child = mAdapter.getView(0, null, this);
        int childHeightSpec = getChildMeasureSpec(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 0, 0);
        int childWidthSpec = getChildMeasureSpec(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 0, 0);
        child.measure(childWidthSpec, childHeightSpec);
		int childWidth = child.getWidth();
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int widthSize = childWidth * mNumColumns;
		
		setMeasuredDimension(widthSize, heightSize);
	}
}
