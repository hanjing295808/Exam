package com.tarena.allrun.util;

import android.util.Log;

import com.tarena.allrun.TAppliction;

//日志工具类
public class LogUtil {
	public static void i(String tag, Object msg) {
		if (TAppliction.ISRELEASE) {
			// 运行在用户的真机上
			return;
		}
     //开发中
		Log.i(tag, String.valueOf(msg));
	}
	
//	public static void i(String tag,int msg)
//	{
//		i(tag,String.valueOf(msg));
//	}
}
