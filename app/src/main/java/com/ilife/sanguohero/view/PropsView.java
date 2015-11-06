package com.ilife.sanguohero.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ilife.sanguohero.R;
import com.ilife.sanguohero.db.Props;
import com.ilife.sanguohero.resoure.IconsCache;

import java.util.ArrayList;
import java.util.List;

public class PropsView extends LinearLayout {
	private TextView titleView;
	private ImageButton closeButton;
	private final List<Props> propsList = new ArrayList<>();
	private RecyclerViewAdapter adapter;

	public PropsView(Context context) {
		super(context);
		init(context);
	}

	public PropsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PropsView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context c){
		LayoutInflater.from(c).inflate(R.layout.props_grid, this);
		titleView = (TextView) findViewById(R.id.textView1);
		closeButton = (ImageButton) findViewById(R.id.imageButton1);
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		final int gap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, displayMetrics);
		GridLayoutManager layoutManager = new GridLayoutManager(c, 5, GridLayoutManager.VERTICAL, false) {

			@Override
			public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
				final int widthMode = View.MeasureSpec.getMode(widthSpec);
				final int heightMode = View.MeasureSpec.getMode(heightSpec);
				final int widthSize = View.MeasureSpec.getSize(widthSpec);
				final int heightSize = View.MeasureSpec.getSize(heightSpec);

				int width;
				int height;

				View itemView = recycler.getViewForPosition(0);
				int childWith = 0;
				int childHeight = 0;
				if (itemView != null) {
					int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
					itemView.measure(spec, spec);
					childWith = itemView.getMeasuredWidth();
					childHeight = itemView.getMeasuredHeight();
				}

				switch (widthMode) {
					case View.MeasureSpec.EXACTLY:
						width = widthSize;
						break;
					case View.MeasureSpec.AT_MOST:
					case View.MeasureSpec.UNSPECIFIED:
					default:
						width = childWith * getSpanCount() + gap * (getSpanCount() + 1)
								+ getPaddingLeft() + getPaddingRight();
						break;
				}

				switch (heightMode) {
					case View.MeasureSpec.EXACTLY:
						height = heightSize;
						break;
					case View.MeasureSpec.AT_MOST:
					case View.MeasureSpec.UNSPECIFIED:
					default:
						int line = getItemCount() / getSpanCount() + (getItemCount() % getSpanCount() == 0 ? 0 : 1);
						height = childHeight * line  + gap * (line + 1)
								+ getPaddingBottom() + getPaddingTop();
						break;
				}

				setMeasuredDimension(width, height);
			}
		};
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);
		recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);

				int position = parent.getChildAdapterPosition(view); // item position
				GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
				int spanCount = layoutManager.getSpanCount();
				int column = position % spanCount; // item column

				outRect.left = gap - column * gap / spanCount; // spacing - column * ((1f / spanCount) * spacing)
				outRect.right = (column + 1) * gap / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

				if (position < spanCount) { // top edge
					outRect.top = gap;
				}
				outRect.bottom = gap; // item bottom
			}
		});
		adapter = new RecyclerViewAdapter();
		recyclerView.setAdapter(adapter);
	}
	
	public void setTitle(String title){
		titleView.setText(title);
	}
	
	public void setCloseButton(final ViewGroup popupView){
		OnClickListener l = new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupView.removeAllViews();
				popupView.setVisibility(View.GONE);
			}
		};
		closeButton.setOnClickListener(l);
	}
	
	public void setPropsGrid(final List<Props> props, View.OnClickListener l){
		if(props.isEmpty()) return;
		propsList.clear();
		propsList.addAll(props);
		adapter.setOnItemClickListener(l);
		adapter.notifyDataSetChanged();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ImageView imageView;

		public ViewHolder(View view) {
			super(view);
			imageView = (ImageView) view.findViewById(R.id.imageView);
		}
	}

	private class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder>{
		private View.OnClickListener listener;

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			if(position >= propsList.size()) {
				holder.imageView.setImageBitmap(null);
				return;
			}
			Props t = propsList.get(position);
			String[] photo = t.getPhotoString().split(Props.PHOTO_SYMBOL);
			Bitmap icon = IconsCache.getInstance().getIconByFileAndLocalId(getResources(),
					Props.PHOTO_ITEM_PREFIX + photo[0],
					Integer.parseInt(photo[1]));
			if (icon != null)
				holder.imageView.setImageBitmap(icon);
			holder.itemView.setTag(position);
			if(listener != null)
				holder.itemView.setOnClickListener(listener);
		}

		@Override
		public int getItemCount() {
			return propsList.size() + 12;
		}

		public void setOnItemClickListener(View.OnClickListener listener){
			this.listener = listener;
		}
	}
}
