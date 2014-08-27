package com.sg.sghero.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.sg.sanguohero.R;
import com.sg.sghero.app.SgApplication;
import com.sg.sghero.app.WorldContext;
import com.sg.sghero.db.Actor;

public class BattleActivity extends Activity{
	private WorldContext world;
	private Actor[] attackers;
	private Actor[] defenders;
	private ImageView attacker1;
	private ImageView attacker2;
	private ImageView attacker3;
	private ImageView attacker4;
	private ImageView attacker5;
	private ImageView attacker6;
	private ImageView attacker7;
	private ImageView attacker8;
	private ImageView attacker9;
	private ImageView defender1;
	private ImageView defender2;
	private ImageView defender3;
	private ImageView defender4;
	private ImageView defender5;
	private ImageView defender6;
	private ImageView defender7;
	private ImageView defender8;
	private ImageView defender9;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SgApplication app = SgApplication.getApplication(this);
		world = app.world;
		attackers = world.getAttackers();
		defenders = world.getDefenders();
		assert(attackers != null && attackers.length > 0);
		assert(defenders != null && defenders.length > 0);
		
		setContentView(R.layout.activity_battle);
		attacker1 = (ImageView) findViewById(R.id.imageView10);
		attacker2 = (ImageView) findViewById(R.id.imageView11);
		attacker3 = (ImageView) findViewById(R.id.imageView12);
		attacker4 = (ImageView) findViewById(R.id.imageView13);
		attacker5 = (ImageView) findViewById(R.id.imageView14);
		attacker6 = (ImageView) findViewById(R.id.imageView15);
		attacker7 = (ImageView) findViewById(R.id.imageView16);
		attacker8 = (ImageView) findViewById(R.id.imageView17);
		attacker9 = (ImageView) findViewById(R.id.imageView18);
		defender1 = (ImageView) findViewById(R.id.imageView7);
		defender2 = (ImageView) findViewById(R.id.imageView8);
		defender3 = (ImageView) findViewById(R.id.imageView9);
		defender4 = (ImageView) findViewById(R.id.imageView4);
		defender5 = (ImageView) findViewById(R.id.imageView5);
		defender6 = (ImageView) findViewById(R.id.imageView6);
		defender7 = (ImageView) findViewById(R.id.imageView1);
		defender8 = (ImageView) findViewById(R.id.imageView2);
		defender9 = (ImageView) findViewById(R.id.imageView3);
	}
}
