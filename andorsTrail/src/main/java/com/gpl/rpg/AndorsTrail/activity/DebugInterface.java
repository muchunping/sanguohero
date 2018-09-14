package com.gpl.rpg.AndorsTrail.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gpl.rpg.AndorsTrail.AndorsTrailApplication;
import com.gpl.rpg.AndorsTrail.R;
import com.gpl.rpg.AndorsTrail.context.ControllerContext;
import com.gpl.rpg.AndorsTrail.context.WorldContext;
import com.gpl.rpg.AndorsTrail.controller.Constants;
import com.gpl.rpg.AndorsTrail.model.item.ItemType;
import com.gpl.rpg.AndorsTrail.model.map.MapObject;
import com.gpl.rpg.AndorsTrail.model.map.PredefinedMap;
import com.gpl.rpg.AndorsTrail.view.MainView;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public final class DebugInterface {
	private final ControllerContext controllerContext;
	private final MainActivity mainActivity;
	private final Resources res;
	private final WorldContext world;
	
	private DebugButton[] buttons;
	private List<DebugButton> tpButtons = new ArrayList<DebugButton>();

	public DebugInterface(ControllerContext controllers, WorldContext world, MainActivity mainActivity) {
		this.controllerContext = controllers;
		this.world = world;
		this.res = mainActivity.getResources();
		this.mainActivity = mainActivity;
	}

	public void addDebugButtons() {
		if (!AndorsTrailApplication.DEVELOPMENT_DEBUGBUTTONS) return;

		List<DebugButton> buttonList = new ArrayList<DebugButton>();
		buttonList.addAll(Arrays.asList(new DebugButton[] {
			new DebugButton("dbg", new OnClickListener() {
				boolean hidden = false;
					@Override
					public void onClick(View arg0) {
						hidden = !hidden;
						for (int i = 1; i < buttons.length; i++) {
							buttons[i].b.setVisibility(hidden ? View.GONE : View.VISIBLE);
						}
						for (DebugButton b : tpButtons) {
							b.b.setVisibility(View.GONE);
						}
					}
			})
			,new DebugButton("tp", new OnClickListener() {
				public void onClick(View arg0) {
					for (int i = 0; i < buttons.length; i++) {
						buttons[i].b.setVisibility(View.GONE);
					}
					for (DebugButton tpButton : tpButtons) {
						tpButton.b.setVisibility(View.VISIBLE);
					}
				}
			})
			,new DebugButton("dmg", new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					world.model.player.damagePotential.set(500, 500);
					world.model.player.attackChance = 500;
					world.model.player.attackCost = 1;
					showToast(mainActivity, "DEBUG: damagePotential=500, chance=500%, cost=1", Toast.LENGTH_SHORT);
				}
			})
			/*,new DebugButton("dmg=1", new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					world.model.player.damagePotential.set(1, 1);
					showToast(mainActivity, "DEBUG: damagePotential=1", Toast.LENGTH_SHORT);
				}
			})*/
			,new DebugButton("itm", new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					for (ItemType item : world.itemTypes.UNITTEST_getAllItemTypes().values()) {
						world.model.player.inventory.addItem(item, 10);
					}
					world.model.player.inventory.gold += 50000;
					showToast(mainActivity, "DEBUG: added items", Toast.LENGTH_SHORT);
				}
			})
			,new DebugButton("xp", new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					controllerContext.actorStatsController.addExperience(10000);
					showToast(mainActivity, "DEBUG: given 10000 exp", Toast.LENGTH_SHORT);
				}
			})
			,new DebugButton("rst", new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					for(PredefinedMap map : world.maps.getAllMaps()) {
						map.resetTemporaryData();
					}
					showToast(mainActivity, "DEBUG: maps respawned", Toast.LENGTH_SHORT);
				}
			})
			,new DebugButton("hp", new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					world.model.player.baseTraits.maxHP = 500;
					world.model.player.health.max = world.model.player.baseTraits.maxHP;
					controllerContext.actorStatsController.setActorMaxHealth(world.model.player);
					world.model.player.conditions.clear();
					showToast(mainActivity, "DEBUG: hp set to max", Toast.LENGTH_SHORT);
				}
			})
			,new DebugButton("skl", new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					world.model.player.availableSkillIncreases += 10;
					showToast(mainActivity, "DEBUG: 10 skill points", Toast.LENGTH_SHORT);
				}
			})
			,new DebugButton("spd", new OnClickListener() {
				boolean fast = Constants.MINIMUM_INPUT_INTERVAL == Constants.MINIMUM_INPUT_INTERVAL_FAST;
				@Override
				public void onClick(View arg0) {
					fast = !fast;
					if (fast) {
						Constants.MINIMUM_INPUT_INTERVAL = Constants.MINIMUM_INPUT_INTERVAL_FAST;
					} else {
						Constants.MINIMUM_INPUT_INTERVAL = Constants.MINIMUM_INPUT_INTERVAL_STD;
					}
					MainView.SCROLL_DURATION = Constants.MINIMUM_INPUT_INTERVAL;
					AndorsTrailApplication.getApplicationFromActivity(mainActivity).getControllerContext().movementController.resetMovementHandler();
				}
			})
		}));
		
		tpButtons.addAll(Arrays.asList(new DebugButton[] {
				new DebugButton("tp", new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						for (int i = 0; i < buttons.length; i++) {
							buttons[i].b.setVisibility(View.VISIBLE);
						}
						for (DebugButton tpButton : tpButtons) {
							tpButton.b.setVisibility(View.GONE);
						}
					}
				})
				,new DebugButton("cg", new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						controllerContext.movementController.placePlayerAsyncAt(MapObject.MapObjectType.newmap, "crossglen", "hall", 0, 0);
					}
				})
				,new DebugButton("vg", new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						controllerContext.movementController.placePlayerAsyncAt(MapObject.MapObjectType.newmap, "vilegard_s", "tavern", 0, 0);
					}
				})
				,new DebugButton("cr", new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						controllerContext.movementController.placePlayerAsyncAt(MapObject.MapObjectType.newmap, "houseatcrossroads4", "down", 0, 0);
					}
				})
				,new DebugButton("lf", new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						controllerContext.movementController.placePlayerAsyncAt(MapObject.MapObjectType.newmap, "loneford9", "south", 0, 0);
					}
				})
				,new DebugButton("fh", new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						controllerContext.movementController.placePlayerAsyncAt(MapObject.MapObjectType.newmap, "fallhaven_ne", "clothes", 0, 0);
					}
				})
				,new DebugButton("prim", new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						controllerContext.movementController.placePlayerAsyncAt(MapObject.MapObjectType.newmap, "blackwater_mountain29", "south", 0, 0);
					}
				})
				,new DebugButton("bwm", new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						controllerContext.movementController.placePlayerAsyncAt(MapObject.MapObjectType.newmap, "blackwater_mountain43", "south", 0, 0);
					}
				})
				,new DebugButton("rmg", new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						controllerContext.movementController.placePlayerAsyncAt(MapObject.MapObjectType.newmap, "remgard0", "east", 0, 0);
					}
				})
				,new DebugButton("chr", new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						controllerContext.movementController.placePlayerAsyncAt(MapObject.MapObjectType.newmap, "waytolostmine2", "minerhouse4", 0, 0);
					}
				})
				,new DebugButton("ldr", new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						controllerContext.movementController.placePlayerAsyncAt(MapObject.MapObjectType.newmap, "lodarhouse0", "lodarhouse", 0, 0);
					}
				})
				,new DebugButton("sf", new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						controllerContext.movementController.placePlayerAsyncAt(MapObject.MapObjectType.newmap, "wild20", "south2", 0, 0);
					}
				})
				,new DebugButton("gm", new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						controllerContext.movementController.placePlayerAsyncAt(MapObject.MapObjectType.newmap, "guynmart_wood_1", "farmhouse", 0, 0);
					}
				})
		}));
		buttonList.addAll(tpButtons);
		
		buttons = buttonList.toArray(new DebugButton[buttonList.size()]);
		addDebugButtons(buttons);
		
		for (DebugButton b : tpButtons) {
			b.b.setVisibility(View.GONE);
		}
	}

	private void showToast(Context context, String msg, int duration) {
		Toast.makeText(context, msg, duration).show();
	}

	private static class DebugButton {
		public final String text;
		public final OnClickListener listener;
		public Button b = null;
		public DebugButton(String text, OnClickListener listener) {
			this.text = text;
			this.listener = listener;
		}
		public void makeButton(Context c, int id) {
			b = new Button(c);
			b.setText(text);
			b.setTextSize(10);//res.getDimension(R.dimen.actionbar_text));
			b.setId(id);
			b.setOnClickListener(listener);
		}
	}

	private void addDebugButton(DebugButton button, int id, RelativeLayout layout) {
		if (!AndorsTrailApplication.DEVELOPMENT_DEBUGBUTTONS) return;

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, res.getDimensionPixelSize(R.dimen.smalltext_buttonheight));
		if (id == 1)
			lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		else
			lp.addRule(RelativeLayout.RIGHT_OF, id - 1);
		lp.addRule(RelativeLayout.ABOVE, R.id.main_statusview);
		button.makeButton(mainActivity, id); 
		button.b.setLayoutParams(lp);
		layout.addView(button.b);
	}

	private void addDebugButtons(DebugButton[] buttons) {
		if (!AndorsTrailApplication.DEVELOPMENT_DEBUGBUTTONS) return;

		if (buttons == null || buttons.length <= 0) return;
		RelativeLayout layout = (RelativeLayout) mainActivity.findViewById(R.id.main_container);

		int id = 1;
		for (DebugButton b : buttons) {
			addDebugButton(b, id, layout);
			++id;
		}
	}
}
