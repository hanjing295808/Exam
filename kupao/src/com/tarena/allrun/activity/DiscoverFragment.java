package com.tarena.allrun.activity;

import com.tarena.allrun.R;
import com.tarena.allrun.util.ExceptionUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class DiscoverFragment extends Fragment {
	View view;
	LinearLayout llFriend,llTaobao;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_discover, null);

		llFriend = (LinearLayout) view.findViewById(R.id.ll_discover_friend);
		llFriend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					getActivity().startActivity(
							new Intent(getActivity(), ChatActivity.class));
				} catch (Exception e) {
					ExceptionUtil.handleException(e);
				}
			}
		});
		
		
		llTaobao = (LinearLayout) view.findViewById(R.id.ll_discover_mall);
		llTaobao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					getActivity().startActivity(
							new Intent(getActivity(), TaobaoActivity.class));
				} catch (Exception e) {
					ExceptionUtil.handleException(e);
				}
			}
		});
		return view;
	}

}
