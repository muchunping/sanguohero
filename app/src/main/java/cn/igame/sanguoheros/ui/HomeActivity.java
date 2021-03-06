package cn.igame.sanguoheros.ui;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.app.SgApplication;
import cn.igame.sanguoheros.app.WorldContext;
import cn.igame.sanguoheros.model.Equipment;
import cn.igame.sanguoheros.model.Goods;
import cn.igame.sanguoheros.model.Scene;
import cn.igame.sanguoheros.model.SystemActor;
import cn.igame.sanguoheros.ui.fragment.EquipmentFragment;
import cn.igame.sanguoheros.ui.fragment.GoodsDetailFragment;
import cn.igame.sanguoheros.ui.fragment.InventoryFragment;
import cn.igame.sanguoheros.ui.fragment.QuestFragment;
import cn.igame.sanguoheros.ui.fragment.SkillFragment;

public class HomeActivity extends AppCompatActivity {
    private WorldContext world;
    private RecyclerView.Adapter<ViewHolder> adapter;

    private TextView logView;
    private Fragment fragment;
    private View floatView;
    private Fragment fragment1;
    private Fragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        world = ((SgApplication) getApplication()).getWorldContext();

        setContentView(R.layout.activity_home);
        logView = findViewById(R.id.layout_logcat);
        logView.setText("欢迎你来到了《三国英雄》的世界。\n");

        Scene scene = world.getScene();
        TextView sceneNameView = findViewById(R.id.tv_scene);
        sceneNameView.setText(scene.getName());
        logView.append("你进入了「" + scene.getName() + "」。\n");

        RecyclerView recyclerView = findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerView.Adapter<ViewHolder>() {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_system_actor, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                List<Integer> systemActorList = world.getScene().getSystemActorList();
                final SystemActor systemActor = world.findSystemActorById(systemActorList.get(position));
                if (systemActor != null) {
                    holder.nameView.setText(systemActor.getName());
                    holder.actionView.setText(systemActor.getActions().get(0));
                    int picRid = systemActor.getPic();
                    if (picRid != -1) {
                        holder.imageView.setImageResource(picRid);
                    }
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
                return world.getScene().getSystemActorList().size();
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int childAdapterPosition = parent.getChildAdapterPosition(view);
                if (childAdapterPosition != parent.getAdapter().getItemCount() - 1)
                    outRect.bottom += 10;
            }
        });
    }

    public void openLuggage(View view) {
        handleFloatWindow(view);

        if (fragment instanceof InventoryFragment) {
            fragment = null;
            return;
        }
        InventoryFragment inventoryFragment = new InventoryFragment();
        ArrayList<Goods> goodsList = new ArrayList<>();
        for (int i = 0; i < 142; i++) {
            Equipment equipment = new Equipment(0, 1, getString(R.string.weapon_qlyyd), R.drawable.pic_2300070_l,
                    getString(R.string.weapon_qlyyd_description_examples), 70, 5, 80, 0, 0, 0, 0);
            goodsList.add(equipment);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            inventoryFragment.setEnterTransition(new Slide(Gravity.TOP));
            inventoryFragment.setExitTransition(new Slide(Gravity.TOP));
        }
        inventoryFragment.setGoodsList(goodsList);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fl_float, inventoryFragment);
        ft.commit();

        fragment = inventoryFragment;
    }

    public void openQuest(View view) {
        handleFloatWindow(view);
        fragment = new QuestFragment();
    }

    public void openSkill(View view) {
        handleFloatWindow(view);
        fragment = new SkillFragment();
    }

    public void onClickSearch(View view) {

    }

    public void openEquipment(View view) {
        handleFloatWindow(view);

        if (fragment instanceof EquipmentFragment){
            fragment = null;
            return;
        }
        EquipmentFragment equipmentFragment = new EquipmentFragment();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            equipmentFragment.setEnterTransition(new Slide(Gravity.TOP));
            equipmentFragment.setExitTransition(new Slide(Gravity.TOP));
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fl_float, equipmentFragment);
        ft.commit();

        fragment = equipmentFragment;
    }

    public void showGoodsDetail(Goods goods){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        GoodsDetailFragment detailFragment = (GoodsDetailFragment) getFragmentManager().findFragmentByTag("fragment_detail");
        if ( detailFragment== null){
            detailFragment = new GoodsDetailFragment();
            ft.add(detailFragment, "fragment_detail");
            ft.commit();
        }
        detailFragment.setGoods(goods);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            detailFragment.setEnterTransition(new Slide(Gravity.TOP));
            detailFragment.setExitTransition(new Slide(Gravity.TOP));
        }
        DialogFragment fragment = new DialogFragment();
        ft = getFragmentManager().beginTransaction();
        fragment.show(ft, "fragment_detail");
    }

    private void handleFloatWindow(View currentView){
        if (floatView != null) {
            floatView.setSelected(false);
        }
        if (floatView == currentView) {
            floatView = null;
        } else {
            floatView = currentView;
            currentView.setSelected(true);
        }
        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(fragment);
            ft.commit();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameView;
        public TextView actionView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            nameView = view.findViewById(R.id.nameView);
            actionView = view.findViewById(R.id.actionView);
        }
    }
}
