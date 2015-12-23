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
    private float top, left, width, height;
    private float top_rect=0, right_rect=250,left_rect=0,bottom_rect=125;
    private float radyus=50;
    private int rand,rect_x,rect_y;
    private int i,j;
    private Path path;
    private Random random;
    private String complexty="0";
    private int[] ArrX = {60,360,690,70,360,700,80,370,710,60,360,690,70,360,700};
    private int[] ArrY = {70,70,70,240,240,240,420,420,420,590,590,590,760,760,760};
    private int num_loc=20;
    private boolean[] location=new boolean[num_loc];

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
        reset_locstion();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        left = getPaddingRight();
        top = getPaddingTop();
        width = w - (getPaddingLeft() + getPaddingRight());
        height = h - (getPaddingTop() + getPaddingBottom());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        random = new Random();
        rand = random.nextInt(20);
        Log.d("debug5", "rand:" + rand);
        // location[rand]=true;
         //rect_x=ArrX[rand];
        // rect_y=ArrY[rand];
         canvas.drawRect(left_rect + ArrX[rand], top_rect + ArrY[rand], right_rect + ArrX[rand], bottom_rect + ArrY[rand], paint);



        /*
        for(i=0;i<Integer.parseInt(complexty);i++)
        {
            rand = random.nextInt(20);
            while (location[rand]==true) //loction not clear to draw
            {
                rand = random.nextInt(20);
            }
            location[rand]=true ;
            canvas.drawCircle(ArrX[rand], ArrY[rand], radyus, paint);
        }
            /*


        /*
        for (i=0;i<ArrX.length;i++)
        {
           // canvas.drawRect(left_rect + ArrX[i], top_rect + ArrY[i], right_rect + ArrX[i], bottom_rect + ArrY[i], paint);
          canvas.drawCircle(ArrX[i], ArrY[i], radyus, paint);
        }
         /*/

    }


    private void reset_locstion()
    {
        for (i=0;i<num_loc;i++)
        {
            location[i]=false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:


                if((event.getX()>=(rect_x+left_rect))&&((event.getX()<=(rect_x+right_rect))))
                {
                    if ((event.getY()>=(rect_y+top_rect))&&(event.getY()<=(rect_y+bottom_rect)))
                    {
                        //invalidate();
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



    public void set_complexty(String c)
    {
        complexty=c;
        Log.d("debug1","comlexty:"+c);
    }




}
