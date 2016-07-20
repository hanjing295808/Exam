package com.tarena.allrun;

import com.tarena.allrun.activity.BaseActivity;
import com.tarena.allrun.activity.MainFragmentActivity;
import com.tarena.allrun.util.ExceptionUtil;
import com.tarena.allrun.util.LogUtil;
import com.tarena.allrun.widget.AnimationView;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	AnimationView animationView;
static{
	System.loadLibrary("hello-jni");
}
	public native void showWj();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showWj();
		try {
			setContentView(R.layout.activity_main);
			animationView = (AnimationView) findViewById(R.id.animationView);
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					startActivity(
					 new Intent(MainActivity.this,
					 MainFragmentActivity.class));
					finish();
				}
			}, 2000);
		} catch (Exception e) {
			// e.printStackTrace();
			ExceptionUtil.handleException(e);
		}

	}

	private void testLog() {
		long startTime = System.currentTimeMillis();
		int sum = 0;
		for (int i = 0; i < 10000; i++) {
			sum = sum + i;
			//Log.i("logTime", "" + i);
			LogUtil.i("logTime", "" + i);
		}
		long endTime = System.currentTimeMillis();
		Log.i("logTime", "log time=" + (endTime - startTime));
		
		 startTime = System.currentTimeMillis();
		 sum = 0;
		for (int i = 0; i < 10000; i++) {
			sum = sum + i;
			//Log.i("logTime", "" + i);
		}
		 endTime = System.currentTimeMillis();
		Log.i("logTime", "Ã»ÓÐlog time=" + (endTime - startTime));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int width = animationView.getWidth();
		int height = animationView.getHeight();
		Log.i("widthheight", width + "," + height);
		Toast toast=new Toast(this);
		View view=null;
		TextView tv=(TextView) view.findViewById(0);
		tv.setText("");
		toast.setView(view);
		
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
