package com.mu.sanguo.heroes.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mu.sanguo.heroes.model.Scene;
import com.mu.sanguo.heroes.model.SgScene;

import java.util.ArrayList;
import java.util.List;

import cn.igame.sanguoheros.R;

/**
 * 网格排列的单选按钮组
 * Created by Administrator on 2015/12/14.
 */
public class RadioGroupPlus extends ViewGroup {
    private float mHorizontalMargin;
    private float mVerticalMargin;
    private int mColumn;
    private int mLayoutId;
    private List<String> mItemList = new ArrayList<>();
    private int currentChecked;

    public RadioGroupPlus(Context context) {
        this(context, null);
    }

    public RadioGroupPlus(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadioGroupPlus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.radioGroupPlusStyle, defStyleAttr, 0);
        mColumn = a.getInt(R.styleable.radioGroupPlusStyle_column, 3);
        int horizontalMargin = getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin);
        int verticalMargin = getResources().getDimensionPixelOffset(R.dimen.activity_vertical_margin);
        mHorizontalMargin = a.getDimension(R.styleable.radioGroupPlusStyle_horizontal_spacing, horizontalMargin);
        mVerticalMargin = a.getDimension(R.styleable.radioGroupPlusStyle_vertical_spacing, verticalMargin);
        mLayoutId = a.getResourceId(R.styleable.radioGroupPlusStyle_layout_id, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        final int widthMode = MeasureSpec.getMode(widthSpec);
        final int heightMode = MeasureSpec.getMode(heightSpec);
        final int widthSize = MeasureSpec.getSize(widthSpec);
        final int heightSize = MeasureSpec.getSize(heightSpec);

        int width;
        int height;

        View childView = getChildAt(0);
        int childWith = 0;
        int childHeight = 0;
        if (childView != null) {
            int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            childView.measure(spec, spec);
            childWith = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
        }

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
            default:
                width = childWith * mColumn
                        + getPaddingLeft() + getPaddingRight();
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
            default:
                int line = (getChildCount() - 1) / mColumn + 1;
                height = (int) (childHeight * line + (line - 1) * mVerticalMargin
                        + getPaddingBottom() + getPaddingTop());
                break;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int top = getPaddingTop();

        int count = getChildCount();
        if (count > 0) {
            View childView = getChildAt(0);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            // 为了居中显示，设置偏移量
            int width = (int) (childWidth * mColumn + (mColumn - 1) * mHorizontalMargin);
            int delta = (getMeasuredWidth() - width - getPaddingLeft() - getPaddingRight()) / 2;

            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                int column = i % mColumn;
                if (column == 0) { //换行
                    left = getPaddingLeft();
                    if (i != 0) { //非第一行
                        top += childHeight + mVerticalMargin;
                    }
                } else {
                    left += childWidth + mHorizontalMargin;
                }

                setChildFrame(child, left + delta, top, childWidth, childHeight);
            }
        }
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }

    public void setItemList(List<Scene> itemList) {
        mItemList.clear();

        int position = 0;
        for (Scene o : itemList) {
            mItemList.add(o.getName());
            View child = getChildView(position);
            if (child != null) {
                child.setTag(o);
                child.setOnClickListener(new OnViewClicked(position));
                addView(child);
                if(position == currentChecked){
                    child.setSelected(true);
                }
                child.setBackgroundResource(o.getType() == SgScene.TYPE_WILD ? R.drawable.bg_outlet_danger : R.drawable.bg_outlet);
            }
            position ++;
        }

        requestLayout();
        invalidate();
    }

    public void setColumn(int mColumn) {
        this.mColumn = mColumn;
    }

    public void setLayoutId(int mLayoutId) {
        this.mLayoutId = mLayoutId;
    }

    public Object getCheckedTarget() {
        View view = getChildAt(currentChecked);
        if (view == null)
            return null;
        return view.getTag();
    }

    @Nullable
    private View getChildView(int position) {
        if (mLayoutId > 0) {
            View child = LayoutInflater.from(getContext()).inflate(mLayoutId, this, false);
            if (mItemList.size() > position)
                ((TextView) child).setText(mItemList.get(position));
            return child;
        } else {
            return null;
        }
    }

    private class OnViewClicked implements OnClickListener {
        private final int position;

        public OnViewClicked(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            getChildAt(currentChecked).setSelected(false);
            v.setSelected(true);
            currentChecked = position;
        }
    }
}
