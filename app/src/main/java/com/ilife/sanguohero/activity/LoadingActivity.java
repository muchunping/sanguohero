/**
 * 
 */
package com.ilife.sanguohero.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ilife.sanguohero.R;
import com.ilife.sanguohero.app.SgApplication;
import com.ilife.sanguohero.app.WorldContext;

public class LoadingActivity extends Activity {
	private ProgressBar progressBar;
	private TextView progressView;
	private TextView loadingView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressView = (TextView) findViewById(R.id.textView1);
		loadingView = (TextView) findViewById(R.id.textView2);
		
		SgApplication app = SgApplication.getApplication(this);
		app.world.addOnLoadingLinstener(new WorldContext.OnLoadingListener() {
			
			@Override
			public void load(int progress, String doing) {
				progressBar.setProgress(progress);
				progressView.setText(doing);
				loadingView.setText(progress + "%");
			}
		});
	}
	
	public void toMainActivity(){
		startActivity(new Intent(this, MainActivity.class));
	}
}
