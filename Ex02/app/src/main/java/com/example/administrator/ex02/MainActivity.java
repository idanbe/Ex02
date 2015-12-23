package com.example.administrator.ex02;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //views

    private TextView text_recent, text_best, time1_text, time2_text;
    SharedPreferences sharedPreferences;
    static final String text_time1_key = "key1";     // key for shardepre..
    static final String text_time2_key = "key2";
    static final String key_xx = "key3";
    static final String key_yy = "key4";
    static final String Rxx = "key5";
    static final String Ryy = "key6";
    static final String str_xx = "XX";
    static final String str_yy = "YY";
    static final String RECENT = "Recent result";
    static final String CURRENT = "Current time";
    static final String str_time1 = "00:00";
    static String level = "1";
    static String complexity = "0";
    private final String TAG = getClass().getSimpleName();
    private int counterPress = 0;
    private Intent intent;
    private Bundle bundle;
    private boolean best_presed = false;
    private Boolean gameOn = false;
    private AppEntryTimeDAL dal;
    public long startTime = 0;
    public long stopTime = 0;
    final int REFRESH = 10;
    private CostomView cv;
    View view ;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            stopTime = System.currentTimeMillis() ;
            long timeInMilli = stopTime - startTime;
            int seconds = ((int)(timeInMilli/1000))%60;

            time2_text.setText(String.format("%02d:%02d", seconds, timeInMilli % 100));
            timerHandler.postDelayed( this , REFRESH);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start_button, setting_button;

        start_button = (Button) findViewById(R.id.button_start);
        setting_button = (Button) findViewById(R.id.button_sttings);
        time1_text = (TextView) findViewById(R.id.text_time1);
        time2_text = (TextView) findViewById(R.id.text_time2);
        text_recent = (TextView) findViewById(R.id.textView_resnt);
        text_best = (TextView) findViewById(R.id.textView_best);
        view = findViewById(R.id.view);
        cv = new CostomView(this);
        best_presed = false;


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


        bundle = getIntent().getExtras();

        Log.d(TAG, "@@@mainActivity");

        // get complexity and level
        if (bundle != null) {

            level = bundle.getString(Rxx);
            complexity = bundle.getString(Ryy);


            System.out.println("! level = " + level);
            System.out.println("! complexity = " + complexity);
        }
        cv.set_complexty(complexity);


        // start button
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameOn) {
                    System.out.println("! in start");
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    gameOn = true;
                    text_recent.setText(CURRENT);
                }
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (gameOn && view.onTouchEvent(event)) {
                    counterPress++;

                    if (counterPress == Integer.parseInt(level)) {
                        gameOn = false;
                        text_recent.setText(RECENT);
                        System.out.println("!! press = " + counterPress);
                        counterPress = 0;
                        timerHandler.removeCallbacks(timerRunnable);


                        // record time
                        int index;
                        try {
                            index = (time1_text.getText().toString()).indexOf(":");
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
                        catch (Exception e){
                            time1_text.setText(time2_text.getText().toString());
                        }


                    }
                }
                return false;
            }
        });

        
        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to settings activity
                if (!gameOn) {
                    System.out.println("! in setting");
                    if (!best_presed) {
                        intent = new Intent(v.getContext(), settings.class);
                    }
                    startActivity(intent);
                }


            }
        });


        text_best.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameOn) {
                    time1_text.setText(str_time1);
                    intent = new Intent(v.getContext(), settings.class);
                    intent.putExtra(key_xx, str_xx);
                    intent.putExtra(key_yy, str_yy);
                    best_presed = true;
                }
            }
        });


    }


    private void saveData(SharedPreferences sharedPreferences) {
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
        if (sharedPreferences != null) {
            // restore data
            time1_text.setText(sharedPreferences.getString(text_time1_key, ""));
            time2_text.setText(sharedPreferences.getString(text_time2_key, ""));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();


        saveData(sharedPreferences);
    }

    @Override
    protected void onPause() {
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
}