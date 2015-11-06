package com.ilife.sanguohero.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.ilife.sanguohero.R;
import com.ilife.sanguohero.app.SgApplication;
import com.ilife.sanguohero.app.WorldContext;
import com.ilife.sanguohero.db.Actor;
import com.ilife.sanguohero.db.Props;
import com.ilife.sanguohero.resoure.IconsCache;
import com.ilife.sanguohero.util.ILog;

public class BattleActivity extends Activity {
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

        prepare();
    }

    public void prepare() {
        attacker1.setImageResource(R.drawable.photo_guanyu);
        attacker2.setImageResource(R.drawable.photo_guanyu);
        attacker3.setImageResource(R.drawable.photo_guanyu);
        attacker4.setImageResource(R.drawable.photo_guanyu);
        attacker5.setImageResource(R.drawable.photo_guanyu);

        defender1.setImageResource(R.drawable.photo_guanyu);
        defender2.setImageResource(R.drawable.photo_guanyu);
        defender3.setImageResource(R.drawable.photo_guanyu);
        String photoString = defenders[0].getPhoto();
        ILog.i(" " + photoString);
        String[] split = photoString.split("\\|");
        Bitmap icon = IconsCache.getInstance().getIconByFileAndLocalId(getResources(),
                Props.PHOTO_MONSTER_PREFIX2 + split[0],
                Integer.parseInt(split[1]));
        if (icon != null)
            defender3.setImageBitmap(icon);
    }

    public void beginFight() {

    }
}
