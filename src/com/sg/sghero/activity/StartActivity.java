/**
 * 
 */
package com.sg.sghero.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

import com.sg.sanguohero.R;

public class StartActivity extends Activity {
	private ViewSwitcher animatorContainer;
	private final int[] sessions = {
			R.string.session_create_player_01,
			R.string.session_create_player_02,
			R.string.session_create_player_03,
			R.string.session_create_player_04,
			R.string.session_create_player_05
	};
	private final int[] selection = {
			R.array.way_life,
			R.array.born_family,
			R.array.idear_future
	};
	private int step = 0;
	
	private String born_family;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		animatorContainer = (ViewSwitcher) findViewById(R.id.viewSwitcher1);
		animatorContainer.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				View v = getLayoutInflater().inflate(R.layout.layout_create_player, null);
				if(step == 3){
					String text = getString(sessions[step], born_family);
					((TextView)v.findViewById(R.id.textView1)).setText(text);
				}else
					((TextView)v.findViewById(R.id.textView1)).setText(sessions[step]);
				if(step == 1 || step == 2 || step ==3){
					ListView listView = (ListView) v.findViewById(R.id.listView1);
					listView.setAdapter(new BaseAdapter() {
						public String[] datas = getResources().getStringArray(selection[step]);
						
						@Override
						public View getView(int position, View convertView, ViewGroup parent) {
							if(convertView == null){
								convertView = getLayoutInflater().inflate(R.layout.radiobutton, null);
							}
							((TextView)convertView).setText(datas[position]);
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
							return datas.length;
						}
					});
				}
				
				return v;
			}
		});
		animatorContainer.setInAnimation(this, android.R.anim.fade_in);
		animatorContainer.setOutAnimation(this, android.R.anim.fade_out);
	}
	
	public void newGame(View v){
		animatorContainer.setVisibility(View.VISIBLE);
		animatorContainer.showNext();
		step ++;
	}
	
	public void switchNext(View v){
		if(step > 4) return;
		animatorContainer.showNext();
		step ++;
	}
}
