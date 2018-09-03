package cn.igame.sanguoheros.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.app.SgApplication;
import cn.igame.sanguoheros.app.WorldContext;
import cn.igame.sanguoheros.model.Scene;
import cn.igame.sanguoheros.model.SystemActor;

public class HomeActivity extends AppCompatActivity {
    private WorldContext world;
    private RecyclerView.Adapter<ViewHolder> adapter;

    private TextView logView;

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
    }

    public void openQuest(View view) {
    }

    public void openSkill(View view) {
    }

    public void onClickSearch(View view) {

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
