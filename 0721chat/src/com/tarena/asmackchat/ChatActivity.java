package com.tarena.asmackchat;

import java.io.InputStream;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ChatActivity extends Activity {
	EditText etBody;
	LinearLayout linearLayout;
	ShowMessage showMessage;
	ScrollView scrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		showMessage = new ShowMessage();
		this.registerReceiver(showMessage, new IntentFilter("showMessage"));

		linearLayout = (LinearLayout) findViewById(R.id.ll_chatContent);
		etBody = (EditText) findViewById(R.id.editTextBody);
		Button sendBtn = (Button) findViewById(R.id.buttonSend);
		sendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					String body = etBody.getText().toString();
					TApplicatioin.multiUserChat.sendMessage(body);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	public void sendImage(View v) {
		try {
			AssetManager assetManager = getAssets();
			InputStream is = assetManager.open("01.jpg");
			// 流的大小
			int size = is.available();
			byte[] data = new byte[size];
			is.read(data);
			// 如果好友用的也是android
			// 00000001 1
			// String str=new String(data);
			// data=str.getBytes();
			// 把二进制的byte[]转成字符
			String body = Base64.encodeToString(data, Base64.DEFAULT);
			// <audio><vidio><map>
			// 加标记 tag
			body = "<image>" + body;
			TApplicatioin.multiUserChat.sendMessage(body);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected void onDestroy() {
		this.unregisterReceiver(showMessage);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	class ShowMessage extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			try {
				// 从intent中得到聊天内容
				String body = intent.getStringExtra("body");
				String from = intent.getStringExtra("from");
				// 判断收的内容是文本还是图
				// body=<image>abcsd
				if (body.startsWith("<image>")) {
					// 用base64把字符串转成byte[]
					body = body.substring("<image>".length());
					byte[] data = Base64.decode(body, Base64.DEFAULT);
					// bitmapFactory把 byte[]转成bitmap
					Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
							data.length);
					// 创建imageView
					ImageView iv = new ImageView(context);
					iv.setImageBitmap(bitmap);
					linearLayout.addView(iv);
				} else {
					// 创建textView
					TextView tv = new TextView(context);
					tv.setText(body);
					// 把textView放到linearLayout
					// 动态添加控件
					linearLayout.addView(tv);
				}
				// 向上移
				// 运行在主线程，
				//如果你的内容刚刚添加到linearLayout,最后的内容高度得不到
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// 运行在主线程
						int linearLayoutHeight = linearLayout.getHeight();
						int scrollViewHeight = scrollView.getHeight();
						Log.i("向上移", "linearLayoutHeight=" + linearLayoutHeight
								+ "," + scrollViewHeight);
						if (linearLayoutHeight > scrollViewHeight) {
							int moveUp = linearLayoutHeight - scrollViewHeight;
							scrollView.scrollTo(0, moveUp);
						}
					}
				}, 10);

				// linearLayout.removeView(view)
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

}
