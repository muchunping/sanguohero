package com.gpl.rpg.AndorsTrail.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.gpl.rpg.AndorsTrail.AndorsTrailApplication;
import com.gpl.rpg.AndorsTrail.AndorsTrailPreferences;
import com.gpl.rpg.AndorsTrail.R;
import com.gpl.rpg.AndorsTrail.activity.fragment.StartScreenActivity_MainMenu;
import com.gpl.rpg.AndorsTrail.activity.fragment.StartScreenActivity_MainMenu.OnNewGameRequestedListener;
import com.gpl.rpg.AndorsTrail.activity.fragment.StartScreenActivity_NewGame;
import com.gpl.rpg.AndorsTrail.activity.fragment.StartScreenActivity_NewGame.GameCreationOverListener;
import com.gpl.rpg.AndorsTrail.resource.tiles.TileManager;
import com.gpl.rpg.AndorsTrail.util.ThemeHelper;
import com.gpl.rpg.AndorsTrail.view.CloudsAnimatorView;

public final class StartScreenActivity extends FragmentActivity implements OnNewGameRequestedListener, GameCreationOverListener, OnBackStackChangedListener {

	private TextView tv;
	private TextView development_version;
	private CloudsAnimatorView clouds_back, clouds_mid, clouds_front;
	private Fragment currentFragment;
	
	//Means false by default, as a toggle is initiated in onCreate.
	boolean ui_visible = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		initPreferences();
		setTheme(ThemeHelper.getBaseTheme());
		super.onCreate(savedInstanceState);

		final AndorsTrailApplication app = AndorsTrailApplication.getApplicationFromActivity(this);
		final Resources res = getResources();
		TileManager tileManager = app.getWorld().tileManager;
		tileManager.setDensity(res);
		app.setWindowParameters(this);

		setContentView(R.layout.startscreen);

		if (findViewById(R.id.startscreen_fragment_container) != null) {
			StartScreenActivity_MainMenu mainMenu = new StartScreenActivity_MainMenu();
			
			getSupportFragmentManager().beginTransaction()
				.replace(R.id.startscreen_fragment_container, mainMenu)
				.commit();
			currentFragment = mainMenu;
			
			getSupportFragmentManager().addOnBackStackChangedListener(this);
		}
		
		
		
		tv = (TextView) findViewById(R.id.startscreen_version);
		tv.setText('v' + AndorsTrailApplication.CURRENT_VERSION_DISPLAY);
		
		development_version = (TextView) findViewById(R.id.startscreen_dev_version);
		if (AndorsTrailApplication.DEVELOPMENT_INCOMPATIBLE_SAVEGAMES) {
			development_version.setText(R.string.startscreen_incompatible_savegames);
			development_version.setVisibility(View.VISIBLE);
		} else if (!AndorsTrailApplication.IS_RELEASE_VERSION) {
			development_version.setText(R.string.startscreen_non_release_version);
			development_version.setVisibility(View.VISIBLE);
		}
		

		clouds_back = (CloudsAnimatorView) findViewById(R.id.ts_clouds_animator_back);
		if (clouds_back != null) clouds_back.setCloudsCountAndLayer(40, CloudsAnimatorView.Layer.below);
		clouds_mid = (CloudsAnimatorView) findViewById(R.id.ts_clouds_animator_mid);
		if (clouds_mid != null) clouds_mid.setCloudsCountAndLayer(15, CloudsAnimatorView.Layer.center);
		clouds_front = (CloudsAnimatorView) findViewById(R.id.ts_clouds_animator_front);
		if (clouds_front != null) clouds_front.setCloudsCountAndLayer(8, CloudsAnimatorView.Layer.above);
		
		View background = findViewById(R.id.title_bg);
		if (background != null) {
			background.setOnClickListener(new View.OnClickListener() {
			
				@Override
				public void onClick(View v) {
					toggleUiVisibility();
				}
			});
		}
		
		if (development_version.getVisibility() == View.VISIBLE) {
			development_version.setText(development_version.getText()
//					+
//					"\nMax Heap: " + Runtime.getRuntime().maxMemory() / (1024 * 1024) + "MB"+
//					"\nUsed Heap: " + Runtime.getRuntime().totalMemory() / (1024 * 1024) + "MB"+
//					"\nTile size: " + (int) (32 * res.getDisplayMetrics().density)
					);
		}

		toggleUiVisibility();
		
		app.getWorldSetup().startResourceLoader(res);

	}
	
	private void toggleUiVisibility() {
		ui_visible = !ui_visible; 
		int visibility = ui_visible ? View.VISIBLE : View.GONE;
		if (tv != null) tv.setVisibility(visibility);
		if (development_version != null) development_version.setVisibility(visibility);
		if (currentFragment != null) {
			if (ui_visible) {

				development_version.setText(
						development_version.getText()
//						+
//						"\nMax Heap: " + Runtime.getRuntime().maxMemory() / (1024 * 1024) + "MB"+
//						"\nUsed Heap: " + Runtime.getRuntime().totalMemory() / (1024 * 1024) + "MB"+
//						"\nTile size: " + (int) (32 * getResources().getDisplayMetrics().density)
						);
				
				getSupportFragmentManager().beginTransaction()
					.show(currentFragment)
					.commit();
			} else {
				getSupportFragmentManager().beginTransaction()
					.hide(currentFragment)
					.commit();
			}
		}
	}
	
	private void initPreferences() {
		AndorsTrailApplication app = AndorsTrailApplication.getApplicationFromActivity(this);
		AndorsTrailPreferences preferences = app.getPreferences();
		preferences.read(this);
		ThemeHelper.changeTheme(preferences.selectedTheme);
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			((AnimationDrawable)((ImageView)findViewById(R.id.title_logo)).getDrawable()).start();
			ImageView iv = (ImageView) findViewById(R.id.ts_foreground);
			int ivWidth = iv.getWidth();
			int drawableWidth = iv.getDrawable().getIntrinsicWidth();
			float ratio = ((float)ivWidth) / ((float)drawableWidth);
			
			if (clouds_back != null) {
				clouds_back.setScalingRatio(ratio);
			}
			if (clouds_mid != null) {
				clouds_mid.setScalingRatio(ratio);
			}
			if (clouds_front != null) {
				clouds_front.setScalingRatio(ratio);
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		final ImageView iv = (ImageView) findViewById(R.id.ts_foreground);
		iv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				float[] point = new float[]{0f,0.25f * iv.getDrawable().getIntrinsicHeight()};
				iv.getImageMatrix().mapPoints(point);
				int imgY = (int) (iv.getTop() + point[1]);
				int screenHeight = getResources().getDisplayMetrics().heightPixels;

				if (clouds_back != null) {
					clouds_back.setYMax(imgY);
				}
				if (clouds_mid != null) {
					clouds_mid.setYMax(imgY);
				}
				if (clouds_front != null) {
					clouds_front.setYMax(imgY);
				}
				iv.getViewTreeObserver().removeOnPreDrawListener(this);
				return true;
			}
		});
		
		
		if (clouds_back != null)clouds_back.resumeAnimation();
		if (clouds_mid != null)clouds_mid.resumeAnimation();
		if (clouds_front != null)clouds_front.resumeAnimation();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (clouds_back != null)clouds_back.pauseAnimation();
		if (clouds_mid != null)clouds_mid.pauseAnimation();
		if (clouds_front != null)clouds_front.pauseAnimation();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
				backPressed();
				return true;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void backPressed() {
		if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			getSupportFragmentManager().popBackStack();
			currentFragment = getSupportFragmentManager().findFragmentById(R.id.startscreen_fragment_container);
		}
	}
	
	
	
	public void onNewGameRequested() {
		if (findViewById(R.id.startscreen_fragment_container) != null) {
			StartScreenActivity_NewGame newGameFragment = new StartScreenActivity_NewGame();
			
			getSupportFragmentManager().beginTransaction()
				.replace(R.id.startscreen_fragment_container, newGameFragment)
				.addToBackStack(null)
				.commit();

			currentFragment = newGameFragment;
			
		}
	}
	
	@Override
	public void onGameCreationCancelled() {
		backPressed();
	}

	@Override
	public void onBackStackChanged() {
		currentFragment = getSupportFragmentManager().findFragmentById(R.id.startscreen_fragment_container);
	}
	
}
