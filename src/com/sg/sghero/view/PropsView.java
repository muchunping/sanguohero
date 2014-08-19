package com.sg.sghero.view;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sg.sanguohero.R;
import com.sg.sghero.db.Props;
import com.sg.sghero.resoure.IconsCache;
import com.sg.sghero.util.CBaseAdapter;

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
	
	public void setPropsGrid(final List<Props> props, OnItemClickListener l){
		propsView.setOnItemClickListener(l);
		propsView.setAdapter(new CBaseAdapter<Props>(props) {

			@Override
			protected View newView() {
				return getLayoutInflater().inflate(R.layout.layout_grid_props_shop, null);
			}

			@Override
			protected void bindView(int position, View convertView, Props t) {
				String[] photo = t.getPhotoString().split(Props.PHOTO_SYMBOL);
				Bitmap icon = IconsCache.getInstance().getIconByFileAndLocalId(getResources(), 
						Props.PHOTO_PREFIX + photo[0], 
						Integer.parseInt(photo[1]));
				if(icon != null)
					((ImageView)convertView.findViewById(R.id.imageView1)).setImageBitmap(icon);
			}
		});
	}

	protected LayoutInflater getLayoutInflater() {
		return LayoutInflater.from(getContext());
	}
}
