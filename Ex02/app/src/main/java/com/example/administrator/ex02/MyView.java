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
    private float top_rect=0, right_rect=250,left_rect=0,bottom_rect=125;
    private float cx,cy,radyus=50;
    private int rect_x,rect_y;

    private Path path;
    private Random random;
    private  String level = "1";
    private  String complexity = "3";


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

        random = new Random();
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

        rect_x = random.nextInt(getWidth());
        while((rect_x+left_rect<getLeft()) || (rect_x+right_rect>getRight()))
        {
            rect_x = random.nextInt(getWidth());
        }
        rect_y = random.nextInt(getBottom());
        while ((rect_y+top_rect<getTop())||(rect_y+bottom_rect>getBottom()))
        {
            rect_y = random.nextInt(getBottom());
        }

        canvas.drawRect(left_rect+rect_x, top_rect+rect_y, right_rect+rect_x, bottom_rect+rect_y, paint);

        for (int i=0;i< Integer.parseInt(complexity);i++) {
            cx = random.nextInt(getWidth());
            while ((cx-radyus<getLeft())||(cx+radyus>getRight()))
            {
                cx = random.nextInt(getWidth());
            }
            cy = random.nextInt(getBottom());
            while ((cy-radyus<getTop())||(cy+radyus>getBottom()))
            {
                cy = random.nextInt(getBottom());
            }

            canvas.drawCircle(cx, cy, radyus, paint);
        }

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
