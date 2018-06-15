package com.mu.sanguo.heroes;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mu.sanguo.heroes.context.WorldContext;
import com.mu.sanguo.heroes.model.Actor;
import com.mu.sanguo.heroes.ui.MonsterInfoFragment;
import com.mu.sanguo.heroes.ui.PlayInfoFragment;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, WorldSetup.OnResourcesLoadedListener {
    private static final String TAG = "SgLog";
    private final static String fragment_name_1 = "fragment1";
    private final static String fragment_name_2 = "fragment2";

    private WorldContext world;

    private Fragment showingFragment;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowParameters(this);
        world = SgApplication.getApplication().getWorld();

        setContentView(R.layout.activity_main);
        findViewById(R.id.tab1).setOnClickListener(this);
        findViewById(R.id.tab2).setOnClickListener(this);
        findViewById(R.id.tab3).setOnClickListener(this);
        findViewById(R.id.tab4).setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_system_actor, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                List<Integer> systemActorList = world.currentScene.getSystemActorList();
                final Actor systemActor = world.findSystemActorById(systemActorList.get(position));
                if (systemActor != null) {
                    holder.nameView.setText(systemActor.getName());
                    holder.actionView.setText(systemActor.getActions().isEmpty() ? "" : systemActor.getActions().get(0));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            doAction(systemActor, systemActor.getActions().get(0));
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return world.currentScene == null ? 0 : world.currentScene.getSystemActorList().size();
            }
        });

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int childAdapterPosition = parent.getChildAdapterPosition(view);
                if (childAdapterPosition != parent.getAdapter().getItemCount() - 1)
                    outRect.bottom += 10;
            }
        });

        SgApplication.getApplication().getWorldSetup().setOnResourcesLoadedListener(this);
    }

    private void setWindowParameters(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (SgPreferences.fullscreen) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            activity.getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setLocale(activity);
    }

    private void setLocale(Activity context) {
        Resources res = context.getResources();
        Configuration conf = res.getConfiguration();
        final Locale targetLocale = SgPreferences.useLocalizedResources ? Locale.getDefault() : Locale.US;
        if (targetLocale.equals(conf.locale)) return;

        conf.locale = targetLocale;
        res.updateConfiguration(conf, res.getDisplayMetrics());
        this.getResources().updateConfiguration(conf, res.getDisplayMetrics());
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        if (showingFragment != null) {
            fragmentManager.beginTransaction().hide(showingFragment).commit();
        }

        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.tab1:
                fragment = fragmentManager.findFragmentByTag(fragment_name_1);
                if (fragment == null) {
                    fragment = new PlayInfoFragment();
                    fragmentManager.beginTransaction().add(R.id.frameLayout, fragment, fragment_name_1).commit();
                }
                break;
            case R.id.tab2:
                fragment = fragmentManager.findFragmentByTag(fragment_name_2);
                if (fragment == null) {
                    fragment = new MonsterInfoFragment();
                    fragmentManager.beginTransaction().add(R.id.frameLayout, fragment, fragment_name_2).commit();
                }
                break;
            case R.id.tab3:
                break;
            case R.id.tab4:
                break;
        }
        if (fragment != null) {
            if (fragment.isHidden()) {
                fragmentManager.beginTransaction().show(fragment).commit();
            }
        }
        showingFragment = fragment;
    }

    @Override
    public void onResourcesLoaded() {
        Log.d(TAG, "onResourcesLoaded: ");
        world.currentScene = world.sceneList.valueAt(0);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView actionView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameView = itemView.findViewById(R.id.nameView);
            actionView = itemView.findViewById(R.id.actionView);
        }
    }
}
