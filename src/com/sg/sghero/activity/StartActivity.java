/**
 * 
 */
package com.sg.sghero.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.sg.sanguohero.R;
import com.sg.sghero.app.SgApplication;
import com.sg.sghero.db.DataProvider;
import com.sg.sghero.db.Scene;
import com.sg.sghero.util.ILog;

public class StartActivity extends Activity {
	private ViewFlipper flipper;
	private String name = "";
	private String lifeStyle = "";
	private String family = "";
	private String idear = "";
	private Scene scene = null;
	private int step = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_start);
		flipper = (ViewFlipper) findViewById(R.id.viewFlipper1);
		View v;
		v = getLayoutInflater().inflate(R.layout.layout_create_player01, null);
		flipper.addView(v);
		
		v = getLayoutInflater().inflate(R.layout.layout_create_player02, null);
		((TextView)v.findViewById(R.id.textView1)).setText(R.string.session_create_player_02);
		RadioGroup group = (RadioGroup) v.findViewById(R.id.radioGroup1);
		String[] list = getResources().getStringArray(R.array.way_life);
		for (String string : list) {
			RadioButton child = new RadioButton(this);
			child.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked)
						lifeStyle = buttonView.getText().toString();
				}
			});
			child.setText(string);
			group.addView(child);
		}
		flipper.addView(v);
		
		v = getLayoutInflater().inflate(R.layout.layout_create_player02, null);
		((TextView)v.findViewById(R.id.textView1)).setText(R.string.session_create_player_03);
		group = (RadioGroup) v.findViewById(R.id.radioGroup1);
		list = getResources().getStringArray(R.array.born_family);
		for (String string : list) {
			RadioButton child = new RadioButton(this);
			child.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked)
						family = buttonView.getText().toString();
				}
			});
			child.setText(string);
			group.addView(child);
		}
		flipper.addView(v);
		
		v = getLayoutInflater().inflate(R.layout.layout_create_player02, null);
		((TextView)v.findViewById(R.id.textView1)).setText(R.string.session_create_player_04);
		group = (RadioGroup) v.findViewById(R.id.radioGroup1);
		list = getResources().getStringArray(R.array.idear_future);
		for (String string : list) {
			RadioButton child = new RadioButton(this);
			child.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked)
						idear = buttonView.getText().toString();
				}
			});
			child.setText(string);
			group.addView(child);
		}
		flipper.addView(v);
		
		v = getLayoutInflater().inflate(R.layout.layout_create_player05, null);
		GridView gridView = (GridView) v.findViewById(R.id.gridView1);
		gridView.setAdapter(new GridViewAdapter(this));
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				scene = (Scene) parent.getItemAtPosition(position);
				int count = parent.getChildCount();
				for (int i = 0; i < count; i++) {
					((TextView)parent.getChildAt(i)).setSelected(/*false*/v == parent.getChildAt(i));
				}
			}
		});
		flipper.addView(v);
		
		SgApplication.getApplication(this).world.load(getResources());
	}
	
	public void newGame(View v){
		flipper.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom));
		flipper.setVisibility(View.VISIBLE);
	}
	
	public void switchNext(View v){
		final View view = flipper.getCurrentView();
		boolean canNext = false;
		switch (step) {
		case 0:
			name = ((EditText)view.findViewById(R.id.editText1)).getText().toString();
			canNext = name != null && !name.isEmpty();
			break;
		case 1:
			canNext = lifeStyle != null && !lifeStyle.isEmpty();
			break;
		case 2:
			canNext = family != null && !family.isEmpty();
			if(canNext){
				View nextView = flipper.getChildAt(flipper.getDisplayedChild() + 1);
				String text = getString(R.string.session_create_player_04, family);
				((TextView)nextView.findViewById(R.id.textView1)).setText(text);
			}
			break;
		case 3:
			canNext = idear != null && !idear.isEmpty();
			break;
		case 4:
			canNext = scene != null;
			break;
		}

		if(!canNext) return;
		step ++;
		flipper.showNext();
	}
	
	public void joinWorld(View v){
		SgApplication.getApplication(this).world.initWorld(this, scene, name, family, idear, lifeStyle);
		if(SgApplication.getApplication(this).world.addOnLoadingLinstener(null)){
			toOtherActivity(MainActivity.class);
		}else{
			toOtherActivity(LoadingActivity.class);
		}
		
		step = 0;
		flipper.showNext();
		flipper.setVisibility(View.GONE);
	}
	
	public void toOtherActivity(Class<?> cls){
		startActivity(new Intent(this, cls));
	}
	
	public void toOtherActivity(Class<?> cls, ContentValues cv){
		Intent i = new Intent(this, cls);
		i.putExtra("values", cv);
		startActivity(i);
	}
	
	private class GridViewAdapter extends BaseAdapter{
		private List<Scene> datas = new ArrayList<Scene>();
		public GridViewAdapter(Context c) {
			DataProvider dataProvider = new DataProvider(c);
			dataProvider.open();
			datas = Arrays.asList(dataProvider.queryAllScene());
			dataProvider.close();
			ILog.i(datas.get(0).getName());
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null)
				convertView = getLayoutInflater().inflate(R.layout.simple_grid_item, null);
			((TextView)convertView).setText(datas.get(position).getName());
			return convertView;
		}
		
	}
}
