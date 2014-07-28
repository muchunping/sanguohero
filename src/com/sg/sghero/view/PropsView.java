package com.sg.sghero.view;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sg.sanguohero.R;
import com.sg.sghero.db.Props;
import com.sg.sghero.resoure.IconResourceFile;
import com.sg.sghero.resoure.IconsCache;
import com.sg.sghero.util.Size;

public class PropsView extends LinearLayout {
	private TextView titleView;
	private ImageButton closeButton;
	private GridView propsView;

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
		propsView = (GridView) findViewById(R.id.gridView1);
	}
	
	public void setTitle(String title){
		titleView.setText(title);
	}
	
	public void setCloseButton(final ViewGroup popupView){
		View.OnClickListener l = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popupView.removeAllViews();
				popupView.setVisibility(View.GONE);
			}
		};
		closeButton.setOnClickListener(l);
	}
	
	public void setPropsGrid(List<Props> props, OnItemClickListener l){
		propsView.setAdapter(new BaseAdapter() {
			IconResourceFile iconFile = new IconResourceFile(getResources(), 
					R.drawable.items_consumables, new Size(14, 5), new Size(32, 32));
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null)
					convertView = getLayoutInflater().inflate(R.layout.layout_grid_props, null);
				((TextView) convertView.findViewById(R.id.textView1)).setText("" + position);
				convertView.findViewById(R.id.textView1).setVisibility(View.GONE);
				Bitmap bm = IconsCache.getInstance().getIconByFileAndLocalId(getResources(), 
						iconFile.getName(), position);
				((ImageView)convertView.findViewById(R.id.imageView1)).setImageBitmap(bm);
				return convertView;
			}
			
			@Override
			public long getItemId(int position) {
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				return null;
			}
			
			@Override
			public int getCount() {
				return iconFile.getNumSize().quadrature();
			}
		});
	}

	protected LayoutInflater getLayoutInflater() {
		return LayoutInflater.from(getContext());
	}
}
