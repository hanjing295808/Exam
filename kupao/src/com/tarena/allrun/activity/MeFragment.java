package com.tarena.allrun.activity;

import com.tarena.allrun.R;
import com.tarena.allrun.TAppliction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MeFragment extends Fragment {
	View view;
	Button btnExit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_me, null);
		setViews();
		addListener();
		return view;
	}

	private void addListener() {
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
TAppliction tAppliction=(TAppliction) 
getActivity().getApplication();
tAppliction.finishActivity();
			}
		});
	}

	private void setViews() {
		btnExit=(Button) 
				view.findViewById
				(R.id.btn_me_exit);
	}

}
