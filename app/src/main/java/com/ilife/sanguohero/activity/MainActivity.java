package com.ilife.sanguohero.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ilife.sanguohero.R;
import com.ilife.sanguohero.app.ActionUtil;
import com.ilife.sanguohero.app.SgApplication;
import com.ilife.sanguohero.app.WorldContext;
import com.ilife.sanguohero.db.Actor;
import com.ilife.sanguohero.db.Consumables;
import com.ilife.sanguohero.db.DataProvider;
import com.ilife.sanguohero.db.Monster;
import com.ilife.sanguohero.db.Props;
import com.ilife.sanguohero.db.Scene;
import com.ilife.sanguohero.db.SystemActor;
import com.ilife.sanguohero.resoure.IconsCache;
import com.ilife.sanguohero.util.ILog;
import com.ilife.sanguohero.view.PlayerInfoView;
import com.ilife.sanguohero.view.PropsView;

public class MainActivity extends Activity implements WorldContext.OnSceneChangeListener, OnItemClickListener {
	private String[] mPlanetTitles;
	private WorldContext world;
	private Scene scene;
	private PopupWindow panel;
	private ListView npcListView;
	private FrameLayout popupLayout;
	private DataProvider dataProvider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SgApplication app = SgApplication.getApplication(this);
		world = app.world;
		world.setSceneChangeListener(this);
		scene = world.getCurrentScene();
		setTitle(scene.getName());
		
		dataProvider = new DataProvider(this);
		
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
	protected void onResume() {
		super.onResume();
		dataProvider.open();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		dataProvider.close();
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
			String where = Monster.FIELD_LOCATION + "=" + scene.getCode();
			Monster[] monsters = dataProvider.queryMonster(where);
			int random = (int)(Math.random() * monsters.length);
			final Monster monster = monsters[random];
			ILog.i("你寻到了一只" + monster.getName() + "!");
			world.setAttackers(new Actor[]{world.player});
			world.setDefenders(new Actor[]{monster});
			Intent intent = new Intent(this, BattleActivity.class);
			startActivity(intent);
			world.setState(WorldContext.State.Battle);
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
			return ;
		}
		super.onBackPressed();
	}

	private class DrawerItemClickListener implements OnItemClickListener {
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
				panel.showAtLocation(findViewById(R.id.origin), Gravity.CENTER, 0, 0);
		}
}

	public boolean showSystemPanel() {
		return false;
	}

	public boolean showPlayerPanel() {
		PlayerInfoView view = new PlayerInfoView(this);
		view.setPlayer(world.player);
		panel.setContentView(view);
		return true;
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
		invalidateOptionsMenu();
		npcListView.setAdapter(new ArrayAdapter<SystemActor>(this, 
				android.R.layout.simple_list_item_1, scene.getNpcs()));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		final SystemActor actor = (SystemActor) parent.getItemAtPosition(position);
		final String[] actions = actor.getAction();
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		for (int i = 0; i < actions.length; i++) {
			Button b = new Button(this);
			final String action = actions[i];
			String actionString = ActionUtil.show(this, action);
			b.setText(actionString);
			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					panel.dismiss();
					handleAction(actor, action);
				}
			});
			layout.addView(b);
		}
		panel.setContentView(layout);
		panel.showAtLocation(findViewById(R.id.origin), Gravity.CENTER, 0, 0);
	}

	protected void handleAction(SystemActor actor, String action) {
		popupLayout.setVisibility(View.VISIBLE);
		if(action.equalsIgnoreCase("merchandise")){
			PropsView view = new PropsView(this);
			view.setTitle("商店");
			view.setCloseButton(popupLayout);
			List<Props> props = new ArrayList<Props>();
			switch (actor.getCode()) {
			case SystemActor.CODE_WEAPON:
				props.addAll(Arrays.asList(dataProvider.queryAllConsumables()));
				break;
			case SystemActor.CODE_ARMOR:
				break;
			case SystemActor.CODE_JEWELRY:
				break;
			case SystemActor.CODE_DRUG:
				props.addAll(Arrays.asList(dataProvider.queryAllConsumables()));
				break;
			case SystemActor.CODE_HOTEL:
				break;
			case SystemActor.CODE_HOTEL_DEPUTY:
				break;
			case SystemActor.CODE_TAVERN:
				break;

			default:
				break;
			}
			view.setPropsGrid(props, new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Consumables props = (Consumables) parent.getItemAtPosition(position);
					showPropsInfo(props);
				}
			});
			popupLayout.addView(view);
		}
	}

	protected void showPropsInfo(Consumables props) {
		View view = getLayoutInflater().inflate(R.layout.layout_props_info, null);
		((TextView)view.findViewById(R.id.textView1)).setText(props.getName());
		String[] photo = props.getPhotoString().split(Props.PHOTO_SYMBOL);
		Bitmap icon = IconsCache.getInstance().getIconByFileAndLocalId(getResources(),
				Props.PHOTO_PREFIX + photo[0], 
				Integer.parseInt(photo[1]));
		if(icon != null)
			((ImageView)view.findViewById(R.id.imageView1)).setImageBitmap(icon);
		((TextView)view.findViewById(R.id.textView2)).setText(props.getEffectsString());
		String priceString = getString(R.string.price_gold, props.getPrice());
		((TextView)view.findViewById(R.id.textView3)).setText(priceString);
		panel.setContentView(view);
		panel.showAtLocation(findViewById(R.id.origin), Gravity.CENTER, 0, 0);
	}
}
