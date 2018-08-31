package cn.igame.sanguoheros.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import cn.igame.sanguoheros.R;

public class HomeActivity extends AppCompatActivity {

    private TextView logView;
    private int count = 0;
    private Handler handler = new Handler();
    private Runnable autoRun = new Runnable() {
        @Override
        public void run() {
//            logView.append("AAAAAAAAAAAAAAAAAAAABBBBbbbbbbbbbbAAAAAAAAAAAAAAAAAAABBBBbbbbbbbbbbAAAAAAAAAAAAAAAAAAABBBBbbbbbbbbbb" + count + "\n");
            logView.append("AAAAAAAAAAAAAAAAAAAABBB" + count + "\n");
            count++;
//            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logView = findViewById(R.id.layout_logcat);

//        handler.post(autoRun);
    }


    public void openLuggage(View view) {
        logView.append("AAAAAAAAAAAAAAAAAAAABBB" + count + "\n");
        count++;
    }

    public void openQuest(View view) {
        logView.append("AAAAAAAAAAAAAAAAAAAABBBBbNBBBBBBBBBBBBBBBBBBBBBBBBBBBB" + count + "\n");
        count++;
    }

    public void openSkill(View view) {
        logView.append("AAAAAAAAAAAAAAAAAAAABBBBbbbbbbbbbbAAAAAAAAAAAAAAAAAAABBBBbbbbbbbbbbAAAAAAAAAAAAAAAAAAABBBBbbbbbbbbbb" + count + "\n");
        count++;
    }
}
