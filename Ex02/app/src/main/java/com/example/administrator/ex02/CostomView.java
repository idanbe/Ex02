package com.example.administrator.ex02;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.Random;


public class CostomView extends View {

    private Paint paint;
    private float top, left, width, height;
    private float top_rect=0, right_rect=250,left_rect=0,bottom_rect=105;
    private float cx=30,cy=20,radyus=50;
    private int rand=0,rect_x=0,rect_y=0;
    private int i;
    private static final int zero=0;
    private static final int ten=10;
    private Path path;
    private Random random;
    private static int complexity;
    private static boolean gameOn = false ;
    private int[] ArrX = {60,360,690,70,360,700,80,370,710,60,360,690,70,360,700};
    private int[] ArrY = {70,70,70,240,240,240,420,420,420,590,590,590,760,760,760};
    private int num_loc=15;
    private boolean[] location;

    public CostomView(Context context) {
        super(context);
        init(null, zero);
        reset_location();
    }

    public CostomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, zero);
    }

    public CostomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }


    private void init(AttributeSet attrs, int defStyle) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(ten);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        path = new Path();
        location =new boolean[num_loc];
        reset_location();
        random = new Random();
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
        System.out.println("!! onDraw !!");

        //draw rect
        rand = random.nextInt(num_loc);
        location[rand]=true;
        rect_x=ArrX[rand];
        rect_y=ArrY[rand];
        canvas.drawRect(left_rect + rect_x, top_rect + rect_y, right_rect + rect_x, bottom_rect + rect_y, paint);

        //draw circle
        System.out.println("!! complexity = " + complexity);
        for(i=zero ; i <complexity; i++)
        {
            rand = random.nextInt(num_loc) ;
            while (location[rand]) //loction not clear to draw
            {
                rand = random.nextInt(num_loc);
            }
            location[rand] = true;
            canvas.drawCircle((ArrX[rand]+cx),(ArrY[rand]+cy), radyus, paint);
        }
    }

    // reset boolean array
    private void reset_location()
    {
        for (i=zero ; i <num_loc; i++)
        {
            location[i]=false;
        }
        return;
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



    public void set_complexity(int c) {
        this.complexity = c;

    }

    public  void setGameOn(boolean gameOn) {
        this.gameOn = gameOn ;
    }




}
