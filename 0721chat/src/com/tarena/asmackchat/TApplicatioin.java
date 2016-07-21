package com.tarena.asmackchat;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class TApplicatioin extends Application {
	// asmack聊天框架提供，用来连接openfire服务器
	public static XMPPConnection xmppConnection;
	// asmack框架提供，群聊
	public static MultiUserChat multiUserChat;

	@Override
	public void onCreate() {
		super.onCreate();
		new Thread() {
			public void run() {
				try {
					// openfire有两个端口
					// 9090:用浏览器访问，看到后台管理
					// 5222:用聊天客户端访问,发消息
					ConnectionConfiguration config = new ConnectionConfiguration(
							"172.60.12.198", 5222, "tarena.com");
					xmppConnection = new XMPPConnection(config);
					//让框架中的接口指向实现类
					MyPacketListener myPacketListener=new MyPacketListener();
					xmppConnection.addPacketListener(myPacketListener, null);
					
					xmppConnection.connect();

					xmppConnection.login("zjj01", "1");

					String room = "test2@conference.tarena.com";
					multiUserChat = new MultiUserChat(xmppConnection, room);
					multiUserChat.join("zjj01");

				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	class MyPacketListener implements PacketListener {

		@Override
		public void processPacket(Packet packet) {
			Log.i("收到内容", packet.toXML());
			if (packet instanceof Message) {
				// 框架收的是聊天内容
				Message msg = (Message) packet;
				String from = msg.getFrom();
				String body = msg.getBody();

				Intent intent = new Intent("showMessage");
				intent.putExtra("from", from);
				intent.putExtra("body", body);
				sendBroadcast(intent);
			}
		}
	}
}
