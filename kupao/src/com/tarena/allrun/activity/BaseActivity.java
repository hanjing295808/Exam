package com.tarena.allrun.activity;

import com.tarena.allrun.TAppliction;
import com.tarena.allrun.util.NetworkUtil;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity 
extends FragmentActivity{

	@Override
	protected void onCreate(Bundle arg0) {		
		super.onCreate(arg0);
		try {
			TAppliction tAppliction=
					(TAppliction) 
					getApplication();
			tAppliction.activityList.add(this);
			
			NetworkUtil.checkNetwork(this);
		} catch (Exception e) {
			e.printStackTrace();
		}}
	@Override
	protected void onDestroy() {
		try {
			TAppliction tAppliction=
					(TAppliction) 
					getApplication();
			tAppliction.activityList.remove(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

}
