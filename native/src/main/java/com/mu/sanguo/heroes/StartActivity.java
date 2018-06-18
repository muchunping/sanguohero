package com.mu.sanguo.heroes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.mu.sanguo.heroes.app.SgApplication;
import com.mu.sanguo.heroes.app.WorldContext;
import com.mu.sanguo.heroes.model.Player;
import com.mu.sanguo.heroes.ui.MainActivity;
import com.mu.sanguo.heroes.util.Logger;
import com.mu.sanguo.heroes.util.ToastUtil;

import cn.igame.sanguoheros.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    private PopupWindow popupWindow;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //创建角色
            startCreatePlayer();
        }
    };

    private int sex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        WorldContext world = ((SgApplication) getApplication()).getWorldContext();
        world.init(this);

        Player player = null;
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(this);
        String playerInfoString = spf.getString("player_info", null);
        if (!TextUtils.isEmpty(playerInfoString)) {
            player = Player.readPlayerInfoFromString(playerInfoString);
        }

        View archiveButton = findViewById(R.id.button_old);
        archiveButton.setEnabled(player != null);
        archiveButton.setTag(player);
        archiveButton.setOnClickListener(this);
        findViewById(R.id.button_new).setOnClickListener(this);
        findViewById(R.id.button_set).setOnClickListener(this);
    }

    private void startCreatePlayer() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(this);
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.setAnimationStyle(R.style.top_to_bottom);
            //noinspection deprecation
            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_inventory));
        }

        //设置角色名和性别
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.window_create_player_one, null, false);
        popupWindow.setContentView(view);

        final EditText editText = view.findViewById(R.id.editText);
        RadioGroup group = view.findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sex = checkedId == R.id.radioButton1 ? 0 : 1;
            }
        });
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                if (name.isEmpty()) {
                    ToastUtil.showToast("角色名不能为空");
                    return;
                }
                cratePlayer(name);
                popupWindow.dismiss();
            }
        });

        View parent = findViewById(R.id.contentView);
        if (parent != null) {
            popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        }
    }

    private void cratePlayer(String name) {
        Logger.dL("创建角色{" + name + "," + (sex == 0 ? "男" : "女") + "}");
        Player player = new Player(name, sex);
        ((SgApplication) getApplication()).getWorldContext().initPlayer(player);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(runnable);
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_new) {
            Logger.dL("onClick: " + "新的开始");
            startCreatePlayer();
        } else if (id == R.id.button_old) {
            Logger.dL("onClick: " + "再续前缘");
            WorldContext world = ((SgApplication) getApplication()).getWorldContext();
            world.joinWorld((Player) v.getTag());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.button_set) {
            Logger.dL("onClick: " + "设置");
        }
    }
}
