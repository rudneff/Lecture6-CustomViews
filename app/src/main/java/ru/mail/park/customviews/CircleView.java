package ru.mail.park.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by rudi on 16.10.16.
 */

public class CircleView extends View {

    private final int BACKGROUND_COLOR = Color.parseColor("#EEFFCC");
    private class Circle{
        int radius;
        int centerX;
        int centerY;
        int color;
    }

    private Circle circle = new Circle();

    private Paint paint  = new Paint();

    private Random rnd = new Random();

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (wMode == MeasureSpec.EXACTLY) {
            if (hMode == MeasureSpec.EXACTLY) {
                setMeasuredDimension(width, height);
            } else if (hMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(width, Math.min(width, height));
            } else {
                setMeasuredDimension(width, width);
            }
        } else if (wMode == MeasureSpec.AT_MOST) {
            if (hMode == MeasureSpec.EXACTLY) {
                setMeasuredDimension(Math.min(width, height), height);
            } else if (hMode == MeasureSpec.AT_MOST) {
                int minSize = Math.min(width, height);
                setMeasuredDimension(minSize, minSize);
            } else {
                setMeasuredDimension(width, width);
            }
        } else {
            if (hMode == MeasureSpec.UNSPECIFIED) {
                setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
            } else {
                setMeasuredDimension(height, height);
            }
        }
        circle.centerX = getMeasuredWidth()/2;
        circle.centerY = getMeasuredHeight()/2;
        circle.radius = Math.min(getMeasuredWidth(), getMeasuredHeight())/2;
        circle.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256),rnd.nextInt(256));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(BACKGROUND_COLOR);
        canvas.drawRect(0,0,getWidth(),getHeight(), paint);
        paint.setColor(circle.color);
        canvas.drawCircle(circle.centerX, circle.centerY, circle.radius, paint);
        postInvalidateDelayed(100);
    }

    private Boolean checkPointInCircle(float cX, float cY, float r, float pX, float pY) {
        return Math.sqrt(Math.pow((cX - pX), 2) + Math.pow((cY - pY), 2)) <= r;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN : {
                float viewX = event.getX();
                float viewY = event.getY();
                if (checkPointInCircle(
                        circle.centerX,
                        circle.centerY,
                        circle.radius,
                        viewX,
                        viewY)) {
                    circle.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
