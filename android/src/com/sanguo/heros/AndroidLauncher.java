package com.sanguo.heros;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.analytics.FirebaseAnalytics;

import box2dLight.RayHandler;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		RayHandler.setGammaCorrection(true);
		RayHandler.useDiffuseLight(true);

		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.numSamples = 2;
		initialize(new SanguoGame(), config);
		FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);
		Bundle bundle = new Bundle();
		bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "testId");
		bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "testName");
		bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
		firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
	}
}
