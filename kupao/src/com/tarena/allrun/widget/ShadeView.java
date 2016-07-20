package com.tarena.allrun.widget;

import com.tarena.allrun.R;
import com.tarena.allrun.util.DisplayUtil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

//阴影控件
public class ShadeView extends View {
	String text;
	int textColor, shadeColor;
	float textSize;
	int shadeSize = 5;
	int stringWidth;
	int stringHeight;

	public ShadeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.i("绘制顺序", this.toString()+" 构造方法");

		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.Shade);
		text = typedArray.getString(R.styleable.Shade_text);
		textColor = typedArray.getColor(R.styleable.Shade_text_color,
				Color.BLACK);
		shadeColor = typedArray.getColor(R.styleable.Shade_shade_color,
				Color.GRAY);
		//得到值，假定单位是dp
		textSize = typedArray.getFloat(R.styleable.Shade_text_size, 24);
		//把dp转成pc
		textSize=DisplayUtil.dp2px(getContext(), textSize);
		shadeSize = (int) (textSize / 10);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.i("绘制顺序", this.toString()+" onMeasure");

		// 用rect得文字的宽度，高度
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		Rect sizeRect = new Rect();
		paint.getTextBounds(text, 0, text.length(), sizeRect);
		// 文字的宽度，高度就控件的宽度，高度
		stringWidth = sizeRect.width();
		stringHeight = sizeRect.height();
		// 问题：文字的下面有点显示不出来
		// 解决方法：增加控件的高度
		int viewHeight = (int) (stringHeight * 1.2);
		setMeasuredDimension(stringWidth, viewHeight);

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.i("绘制顺序", this.toString()+" onSizeChanged");

	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.i("绘制顺序", this.toString()+" onDraw");

		Paint paint = new Paint();
		paint.setTextSize(textSize);
		// 画阴影
		paint.setColor(shadeColor);
		canvas.drawText(text, shadeSize, stringHeight + shadeSize, paint);
		// 画文字
		paint.setColor(textColor);
		canvas.drawText(text, 0, stringHeight, paint);

	}
	//dispatch 分发
		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			Log.i("分发处理顺序", this.toString()+" dispatchTouchEvent");
			return super.dispatchTouchEvent(ev);
		}
		
		//事件处理
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			int action=event.getAction();
			Log.i("分发处理顺序", this.toString()+" onTouchEvent action="+action);
//return true CenterLayout,MyLinearLayout的onTouchEvent不执行，动作有按下，移动，松开
			return super.onTouchEvent(event);
		}
}
