package com.tarena.allrun.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

//�Զ��岼��(����)
public class CenterLayout extends ViewGroup {
	int groupWidth, groupHeight;

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.i("����˳��", this.toString()+" onSizeChanged");

		groupHeight = h;
		groupWidth = w;
	}

	public CenterLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.i("����˳��", this.toString()+" ���췽��");

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.i("����˳��", this.toString()+" onMeasure");

		// ����ÿ���ӿؼ�
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			// �õ��ӿؼ�
			View childView = getChildAt(i);
			// �����ӿؼ�
			childView.measure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	// ָ���������ӿؼ������ʾ
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.i("����˳��", this.toString()+" onLayout");

		int childViewCount = this.getChildCount();
		// �����ӿؼ��߶ȵĺ�
		int sum = 0;
		for (int i = 0; i < childViewCount; i++) {
			View childView = getChildAt(i);
			sum = sum + childView.getMeasuredHeight();
		}

		int top = (groupHeight - sum) / 2;
		// ָ��ÿ���ӿؼ���λ��
		for (int i = 0; i < childViewCount; i++) {
			View childView = getChildAt(i);
			int left = (groupWidth - childView.getMeasuredWidth()) / 2;
			int right = left + childView.getMeasuredWidth();
			int bottom = top + childView.getMeasuredHeight();
			childView.layout(left, top, right, bottom);
			top = top + childView.getMeasuredHeight();
		}
		// �õ��ӿؼ�
		// View animationView = this.getChildAt(0);
		// View shadeView = this.getChildAt(1);
		// ģ���linearLayout
		// animationView.layout(0, 0, 60, 60);
		// shadeView.layout(0, 60, 480, 60 + 50);
		// ģ���frameLayout
		// animationView.layout(0, 0, 60, 60);
		// shadeView.layout(0, 0, 480, 0 + 50);
		// ��Բ���
		// animationView.layout(0, 0, 60, 60);
		// shadeView.layout(60, 60, 480, 60 + 50);
	}
	//dispatch �ַ�
		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			Log.i("�ַ�����˳��", this.toString()+" dispatchTouchEvent");
			return super.dispatchTouchEvent(ev);
		}
		@Override
		public boolean onInterceptTouchEvent(MotionEvent ev) {
			Log.i("�ַ�����˳��", this.toString()+" onInterceptTouchEvent");

			//return super.onInterceptTouchEvent(ev);
			return true;//shadeView��onTouchEvent��ִ��
		}
		//�¼�����
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			int action=event.getAction();
			Log.i("�ַ�����˳��", this.toString()+" onTouchEvent action="+action);

			return super.onTouchEvent(event);
		}
}
