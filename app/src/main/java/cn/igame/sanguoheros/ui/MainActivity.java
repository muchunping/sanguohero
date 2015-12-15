package cn.igame.sanguoheros.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;

import java.util.List;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.app.SgApplication;
import cn.igame.sanguoheros.app.WorldContext;
import cn.igame.sanguoheros.model.Player;
import cn.igame.sanguoheros.model.Scene;
import cn.igame.sanguoheros.model.SystemActor;
import cn.igame.sanguoheros.util.Logger;
import cn.igame.sanguoheros.util.ToastUtil;
import cn.igame.sanguoheros.widget.RadioGroupPlus;

/**
 * 主界面
 * Created by Administrator on 2015/11/9.
 */
public class MainActivity extends AppCompatActivity {
    private FrameLayout floatLayout;
    private TextView logView;
    private TextView playerNameView, playerLevelView;
    private TextView sceneNameView;
    private RecyclerView.Adapter<ViewHolder> adapter;
    private WorldContext world;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        world = ((SgApplication)getApplication()).getWorldContext();
        Scene scene = world.getScene();

        playerNameView = (TextView) findViewById(R.id.playerNameView);
        playerLevelView = (TextView) findViewById(R.id.playerLevelView);
        fillPlayerInfoLayout();
        sceneNameView = (TextView) findViewById(R.id.sceneNameView);
        sceneNameView.setText(scene.getName());

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
                SystemActor systemActor = world.findSystemActorById(systemActorList.get(position));
                if (systemActor != null) {
                    holder.nameView.setText(systemActor.getName());
                    holder.actionView.setText(systemActor.getActions().get(0));
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
        Intent intent = new Intent(this, WorldMapActivity.class);
        startActivity(intent);
    }

    private void changeScene(Scene newScene){
        world.setScene(newScene);
        sceneNameView.setText(newScene.getName());
        adapter.notifyDataSetChanged();
    }

    public void onClickSearch(View view) {
    }

    public void onClickOutlet(View view) {
        List<Scene> neighborList = world.getScene().getNeighborList(world.getSceneList());
//        List<String> list = new ArrayList<>(neighborList.size());
//        for (Scene scene : neighborList)
//            list.add(scene.getName());
//        list.add("成都");
//        list.add("洛阳");
//        list.add("开封");
        final RadioGroupPlus radioGroup = (RadioGroupPlus) LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_select_outlet, null);
        radioGroup.setItemList(neighborList);
        Dialog dialog = new AlertDialog.Builder(view.getContext())
                .setTitle("出口")
                .setMessage("请选择进入的场景")
                .setPositiveButton(android.R.string.cancel, null)
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Object checkedTarget = radioGroup.getCheckedTarget();
                        if(checkedTarget == null){
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
