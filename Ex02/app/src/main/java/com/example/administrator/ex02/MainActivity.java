package com.example.administrator.ex02;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

   //views

    private TextView text_recent,text_best, time1_text,time2_text;
    private StopWatch stopWatch = new StopWatch(); // init watch
    SharedPreferences sharedPreferences;
    static final String text_time1_key ="key1";     // key for shardepre..
    static final String text_time2_key ="key2";
    static final String key_xx ="key3";
    static final String key_yy ="key4";
    static final String Rxx ="key5";
    static final String Ryy ="key6";
    static final String str_xx ="XX";
    static final String str_yy ="YY";
    static final String RECENT ="Recent result";
    static final String CURRENT ="Current time";
    static final String str_time1= "00:00";
    static   String level = "1";
    static   String complexity = "0";
    private final String TAG = getClass().getSimpleName();
    private  int counterPress=0;
    private Intent intent;
    private Bundle bundle;
    private boolean best_presed=false;
    private  Boolean gameOn = false;
    private AppEntryTimeDAL dal;
    private  GameFragment fragment ;

    private String formatSSMM(){
        String s = "" ;
        s = Integer.toString((int)stopWatch.getTimeSecs() );
        s += ":";
        s +=Integer.toString((int)stopWatch.getTimeMilli() % 100 );
        return s ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start_button,setting_button,red_button;

        start_button = (Button) findViewById(R.id.button_start);
        setting_button = (Button) findViewById(R.id.button_sttings);
        red_button = (Button) findViewById(R.id.button_red);
        time1_text = (TextView) findViewById(R.id.text_time1);
        time2_text = (TextView) findViewById(R.id.text_time2);
        text_recent = (TextView) findViewById(R.id.textView_resnt);
        text_best = (TextView) findViewById(R.id.textView_best);
        best_presed=false;

        // create shared pref...
        sharedPreferences = getPreferences(MODE_PRIVATE);

        // create dal
        dal = new AppEntryTimeDAL(this);

        dal.removeAll();

        dal.addTime(0, 1, "01:00");
        ArrayList arrayList = dal.getDb();
        System.out.println("!" + arrayList.toString());

        dal.addTime(0, 1, "02:00");
        arrayList = dal.getDb();
        System.out.println("!" + arrayList.toString());

        dal.addTime(0, 2, "10:00");
        arrayList = dal.getDb();
        System.out.println("!" + arrayList.toString());

        dal.addTime(0, 2, "11:00");
        arrayList = dal.getDb();
        System.out.println("!" + arrayList.toString());

        dal.addTime(1, 2, "20:00");
        arrayList = dal.getDb();
        System.out.println("!" + arrayList.toString());

        dal.addTime(13, 1, "30:00");
        arrayList = dal.getDb();
        System.out.println("!" + arrayList.toString());


        FrameLayout container = (FrameLayout)findViewById(R.id.fragment);

        fragment = new GameFragment();
        getSupportFragmentManager().beginTransaction().replace(container.getId() , fragment , "TAG").addToBackStack(null).commit();


        bundle = getIntent().getExtras();

        Log.d(TAG,"@@@mainActivity");

        // get complexity and level
        if(bundle!=null) {

            level = bundle.getString(Rxx);
            complexity = bundle.getString(Ryy);


            System.out.println("! level = " + level);
            System.out.println("! complexity = " + complexity);
        }

        // start button
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!gameOn)
                {
                    System.out.println("! in start");
                    stopWatch.start();
                    gameOn = true;
                    text_recent.setText(CURRENT);
                    /*AsyncTask as = new AsyncTask() {
                        @Override
                        protected void onProgressUpdate(Object[] values) {
                            time2_text.setText(formatSSMM());
                        }

                        @Override
                        protected String doInBackground(Object[] params) {
                            try {
                                Thread.sleep(10, 10);

                            }
                            catch (Exception e){

                            }
                            return null;
                        }
                    };
                    as.execute();*/

                    time2_text.setText(formatSSMM());
                }
            }
        });


        // red button
        red_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                System.out.println("! in red button");
                if(gameOn){
                    counterPress++;
                    System.out.println("!!!!" + counterPress);

                    if(counterPress == Integer.parseInt(level) ) {
                        stopWatch.stop();
                        gameOn = false;
                        time2_text.setText(formatSSMM());
                        text_recent.setText(RECENT);
                        counterPress = 0 ;

                        // record time
                        int index = (time1_text.getText().toString()).indexOf(":");
                        int s1 = Integer.parseInt((time1_text.getText().toString()).substring(0, index));
                        int m1 = Integer.parseInt((time1_text.getText().toString()).substring(index + 1, time1_text.getText().length()));

                        // new time
                        index = time2_text.getText().toString().indexOf(":");
                        int s2 = Integer.parseInt((time2_text.getText().toString()).substring(0, index));
                        int m2 = Integer.parseInt((time2_text.getText().toString()).substring(index + 1, time2_text.getText().length()));

                        // check if is new record
                        if (s2 < s1 || (s2 == s1 && m2 < m1) || (s1 == 0 && m1 == 0)) {
                            time1_text.setText(time2_text.getText().toString());
                        }
                    }
                }
            }
        });

        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to settings activity
                if(!gameOn) {
                    System.out.println("! in setting");
                    if(!best_presed) {
                        intent = new Intent(v.getContext(), settings.class);
                    }
                    startActivity(intent);
                }


            }
        });


        text_best.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOn) {
                    time1_text.setText(str_time1);
                    intent = new Intent(v.getContext(), settings.class);
                    intent.putExtra(key_xx, str_xx);
                    intent.putExtra(key_yy, str_yy);
                    best_presed=true;
                }
            }
        });




    }




    private void saveData(SharedPreferences sharedPreferences){
        // create Editor
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // add data
        editor.putString(text_time1_key, time1_text.getText().toString());
        editor.putString(text_time2_key, time2_text.getText().toString());

        // save data
        editor.apply();
    }


    @Override
    protected void onResume() {
        super.onResume();

        // check if there is a data
        if(sharedPreferences != null)
        {
            // restore data
            time1_text.setText(sharedPreferences.getString(text_time1_key ,""));
            time2_text.setText(sharedPreferences.getString(text_time2_key ,""));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();


        saveData(sharedPreferences);
    }

    @Override
    protected void onPause(){
        super.onPause();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


/*******************************************************************/

}
