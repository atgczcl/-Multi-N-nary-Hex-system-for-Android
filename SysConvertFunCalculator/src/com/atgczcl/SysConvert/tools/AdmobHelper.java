package com.atgczcl.SysConvert.tools;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.InterstitialAd;

public class AdmobHelper {
	private Activity activity;
	public static final String MY_AD_UNIT_ID = "ca-app-pub-8879167851541244/6680925295";// a151be6bb64530b-pro a151bf28bb5cc0c-tools
																//a150f51626020da-normal a151bf3a1f4f8d8-calulator
	ArrayList<Timer> timers=new ArrayList<Timer>();
	ArrayList<AdView> adViews=new ArrayList<AdView>();
	InterstitialAd interstitial;
//	InterstitialAd interstitialForExit;
	
	public AdmobHelper(Activity activity) {
		this.activity=activity;
	}
	
	public void loadGoogleAdmobSide(int layoutId) {
		loadGoogleAdmobSide(layoutId, 20*1000, 10*1000);
	}

	public void loadGoogleAdmobSide(int layoutId, int loopTime, int delayTime) {
		// 创建 adView
		// adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);

		// 查找 LinearLayout，假设其已获得
		// 属性 android:id="@+id/mainLayout"
		Timer timer=new Timer();
		timers.add(timer);
		final AdView adView=new AdView(activity, AdSize.BANNER, MY_AD_UNIT_ID);
		adViews.add(adView);
		LinearLayout layout = (LinearLayout) activity.findViewById(layoutId);

		// 在其中添加 adView
		layout.addView(adView);

		// 启动一般性请求并在其中加载广告
		adView.loadAd(new AdRequest());
		// 刷新广告
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Log.d("Google admob", "time refresh!");
						adView.loadAd(new AdRequest());
					}
				});
			}
		}, delayTime, loopTime);
	}
	
	

	public void makeFullScreenAd() {
		// 制作非页内广告
		interstitial = new InterstitialAd(activity, MY_AD_UNIT_ID);// MY_INTERSTITIAL_UNIT_ID
		interstitial.loadAd(new AdRequest());
		Timer timer2=new Timer();
		timers.add(timer2);
		interstitial.setAdListener(new myAdListener(interstitial,60*2*10000, timer2));
	}

	public void onDestroy() {
		for (int i = 0; i < adViews.size(); i++) {
			adViews.get(i).destroy();
		}
		adViews.clear();
		
		for (int i = 0; i < timers.size(); i++) {
			timers.get(i).cancel();
		}
		timers.clear();
	}

	public class myAdListener implements AdListener {
		private InterstitialAd interstitial;
		private Timer timer;
		private boolean isShowLoop=false;

		public myAdListener(InterstitialAd interstitial, int addLoadTime,
				Timer timer) {
			myAdListener.this.interstitial = interstitial;
			myAdListener.this.timer = timer;
			// 开始加载您的非页内广告
			if (isShowLoop) {
				myAdListener.this.timer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						activity.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								myAdListener.this.interstitial.loadAd(new AdRequest());
								myAdListener.this.interstitial.setAdListener(myAdListener.this);
							}
						});
					}
				}, 30 * 1000, addLoadTime);
			}
		}

		@Override
		public void onDismissScreen(Ad arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFailedToReceiveAd(Ad arg0, ErrorCode errorCode) {
			Log.d("Google admob", "failed to receive ad (" + errorCode + ")");
		}

		@Override
		public void onLeaveApplication(Ad arg0) {

		}

		@Override
		public void onPresentScreen(Ad arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onReceiveAd(Ad arg0) {
			interstitial.show();
		}

	}
}
