package cn.igame.sanguoheros.ui;

import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.app.SgApplication;
import cn.igame.sanguoheros.model.Player;
import cn.igame.sanguoheros.model.SystemActor;

/**
 * 主界面
 * Created by Administrator on 2015/11/9.
 */
public class MainActivity extends AppCompatActivity {
    private FrameLayout floatLayout;
    private TextView logView;
    private ArrayList<SystemActor> actorList = new ArrayList<>();
    private TextView playerNameView, playerSexView, playerLevelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parseSystemActorXml();

        playerNameView = (TextView) findViewById(R.id.playerNameView);
        playerSexView = (TextView) findViewById(R.id.playerSexView);
        playerLevelView = (TextView) findViewById(R.id.playerLevelView);
        fillPlayerInfoLayout();

        RecyclerView systemActorLayout = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        systemActorLayout.setLayoutManager(layoutManager);
        systemActorLayout.setHasFixedSize(true);
        systemActorLayout.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_system_actor, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                SystemActor systemActor = actorList.get(position);
                holder.nameView.setText(systemActor.getName());
                holder.actionView.setText(systemActor.getAction());
            }

            @Override
            public int getItemCount() {
                return actorList.size();
            }
        });
        systemActorLayout.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom += 10;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SgApplication.getWorldContext().savePlayer();
    }

    private void fillPlayerInfoLayout(){
        Player player = SgApplication.getWorldContext().getPlayer();
        playerNameView.setText(player.getName());
        playerSexView.setText(String.format("等级  %d", player.getLevel()));
        playerLevelView.setText(MessageFormat.format("性别  {0}", player.getSexString()));
    }

    private void parseSystemActorXml(){
        //解析NPC列表
        XmlResourceParser xrp = getResources().getXml(R.xml.system_actor);
        try {
            int eventType;
            while ((eventType = xrp.next()) != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String s = xrp.getName();
                    if (s.equals("item")) {
                        SystemActor systemActor = new SystemActor();
                        systemActor.createFromXml(xrp);
                        actorList.add(systemActor);
                    }
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
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