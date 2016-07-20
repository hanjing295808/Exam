package com.tarena.allrun.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout{

	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.i("绘制顺序", this.toString()+"构造方法");
	}	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.i("绘制顺序", this.toString()+" onMeasure");
	}	
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.i("绘制顺序", this.toString()+" onSizeChanged");
	}
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.i("绘制顺序", this.toString()+" onLayout");
		super.onLayout(changed, l, t, r, b);

	}
	//dispatch 分发
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.i("分发处理顺序", this.toString()+" dispatchTouchEvent");
		return super.dispatchTouchEvent(ev);
	}
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.i("分发处理顺序", this.toString()+" onInterceptTouchEvent");
		return super.onInterceptTouchEvent(ev);
	}
	//事件处理
	public boolean onTouchEvent(MotionEvent event) {
		int action=event.getAction();
		Log.i("分发处理顺序", this.toString()+" onTouchEvent action="+action);

		return super.onTouchEvent(event);
	}

}
