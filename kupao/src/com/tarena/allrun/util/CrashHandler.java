package com.tarena.allrun.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import com.tarena.allrun.MainActivity;
import com.tarena.allrun.TAppliction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class CrashHandler implements UncaughtExceptionHandler {
	TAppliction tAppliction;

	public CrashHandler(TAppliction tAppliction) {
		this.tAppliction = tAppliction;
	}

	// 程序任何地方，出了异常，没加catch,
	// 就会执行uncaughtException
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		ExceptionUtil.handleException(ex);

		// toast告诉用户重启
		new Thread() {
			public void run() {
				Looper.prepare();
				Toast.makeText(tAppliction, "抱歉，程序将重启", Toast.LENGTH_LONG)
						.show();
				Looper.loop();
			};
		}.start();

		Log.i("uncaughtException", "toast执行完了");
		try {
			Thread.currentThread().sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}

		// 实现重启功能
//0x10000000
		Intent intent = new Intent(tAppliction, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(tAppliction,
				100, intent, Intent.FLAG_ACTIVITY_NEW_TASK);

		// 定时器
		AlarmManager manager = (AlarmManager) tAppliction
				.getSystemService(Context.ALARM_SERVICE);

		manager.set(AlarmManager.RTC, System.currentTimeMillis() + 2000,
				pendingIntent);
		tAppliction.finishActivity();

	}

	

}
