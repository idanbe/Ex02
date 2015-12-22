package com.example.administrator.ex02;

/**
 * Created by Administrator on 12/11/2015.
 */
public class StopWatch
{

    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;


    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }


    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }


    //elapsed time in milliseconds
    public long getTimeMilli() {
        long elapsed;
        if (running) {
            elapsed = (System.currentTimeMillis() - startTime);
        }
        else {
            elapsed = (stopTime - startTime);
        }
        return elapsed;
    }


    //elapsed time in seconds
    public long getTimeSecs() {
        long elapsed;
        if (running) {
            elapsed = ((System.currentTimeMillis() - startTime) / 1000);
        }
        else {
            elapsed = ((stopTime - startTime) / 1000);
        }
        return elapsed;
    }

    public String getTime(){
        long time ;
        String s ="";
        if (running) {
            time = System.currentTimeMillis();
        }
        else {
            time = stopTime;
        }
        s = Long.toString((time - startTime) / 1000) + ":" + Long.toString((time - startTime)%100) ;
        //System.out.println("!!s = "+ s);
        return s ;

    }

}
