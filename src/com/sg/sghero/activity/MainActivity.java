package com.sg.sghero.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.sanguohero.R;
import com.sg.sghero.app.Action;
import com.sg.sghero.app.SgApplication;
import com.sg.sghero.app.WorldContext;
import com.sg.sghero.app.WorldContext.OnSceneChangeListener;
import com.sg.sghero.db.Scene;
import com.sg.sghero.db.SystemActor;
import com.sg.sghero.view.PropsView;

public class MainActivity extends Activity implements OnSceneChangeListener, OnItemClickListener {
	private String[] mPlanetTitles;
	private WorldContext world;
	private Scene scene;
	private PopupWindow panel;
	private ListView npcListView;
	private FrameLayout popupLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SgApplication app = SgApplication.getApplication(this);
		world = app.world;
		world.setSceneChangeListener(this);
		scene = world.getCurrentScene();
		setTitle(scene.getName());
		
		setContentView(R.layout.activity_main);
		
		mPlanetTitles = getResources().getStringArray(R.array.planets_array);
		DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		DrawerLayout.SimpleDrawerListener listener = new DrawerLayout.SimpleDrawerListener() {
			@Override
			public void onDrawerOpened(View drawerView) {
//				gamePause();
			}

			@Override
			public void onDrawerClosed(View drawerView) {
//				gameResume();
			}
		};
		mDrawerLayout.setDrawerListener(listener);
		ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mPlanetTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		npcListView = (ListView) findViewById(R.id.listView1);
		npcListView.setAdapter(new ArrayAdapter<SystemActor>(this, 
				android.R.layout.simple_list_item_1, scene.getNpcs()));
		npcListView.setOnItemClickListener(this);
		
		panel = new PopupWindow(this);
		panel.setFocusable(true);
		panel.setWindowLayoutMode(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		popupLayout = (FrameLayout) findViewById(R.id.popupLayout);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		if(scene.getType() == Scene.type_wild){
			menu.add(0, 1, 0, "寻怪");
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_exit:
			Scene[] neighbors = scene.getNeighbors();
			
			View v = getLayoutInflater().inflate(R.layout.scene_change, null);
			((TextView)v.findViewById(R.id.textView1)).setText(scene.getName());
			ListView listView = (ListView) v.findViewById(R.id.listView1);
			listView.setAdapter(new ArrayAdapter<Scene>(this, R.layout.scene_change_list, neighbors));
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View arg1,
						int position, long arg3) {
					Scene newScene = (Scene) parent.getItemAtPosition(position);
					world.enterNewScene(MainActivity.this, newScene);
					panel.dismiss();
				}
			});
			panel.setContentView(v);
			panel.showAtLocation(findViewById(R.id.origin), Gravity.CENTER, 0, 0);
			return true;
		case 1:
			Toast.makeText(this, "开始战斗", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onBackPressed() {
		if(popupLayout.isShown()){
			popupLayout.removeAllViews();
			popupLayout.setVisibility(View.GONE);
		}
		super.onBackPressed();
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			boolean b = false;
			switch (position) {
			case 0:
				b = showPlayerPanel();
				break;
			case 1:
				b = showEquipmentPanel();
				break;
			case 2:
				b = showLuggagePanel();
				break;
			case 3:
				b = showQuestPanel();
				break;
			case 4:
				b = showSkillPanel();
				break;
			case 5:
				b = showPartnerPanel();
				break;
			case 6:
				b = showSystemPanel();
				break;
			default:
				break;
			}
			if (b)
				return;
		}
}

	public boolean showSystemPanel() {
		return false;
	}

	public boolean showPlayerPanel() {
		return false;
	}

	public boolean showEquipmentPanel() {
		return false;
	}

	public boolean showLuggagePanel() {
		return false;
	}

	public boolean showQuestPanel() {
		return false;
	}

	public boolean showSkillPanel() {
		return false;
	}

	public boolean showPartnerPanel() {
		return false;
	}

	@Override
	public void sceneChanged(Scene newScene) {
		scene = newScene;
		setTitle(newScene.getName());
		npcListView.setAdapter(new ArrayAdapter<SystemActor>(this, 
				android.R.layout.simple_list_item_1, scene.getNpcs()));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		SystemActor actor = (SystemActor) parent.getItemAtPosition(position);
		final String action = actor.getAction();
		View view = getLayoutInflater().inflate(R.layout.layout_action, null);
		((Button)view.findViewById(R.id.button1)).setText(Action.show(this, action));
		view.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				panel.dismiss();
				handleAction(action, 1);
			}
		});
		panel.setContentView(view);
		panel.showAtLocation(findViewById(R.id.origin), Gravity.CENTER, 0, 0);
	}

	protected void handleAction(String action, int type) {
		if(action.equalsIgnoreCase("merchandise")){
			popupLayout.setVisibility(View.VISIBLE);
			PropsView view = new PropsView(this);
			view.setTitle("商店");
			view.setCloseButton(popupLayout);
			view.setPropsGrid(null, new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
				}
			});
			popupLayout.addView(view);
		}
	}
}
