package com.tarena.allrun.widget;

import com.tarena.allrun.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

//自定义动画控件
public class AnimationView extends View {
	Bitmap[] array;
	int index=0;
	Thread thread;
	boolean isRunning=true;
	int viewWidth,viewHeight;
	float sleepTime;
	//测量控件大小
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//图片的大小就是控件的大小
		int imageWidth=array[0].getWidth();
		int imageHeight=array[0].getHeight();
		//把的图的大小设置成控件的大小
		setMeasuredDimension(imageWidth, imageHeight);
	}
	class MyRunnable implements Runnable
	{

		@Override
		public void run() {
			while (isRunning) {
				try {
					index++;
					if (index>array.length-1)
					{
						index=0;
					}
					
					//让线程调onDraw()用invalidate();
					//工作线程调onDraw()
					postInvalidate();
					Log.i("ondraw()", "run index="+index+","+System.currentTimeMillis());

					Thread.currentThread().sleep((int)sleepTime);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
		}
		
	}

	public AnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);
	//从布局文件中读取自定义属性
		TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.AnimationView);
		sleepTime=typedArray.getFloat(R.styleable.AnimationView_sleep_time, 0);
	//读取数组
		Resources resources=getResources();
		TypedArray imageIDArray=resources.obtainTypedArray(R.array.animationImages);
		int length=imageIDArray.length();
		array=new Bitmap[length];
		for (int i=0;i<length;i++)
		{
			//从数组中读取图的id 
			int imageId=imageIDArray.getResourceId(i, 0);
			//根据图的id读取图
			array[i]=BitmapFactory.decodeResource(resources, imageId);
		}
		thread=new Thread(new MyRunnable());
		thread.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		try {
			Paint paint=new Paint();
			paint.setColor(Color.RED);
			//画背景
			Rect rect=new Rect(0, 0, viewWidth, viewHeight);
			canvas.drawRect(rect, paint);
			
			Bitmap bitmap=array[index];
			
			canvas.drawBitmap(bitmap, 0, 0, paint);
			Log.i("ondraw()", "index="+index+",bitmap="+bitmap.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
viewHeight=h;
viewWidth=w;
		super.onSizeChanged(w, h, oldw, oldh);
	}
}
