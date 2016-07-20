package com.tarena.allrun.receiver;

import com.tarena.allrun.TAppliction;
import com.tarena.allrun.util.ExceptionUtil;
import com.tarena.allrun.util.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

//用户关网或打开网络
public class NetworkStateChangedReceiver extends BroadcastReceiver {
//第一种：使用静态变量
	//static boolean isOpenMobile=false;
	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			// 判断用户是关网还是打开网络
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
			if (activeNetworkInfo == null) {
				LogUtil.i("关网打开", "用户 关网了");
				TAppliction.networkIsNone=true;

			} else {
				// 判断用户打开的是移动网络还是wifi
				// 电影
				// 包月100MB 1MB是1块钱
				// 4G 1秒100MB 1天 24*60*60*100*1=
				// 在移动网络下，下载有提示“现在是移动网络，下载会产生费用”
				NetworkInfo mobileNetwork = manager
						.getNetworkInfo(manager.TYPE_MOBILE);
				if (mobileNetwork != null && mobileNetwork.isConnected()) {
					LogUtil.i("关网打开", "用户打开的是移动网络");
					//isOpenMobile=true;
					TAppliction.networkIsMobile=true;
				}

				NetworkInfo wifiNetworkInfo = manager
						.getNetworkInfo(manager.TYPE_WIFI);
				if (wifiNetworkInfo != null && wifiNetworkInfo.isConnected()) {
					LogUtil.i("关网打开", "用户打开的是wifi网络");
					TAppliction.networkIsWifi=true;

				}
			}
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}
	}

}
