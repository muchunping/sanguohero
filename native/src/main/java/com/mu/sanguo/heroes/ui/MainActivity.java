package com.mu.sanguo.heroes.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.igame.sanguoheros.R;
import com.mu.sanguo.heroes.app.SgApplication;
import com.mu.sanguo.heroes.app.WorldContext;
import com.mu.sanguo.heroes.model.Equipment;
import com.mu.sanguo.heroes.model.Goods;
import com.mu.sanguo.heroes.model.Ogre;
import com.mu.sanguo.heroes.model.Player;
import com.mu.sanguo.heroes.model.Scene;
import com.mu.sanguo.heroes.model.SgScene;
import com.mu.sanguo.heroes.model.SystemActor;
import com.mu.sanguo.heroes.ui.fragment.BattleFragment;
import com.mu.sanguo.heroes.ui.fragment.EquipmentFragment;
import com.mu.sanguo.heroes.ui.fragment.InventoryFragment;
import com.mu.sanguo.heroes.ui.fragment.MonsterFragment;
import com.mu.sanguo.heroes.ui.fragment.WorldMapFragment;
import com.mu.sanguo.heroes.util.Logger;
import com.mu.sanguo.heroes.util.ToastUtil;
import com.mu.sanguo.heroes.widget.RadioGroupPlus;

/**
 * 主界面
 * Created by Administrator on 2015/11/9.
 */
public class MainActivity extends AppCompatActivity {
    private FrameLayout floatLayout;
    private Fragment lastFragment;
    private TextView logView;
    private TextView playerNameView, playerLevelView;
    private TextView sceneNameView;
    private RecyclerView.Adapter<ViewHolder> adapter;
    private WorldContext world;

    private LinearLayout findEnemyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        world = ((SgApplication) getApplication()).getWorldContext();
        Scene scene = world.getScene();

        floatLayout = (FrameLayout) findViewById(R.id.floatLayout);
        playerNameView = (TextView) findViewById(R.id.playerNameView);
        playerLevelView = (TextView) findViewById(R.id.playerLevelView);
        fillPlayerInfoLayout();
        sceneNameView = (TextView) findViewById(R.id.sceneNameView);
        sceneNameView.setText(scene.getName());
        floatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastFragment != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(lastFragment);
                    ft.commit();
                }
                v.setVisibility(View.GONE);
            }
        });

        findEnemyView = findViewById(R.id.findEnemyLayout);
        findEnemyView.setVisibility(scene.getType() == SgScene.TYPE_WILD ? View.VISIBLE : View.GONE);
        findEnemyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ogre ogre = new Ogre("青蛇", 10, Ogre.TYPE_NORMAL);
                BattleFragment fragment = new BattleFragment();
                fragment.setAttacker(world.getPlayer());
                fragment.setDefender(ogre);
                fragment.show(getFragmentManager(), "battle");
            }
        });
        RecyclerView systemActorLayout = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        systemActorLayout.setLayoutManager(layoutManager);
        systemActorLayout.setHasFixedSize(true);
        adapter = new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_system_actor, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                List<Integer> systemActorList = world.getScene().getSystemActorList();
                final SystemActor systemActor = world.findSystemActorById(systemActorList.get(position));
                if (systemActor != null) {
                    holder.nameView.setText(systemActor.getName());
                    holder.actionView.setText(systemActor.getActions().get(0));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            doAction(systemActor, systemActor.getActions().get(0));
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                List<Integer> systemActorList = world.getScene().getSystemActorList();
                return systemActorList.size();
            }
        };
        systemActorLayout.setAdapter(adapter);
        systemActorLayout.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int childAdapterPosition = parent.getChildAdapterPosition(view);
                if (childAdapterPosition != parent.getAdapter().getItemCount() - 1)
                    outRect.bottom += 10;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (floatLayout.isShown()) {
            floatLayout.setVisibility(View.GONE);
            if (lastFragment != null) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(lastFragment);
                ft.commit();
            }
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        world.savePlayer();
    }

    private void fillPlayerInfoLayout() {
        Player player = world.getPlayer();
        playerNameView.setText(player.getName());
        playerLevelView.setText(String.valueOf(player.getLevel()));
    }

    public void showWorldMap(View view) {
        Scene scene = world.getScene();
        Logger.dL(scene.toString());
        WorldMapFragment fragment = new WorldMapFragment();
        fragment.show(getFragmentManager(), "worldMap");
    }

    private void changeScene(Scene newScene) {
        world.setScene(newScene);
        sceneNameView.setText(newScene.getName());
        findEnemyView.setVisibility(newScene.getType() == SgScene.TYPE_WILD ? View.VISIBLE : View.GONE);
        adapter.notifyDataSetChanged();
    }

    private void doAction(SystemActor actor, String action) {
        Fragment fragment = null;
        switch (action) {
            case "交易":
                if (actor.getId() == SystemActor.SHOP_ID_WEAPON) {
                    InventoryFragment inventoryFragment = new InventoryFragment();
                    ArrayList<Goods> goodsList = new ArrayList<>();
                    for (int i = 0; i < 42; i++) {
                        Equipment equipment = new Equipment(1, getString(R.string.weapon_qlyyd), R.drawable.pic_2300070_l,
                                getString(R.string.weapon_qlyyd_description_examples), 70, 5, 80, 0, 0, 0, 0);
                        goodsList.add(equipment);
                    }
                    inventoryFragment.setGoodsList(goodsList);
                    fragment = inventoryFragment;
                }
                break;
        }
        if (fragment != null) {
            lastFragment = fragment;
            floatLayout.setVisibility(View.VISIBLE);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.contentPanel, fragment);
            ft.commit();
        }
    }

    public void showGoodsDetail(Goods goods) {
        if (goods instanceof Equipment) {
            EquipmentFragment fragment = new EquipmentFragment();
            fragment.setEquipment((Equipment) goods);
            fragment.show(getFragmentManager(), "equipment");
        }
    }

    public void onClickSearch(View view) {
        new MonsterFragment().show(getFragmentManager(), "monster");
    }

    public void onClickOutlet(View view) {
        List<Scene> neighborList = world.getScene().getNeighborList(world.getSceneList());
        @SuppressLint("InflateParams") final RadioGroupPlus radioGroup = (RadioGroupPlus) LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_select_outlet, null);
        radioGroup.setItemList(neighborList);
        Dialog dialog = new AlertDialog.Builder(view.getContext())
                .setTitle("出口")
                .setMessage("请选择进入的场景")
                .setPositiveButton(android.R.string.cancel, null)
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Object checkedTarget = radioGroup.getCheckedTarget();
                        if (checkedTarget == null) {
                            ToastUtil.showToast("请选择一个场景");
                            return;
                        }
                        Scene scene = (Scene) checkedTarget;
                        changeScene(scene);
                    }
                })
                .setView(radioGroup)
                .create();
        dialog.show();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameView;
        public TextView actionView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            nameView = (TextView) view.findViewById(R.id.nameView);
            actionView = (TextView) view.findViewById(R.id.actionView);
        }
    }
}