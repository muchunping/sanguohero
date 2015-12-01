package cn.igame.sanguoheros;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import cn.igame.sanguoheros.app.SgApplication;
import cn.igame.sanguoheros.model.Player;
import cn.igame.sanguoheros.ui.MainActivity;
import cn.igame.sanguoheros.util.Logger;
import cn.igame.sanguoheros.util.ToastUtil;

public class StartActivity extends AppCompatActivity {
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

        SgApplication.getWorldContext().init(this);

        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(this);
        String playerInfoString = spf.getString("player_info", null);
        if(playerInfoString == null) {
            handler.postDelayed(runnable, 500);
        }else {
            Player player = Player.readPlayerInfoFromString(playerInfoString);
            if(player == null){
                handler.postDelayed(runnable, 500);
            }else {
                SgApplication.getWorldContext().joinWorld(player);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void startCreatePlayer() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(this);
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.setAnimationStyle(R.style.top_to_bottom);
            //noinspection deprecation
            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.zy));
        }

        //设置角色名和性别
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.window_create_player_one, null, false);
        popupWindow.setContentView(view);

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.linear_register);
        linearLayout.getBackground().setAlpha(160);

        final EditText editText = (EditText) view.findViewById(R.id.editText);
        RadioGroup group = (RadioGroup) view.findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sex = checkedId == R.id.radioButton1 ? 0 : 1;
            }
        });
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                if(name.isEmpty()) {
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
        SgApplication.getWorldContext().initPlayer(player);

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
}
