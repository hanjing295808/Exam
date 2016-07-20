package com.tarena.allrun.activity;

import java.util.ArrayList;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.map.C;
import com.tarena.allrun.R;
import com.tarena.allrun.TAppliction;
import com.tarena.allrun.util.BaiduMapUtil;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SportFragment extends Fragment {
	View view;
	// 定位
	LocationClient locationClient;
	MapView mapView;
	
	
	// 管理地图
	BaiduMap baiduMap;
	AlertDialog dialog;
	TextView tvAction;
	int count = 3;
	// 运动的坐标
	ArrayList<LatLng> positionList = new ArrayList();

	// 显示运动统计界面
	Handler handler = new Handler();
	int sleepTime = 2000;
	Runnable runnable;
	LinearLayout linearLayout;

	@Override
	public void onPause() {
		try {
			mapView.onPause();
			locationClient.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		super.onPause();
	}
	@Override
	public void onResume() {
		try {
			mapView.onResume();
			locationClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onResume();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			view = inflater.inflate(R.layout.fragment_sport, null);
			linearLayout = (LinearLayout) view
					.findViewById(R.id.ll_sport_recorder);
			tvAction = (TextView) view
					.findViewById(R.id.tv_fragment_sport_action);
			addListener();
			mapView = (MapView) view.findViewById(R.id.mapView);
			baiduMap = mapView.getMap();
			// 地图单击事件，得坐标。显示图
			baiduMap.setOnMapClickListener(new OnMapClickListener() {

				@Override
				public boolean onMapPoiClick(MapPoi arg0) {
					return false;
				}

				@Override
				public void onMapClick(LatLng position) {
					Log.i("我家的坐标", position.latitude + "," + position.longitude);
					// moveMapCenter(position);
					// baiduMap.clear();
					// showImage(position);
					// 模拟用户运动
					positionList.add(position);
					if (positionList.size() >= 2) {
						// 画线 markerOptions画图
						PolylineOptions polylineOptions = new PolylineOptions();
						// 画线用到的点
						polylineOptions.points(positionList);
						baiduMap.clear();
						baiduMap.addOverlay(polylineOptions);

					}
				}
			});
			locationClient = new LocationClient(getActivity());
			// 设置定位的参数
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);
			// option.setCoorType("bd09ll");
			// 每隔2秒得一次坐标
			// 时间少于1000,只得一次
			option.setScanSpan(1);
			// 再次重新定位
			// locationClient.requestLocation();
			locationClient.setLocOption(option);
			// 2.4让百度地图框架中的接中
			// BdlocationListener指向实现类
			MyBdLocationListener listener = new MyBdLocationListener();
			locationClient.registerLocationListener(listener);
			locationClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	private void addListener() {
		tvAction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String text=((TextView)v).getText()
							.toString();
					//判断单击的时候是开始还是结束
					if ("结束".equals(text))
					{
						//上传数据
						if (TAppliction.networkIsNone)
						{
							//dialog
						}
						//用户开了移动网络和wifi,上网用的是wifi
						
						//只开了移动，关了wifi
						if (TAppliction.networkIsMobile && !TAppliction.networkIsWifi)
						{
							//dialog你现在是移动网络，联网会产生费用
						}
						linearLayout.setVisibility(View.GONE);
						baiduMap.clear();
						positionList.clear();
						handler.removeCallbacks(runnable);
						tvAction.setText("开始");
						count=3;
					}else
					{
					
					// 把xml变成view
					View view = View.inflate(getActivity(),
							R.layout.activity_show_counter, null);
					// 创建dialog
					dialog = new AlertDialog.Builder(getActivity()).create();
					// 为dialog设置view
					dialog.setView(view);
					// 显示dialog
					dialog.show();

					// 从view中找到textView
					final TextView tv = (TextView) view
							.findViewById(R.id.tv_show_count);
					// 每隔2秒中count-1,并且显示出来
					final Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							tv.setText(String.valueOf(count));
							count = count - 1;
							if (count > -1) {
								handler.postDelayed(this, 2000);
							} else {
								dialog.dismiss();
								dialog = null;
								// 显示统计界面
								showRecorder();
							}

						}

					}, 100);					
					tvAction.setText("结束");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void moveMapCenter(LatLng currentPosition) {
		// 17地图显示的级别4-17
		MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(
				currentPosition, 17);
		// 以动画的方式移动地图
		baiduMap.animateMapStatus(update);
	}

	private void showImage(LatLng currentPosition) {
		// 在当前位置上显示一张图
		MarkerOptions options = new MarkerOptions();
		options.position(currentPosition);
		options.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.icon_marka));
		baiduMap.addOverlay(options);
	}

	// 2.3 写实现类
	class MyBdLocationListener implements BDLocationListener {
		// 百度地图框架定位成后调接口，接口指向实现类
		@Override
		public void onReceiveLocation(BDLocation bdlocation) {
			try {
				// 得经度
				double longitude = bdlocation.getLongitude();
				// 纬度
				double latitude = bdlocation.getLatitude();
				Log.i("坐标", "纬度=" + latitude + "经度=" + longitude);
				// 判断程序有没有得到坐标
				// 得到是4.9E-324,没有得到坐标，两个原因
				// 1,用的是原生模拟器
				// 2,gps信号不对。
				if (latitude == 4.9E-324) {
					// 模拟一个地址
					latitude = 39.876539;
					longitude = 116.464984;
				}

				// 移动地图中心点为当前位置
				LatLng currentPosition = new LatLng(latitude, longitude);
				// Status状态
				moveMapCenter(currentPosition);
				showImage(currentPosition);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	private void showRecorder() {
		linearLayout.setVisibility(View.VISIBLE);
		// 找到统计界面上的控件
		// meter是统计时间
		final Chronometer meter = (Chronometer) view
				.findViewById(R.id.chronometer1);
		meter.start();
		meter.setBase
		(SystemClock.elapsedRealtime());
		final TextView tvDistance=
				(TextView) view.
				findViewById(R.id.tv_distance);
		tvDistance.setText("0.00");
		final TextView tvSpeed=(TextView) view
				.findViewById
				(R.id.tv_recorder_speed);
		tvSpeed.setText("0.00");
		// 每隔2秒计算数据
		runnable=new Runnable() {
			
			@Override
			public void run() {
				try {
					Log.i("showRecorderRun", ""+System.currentTimeMillis());
					double distance=0;
					if (positionList.size()>=2)
					{
					for (int i=0;i<positionList.size()-1;i++)
					{
						double long1=positionList.get(i)
								.longitude;
						double lat1=positionList.get(i)
								.latitude;
						double long2=positionList.get(i+1)
								.longitude;
						double lat2=positionList.get(i+1)
								.latitude;
						distance=distance+BaiduMapUtil.GetDistance(long1, lat1, long2, lat2);
						
					}
					//把米转成公里
					distance=distance/1000;
					String strDistance=String.valueOf
							(distance);
					if (strDistance.contains("."))
					{
						int pointIndex=strDistance
								.indexOf(".");
						strDistance=strDistance.
								substring(0, pointIndex+3);
						
					}
					tvDistance.setText(strDistance);
					//得时间01:48
					String time=meter.getText().toString();
					//得分钟
					//[0]放的是01 [1]放的是48
					String[] array=time.split(":");
					double minute=Integer.parseInt(array[0]);
					//得秒
					double second=Double.parseDouble(array[1]);
					//把秒转成小时
					double hour=(minute*60+second)/60/60;
					double speed=distance/hour;
					String strSpeed=String.valueOf(speed);
					if (strSpeed.contains("."))
					{
						strSpeed=strSpeed.substring
								(0, strSpeed.indexOf(".")
										+3);
					}
					tvSpeed.setText(strSpeed);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally
				{
					handler.postDelayed(this, sleepTime);
				}
			}
		};
		handler.postDelayed
		(runnable, sleepTime);

				
				
				
				
	}
}
