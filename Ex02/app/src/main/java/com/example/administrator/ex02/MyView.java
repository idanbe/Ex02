package com.example.administrator.ex02;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by Administrator on 12/10/2015.
 */
public class MyView extends View {

    private Paint paint;
    private float top, right, width, height;
    private float top_rect=600, right_rect=550,left_rect=200,bottom_rect=725;
    private float cx=200,cy=200,radyus=100;

    private Path path;
    private Random random;



    public MyView(Context context) {
        super(context);
        init(null, 0);

    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        path = new Path();

        random = new Random(System.currentTimeMillis());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        right = getPaddingRight();
        top = getPaddingTop();
        width = w - (getPaddingLeft() + getPaddingRight());
        height = h - (getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(left_rect,top_rect,right_rect,bottom_rect,paint);
        //todo draw n circle (n=complixtiy)
        canvas.drawCircle(cx,cy,radyus,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
        }
        return true;
    }



}
