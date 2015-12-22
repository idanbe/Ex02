package com.example.administrator.ex02;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;


public class CostomView extends View {

    private Paint paint;
    private float top, right, width, height;
    private float top_rect=0, right_rect=250,left_rect=0,bottom_rect=125;
    private float cx,cy,radyus=50;
    private int rect_x,rect_y;
    private int i,j;
    private Path path;
    private Random random;
    private int[] Arrcx = new int[4];
    private int[] Arrcy = new int[4];

    public CostomView(Context context) {
        super(context);
        init(null, 0);

    }

    public CostomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CostomView(Context context, AttributeSet attrs, int defStyle) {
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

        for (i=0;i<4;i++)
        {
            Arrcx[i]=0;
            Arrcy[i]=0;
        }

        rect_x = random.nextInt((int)width);
        while((rect_x)<getLeft()|| ((rect_x+right_rect)>getRight()))
          {
             rect_x = random.nextInt((int)width);
          }
        rect_y =random.nextInt((int)height);

        while (((rect_y+bottom_rect)<getPaddingTop()))
        {
            rect_y = random.nextInt((int)height);
        }


        canvas.drawRect(left_rect + rect_x, top_rect + rect_y, right_rect + rect_x, bottom_rect + rect_y, paint);

        /*for (i=0;i< Integer.parseInt(MainActivity.complexity);i++) {
            cx = random.nextInt(getWidth());
            while (((cx-radyus)<getLeft())||((cx+radyus)>getRight()))
            {
                cx = random.nextInt(getWidth());
            }
            cy = random.nextInt((int)height);
            while (((cy-radyus)>getTop())||((cy+radyus)<getBottom()))
            {
                cy = random.nextInt((int)height);
            }

            canvas.drawCircle(cx, cy, radyus, paint);
        }*/

    }


    private boolean checkIfClear(int x,int y)
    {
        for (i=0;i<4;i++)
        {
            if(((Arrcx[i]+radyus)>(x-radyus)||(Arrcx[i]-radyus)<(x+radyus))
                    &&((Arrcy[i]-radyus)<(y+radyus)||(Arrcy[i]-radyus)<(y+radyus)))
            {

            }

        }


        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if((event.getX()>=(rect_x+left_rect))&&((event.getX()<=(rect_x+right_rect))))
                {
                    if ((event.getY()>=(rect_y+top_rect))&&(event.getY()<=(rect_y+bottom_rect)))
                    {
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_CANCEL:
        }
        return false;
    }


}
