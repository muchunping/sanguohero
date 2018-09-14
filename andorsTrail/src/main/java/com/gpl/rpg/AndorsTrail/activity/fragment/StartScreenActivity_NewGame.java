package com.gpl.rpg.AndorsTrail.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gpl.rpg.AndorsTrail.AndorsTrailApplication;
import com.gpl.rpg.AndorsTrail.R;
import com.gpl.rpg.AndorsTrail.WorldSetup;
import com.gpl.rpg.AndorsTrail.activity.LoadingActivity;
import com.gpl.rpg.AndorsTrail.resource.tiles.TileManager;

public class StartScreenActivity_NewGame extends Fragment {

	private TextView startscreen_enterheroname;
	
	private int selectedIconID = TileManager.CHAR_HERO;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		if (container != null) {
			container.removeAllViews();
		}
		
		View root = inflater.inflate(R.layout.startscreen_newgame, container, false);
		
		
		startscreen_enterheroname = (TextView) root.findViewById(R.id.startscreen_enterheroname);
		
		final RadioGroup group = (RadioGroup) root.findViewById(R.id.newgame_spritegroup);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				for (int i = 0; i < group.getChildCount(); i++) {
					ToggleButton tb = ((ToggleButton)group.getChildAt(i));
					tb.setChecked(tb.getId() == checkedId);
				}
				switch (checkedId) {
				case R.id.newgame_sprite0:
					selectedIconID = TileManager.CHAR_HERO_0;
					break;
				case R.id.newgame_sprite1:
					selectedIconID = TileManager.CHAR_HERO_1;
					break;
				case R.id.newgame_sprite2:
					selectedIconID = TileManager.CHAR_HERO_2;
					break;
				}
			}
		});
		
		OnClickListener l = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				group.check(v.getId());
			}
		};
		
		for (int i = 0; i < group.getChildCount(); i++) {
			ToggleButton tb = ((ToggleButton)group.getChildAt(i));
			tb.setOnClickListener(l);
		}
		
		Button b = (Button) root.findViewById(R.id.startscreen_newgame_start);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createNewGame();
			}
		});
		
		b = (Button) root.findViewById(R.id.startscreen_newgame_cancel);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gameCreationOver();
			}
		});
		
		return root;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (GameCreationOverListener) activity;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}
	
	
	private void continueGame(boolean createNewCharacter, int loadFromSlot, String name) {
		final WorldSetup setup = AndorsTrailApplication.getApplicationFromActivity(getActivity()).getWorldSetup();
		setup.createNewCharacter = createNewCharacter;
		setup.loadFromSlot = loadFromSlot;
		setup.newHeroName = name;
		setup.newHeroIcon = selectedIconID;
		gameCreationOver();
		startActivity(new Intent(getActivity(), LoadingActivity.class));
	}

	private void createNewGame() {
		String name = startscreen_enterheroname.getText().toString().trim();
		if (name == null || name.length() <= 0) {
			Toast.makeText(getActivity(), R.string.startscreen_enterheroname, Toast.LENGTH_SHORT).show();
			return;
		}
		continueGame(true, 0, name);
	}
	
	public interface GameCreationOverListener {
		public void onGameCreationCancelled();
	}
	
	private GameCreationOverListener listener = null;
	
	private void gameCreationOver() {
		if (listener != null) {
			listener.onGameCreationCancelled();
		}
	}
	
	
}
