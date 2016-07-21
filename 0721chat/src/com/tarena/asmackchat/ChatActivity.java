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
			// ���Ĵ�С
			int size = is.available();
			byte[] data = new byte[size];
			is.read(data);
			// ��������õ�Ҳ��android
			// 00000001 1
			// String str=new String(data);
			// data=str.getBytes();
			// �Ѷ����Ƶ�byte[]ת���ַ�
			String body = Base64.encodeToString(data, Base64.DEFAULT);
			// <audio><vidio><map>
			// �ӱ�� tag
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
				// ��intent�еõ���������
				String body = intent.getStringExtra("body");
				String from = intent.getStringExtra("from");
				// �ж��յ��������ı�����ͼ
				// body=<image>abcsd
				if (body.startsWith("<image>")) {
					// ��base64���ַ���ת��byte[]
					body = body.substring("<image>".length());
					byte[] data = Base64.decode(body, Base64.DEFAULT);
					// bitmapFactory�� byte[]ת��bitmap
					Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
							data.length);
					// ����imageView
					ImageView iv = new ImageView(context);
					iv.setImageBitmap(bitmap);
					linearLayout.addView(iv);
				} else {
					// ����textView
					TextView tv = new TextView(context);
					tv.setText(body);
					// ��textView�ŵ�linearLayout
					// ��̬��ӿؼ�
					linearLayout.addView(tv);
				}
				// ������
				// ���������̣߳�
				//���������ݸո���ӵ�linearLayout,�������ݸ߶ȵò���
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// ���������߳�
						int linearLayoutHeight = linearLayout.getHeight();
						int scrollViewHeight = scrollView.getHeight();
						Log.i("������", "linearLayoutHeight=" + linearLayoutHeight
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
