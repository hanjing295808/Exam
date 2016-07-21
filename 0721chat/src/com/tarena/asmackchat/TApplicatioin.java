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
	// asmack�������ṩ����������openfire������
	public static XMPPConnection xmppConnection;
	// asmack����ṩ��Ⱥ��
	public static MultiUserChat multiUserChat;

	@Override
	public void onCreate() {
		super.onCreate();
		new Thread() {
			public void run() {
				try {
					// openfire�������˿�
					// 9090:����������ʣ�������̨����
					// 5222:������ͻ��˷���,����Ϣ
					ConnectionConfiguration config = new ConnectionConfiguration(
							"172.60.12.198", 5222, "tarena.com");
					xmppConnection = new XMPPConnection(config);
					//�ÿ���еĽӿ�ָ��ʵ����
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
			Log.i("�յ�����", packet.toXML());
			if (packet instanceof Message) {
				// ����յ�����������
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
