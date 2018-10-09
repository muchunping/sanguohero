package cn.igame.sanguoheros.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.model.FightProperty;
import cn.igame.sanguoheros.model.LayoutRule;
import cn.igame.sanguoheros.model.Ogre;
import cn.igame.sanguoheros.model.TurnActor;

public class BattleActivity extends AppCompatActivity {
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private ImageView imageView7;
    private ImageView imageView8;
    private ImageView imageView9;
    private ImageView imageView10;
    private ImageView imageView11;
    private ImageView imageView12;
    private ImageView imageView13;
    private ImageView imageView14;
    private ImageView imageView15;
    private ImageView imageView16;
    private ImageView imageView17;
    private ImageView imageView18;
    private TextView textView1;
    private TextView textView2;

    private Handler handler = new Handler();
    private TurnActor[] actors;
    private TurnActor actActor;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int leave = 10000;
            for (TurnActor actor : actors) {
                leave = Math.min(leave, actor.getLeaveTime());
            }
            for (TurnActor actor : actors) {
                int time = actor.leave(leave);
                if (time == 0) {
                    actActor = actor;
                }
            }
            actActor.action();
            handler.postDelayed(this, 1000);
        }
    };

    private Runnable action = new Runnable() {
        @Override
        public void run() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Drawable drawable = getResources().getDrawable(android.R.drawable.ic_menu_mylocation);
        Drawable tintDrawable = tintDrawable(drawable, Color.RED, 255);
        setContentView(R.layout.activity_main);
        imageView1 = findViewById(R.id.necklaceView);
        imageView1.setImageDrawable(tintDrawable);
        imageView2 = findViewById(R.id.clothesView);
        imageView2.setImageDrawable(tintDrawable);
        imageView3 = findViewById(R.id.trousersView);
        imageView3.setImageDrawable(tintDrawable);
        imageView4 = findViewById(R.id.shoesView);
        imageView5 = findViewById(R.id.leftBraceletView);
        imageView6 = findViewById(R.id.rightBraceletView);
        imageView7 = findViewById(R.id.leftRingView);
        imageView8 = findViewById(R.id.rightRingView);
        imageView9 = findViewById(R.id.imageView9);
        imageView10 = findViewById(R.id.imageView10);
        imageView11 = findViewById(R.id.imageView11);
        imageView12 = findViewById(R.id.imageView12);
        imageView13 = findViewById(R.id.imageView13);
        imageView14 = findViewById(R.id.imageView14);
        imageView15 = findViewById(R.id.imageView15);
        imageView16 = findViewById(R.id.imageView16);
        imageView17 = findViewById(R.id.imageView17);
        imageView18 = findViewById(R.id.imageView18);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        LayoutRule rule1 = LayoutRule.LONG_FEI;
        LayoutRule rule2 = LayoutRule.HU_XIAO;
        textView1.setText(rule1.name);
        textView2.setText(rule2.name);

        try {
            JSONObject jo = new JSONObject();
            jo.put("attack_point", 100);
            jo.put("defense_oint", 100);
            jo.put("health_point", 100);
            jo.put("mana_point", 100);
            jo.put("speed_point", 100);
            actors = new TurnActor[]{
                    new Ogre("A", 10, Ogre.TYPE_NORMAL, FightProperty.readFightPropertyFromJson(jo.put("speed_point", 500))),
                    new Ogre("B", 10, Ogre.TYPE_NORMAL, FightProperty.readFightPropertyFromJson(jo.put("speed_point", 100))),
                    new Ogre("C", 10, Ogre.TYPE_NORMAL, FightProperty.readFightPropertyFromJson(jo.put("speed_point", 240))),
                    new Ogre("D", 10, Ogre.TYPE_NORMAL, FightProperty.readFightPropertyFromJson(jo.put("speed_point", 280))),
                    new Ogre("E", 10, Ogre.TYPE_NORMAL, FightProperty.readFightPropertyFromJson(jo.put("speed_point", 330))),
                    new Ogre("F", 10, Ogre.TYPE_NORMAL, FightProperty.readFightPropertyFromJson(jo.put("speed_point", 460))),
                    new Ogre("G", 10, Ogre.TYPE_NORMAL, FightProperty.readFightPropertyFromJson(jo.put("speed_point", 360))),
                    new Ogre("H", 10, Ogre.TYPE_NORMAL, FightProperty.readFightPropertyFromJson(jo.put("speed_point", 120))),
                    new Ogre("I", 10, Ogre.TYPE_NORMAL, FightProperty.readFightPropertyFromJson(jo.put("speed_point", 150))),
                    new Ogre("J", 10, Ogre.TYPE_NORMAL, FightProperty.readFightPropertyFromJson(jo.put("speed_point", 420)))
            };
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int j = 0;
        for (int i : rule1.layout) {
            ImageView imageView = getImageViewFormAbove(i);
            imageView.setVisibility(View.VISIBLE);
//            actors[j].setImageView(imageView);
            j++;
        }

        for (int i : rule2.layout) {
            ImageView imageView = getImageViewFormBelow(i);
            imageView.setVisibility(View.VISIBLE);
//            actors[j].setImageView(imageView);
            j++;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private ImageView getImageViewFormAbove(int i) {
        ImageView imageView;
        switch (i) {
            case 1:
                imageView = imageView9;
                break;
            case 2:
                imageView = imageView8;
                break;
            case 3:
                imageView = imageView7;
                break;
            case 4:
                imageView = imageView6;
                break;
            case 5:
                imageView = imageView5;
                break;
            case 6:
                imageView = imageView4;
                break;
            case 7:
                imageView = imageView3;
                break;
            case 8:
                imageView = imageView2;
                break;
            case 9:
                imageView = imageView1;
                break;
            default:
                throw new IllegalStateException();
        }
        return imageView;
    }

    private ImageView getImageViewFormBelow(int i) {
        ImageView imageView;
        switch (i) {
            case 1:
                imageView = imageView10;
                break;
            case 2:
                imageView = imageView11;
                break;
            case 3:
                imageView = imageView12;
                break;
            case 4:
                imageView = imageView13;
                break;
            case 5:
                imageView = imageView14;
                break;
            case 6:
                imageView = imageView15;
                break;
            case 7:
                imageView = imageView16;
                break;
            case 8:
                imageView = imageView17;
                break;
            case 9:
                imageView = imageView18;
                break;
            default:
                throw new IllegalStateException();
        }
        return imageView;
    }

    public static Drawable tintDrawable(Drawable drawable, int color, int alpha) {
        ColorStateList colors = ColorStateList.valueOf(color);
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        wrappedDrawable.setAlpha(alpha);
        return wrappedDrawable;
    }
}
