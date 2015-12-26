package com.example.administrator.ex02;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

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
    static final String RECENT = "Recent Result";
    static final String CURRENT = "Current Time";
    static final String str_init = "00:00";
    static final String RESTART_SETTING = "" ;
    static final String SEARCH_SETTING = ":" ;
    static final String RESTART_LEVEL = "1" ;
    static final String RESTART_COMPLEXITY = "0" ;
    static String level = RESTART_LEVEL;
    static String complexity = RESTART_COMPLEXITY;
    private int counterPress = 0;
    private final int ZERO = 0;
    private final int ONE = 1;
    private Intent intent;
    private Bundle bundle;
    private boolean best_pressed = false;
    private Boolean gameOn = false;
    private AppEntryTimeDAL dal;
    public long startTime = 0;
    public long stopTime = 0;
    final int REFRESH = 10;
    private CostomView cv ;
    private View view ;
    private final String str_format=  "%02d:%02d";
    private final int MILLI_IN_SECOND = 1000;
    private final int MODULO_MILLI = 100;


    // time run for text time 2
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            stopTime = System.currentTimeMillis() ;
            long timeInMilli = stopTime - startTime;
            int seconds = ((int)(timeInMilli/MILLI_IN_SECOND));
            // time in thread
            time2_text.setText(String.format(str_format, seconds, timeInMilli % MODULO_MILLI));
            timerHandler.postDelayed( this , REFRESH);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  set values
        Button start_button, setting_button;
        start_button = (Button) findViewById(R.id.button_start);
        setting_button = (Button) findViewById(R.id.button_sttings);
        time1_text = (TextView) findViewById(R.id.text_time1);
        time2_text = (TextView) findViewById(R.id.text_time2);
        text_recent = (TextView) findViewById(R.id.textView_resnt);
        text_best = (TextView) findViewById(R.id.textView_best);
        view = findViewById(R.id.view);
        best_pressed = false;


        // create shared pref...
        sharedPreferences = getPreferences(MODE_PRIVATE);

        // create dal
        dal = new AppEntryTimeDAL(this);

        /*// if you want to remove all DB , after remove mark this line again
        //dal.removeAll();
        you need to mark the function in AooEntryDal class too */

        // custom view
        cv = new CostomView(this);


        // get level and complexity from setting class
        bundle = getIntent().getExtras();

        // get complexity and level
        if (bundle != null) {

            level = bundle.getString(Rxx);
            complexity = bundle.getString(Ryy);

            // if user not enter parameters to level or complexity
            if(complexity.equals(RESTART_SETTING) ){
                complexity = RESTART_COMPLEXITY;
            }
            if(level.equals(RESTART_SETTING)){
                level = RESTART_LEVEL;
            }

            // set complexity number for draw circle
            cv.set_complexity(Integer.parseInt(complexity));

            // get record from DB and set in "best result "
            String s = dal.getRecord(Integer.parseInt(complexity), Integer.parseInt(level));
            time1_text.setText(s);

            // go to onDraw..
            cv.invalidate();
        }

        // start button
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameOn) {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, ZERO);
                    gameOn = true;
                    cv.setGameOn(true);
                    text_recent.setText(CURRENT);
                }
            }
        });

        // whem user press on view
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // only if game is on
                if (gameOn && view.onTouchEvent(event)) {
                    counterPress++;

                    v.invalidate(); //after press draw again in random location

                    // if game is finish
                    if (counterPress == Integer.parseInt(level)) {
                        gameOn = false;
                        cv.setGameOn(false);
                        text_recent.setText(RECENT);
                        counterPress = ZERO;
                        timerHandler.removeCallbacks(timerRunnable);

                        // record time
                        int index;
                        try {
                            // the old record
                            index = (time1_text.getText().toString()).indexOf(SEARCH_SETTING);
                            int s1 = Integer.parseInt((time1_text.getText().toString()).substring(ZERO, index));
                            int m1 = Integer.parseInt((time1_text.getText().toString()).substring(index + ONE, time1_text.getText().length()));

                            // new time
                            index = time2_text.getText().toString().indexOf(SEARCH_SETTING);
                            int s2 = Integer.parseInt((time2_text.getText().toString()).substring(ZERO, index));
                            int m2 = Integer.parseInt((time2_text.getText().toString()).substring(index + ONE, time2_text.getText().length()));

                            // check if is new record
                            if (s2 < s1 || (s2 == s1 && m2 < m1) || (s1 == ZERO && m1 == ZERO)) {
                                time1_text.setText(time2_text.getText().toString());
                                // save in DB
                                dal.addTime(Integer.parseInt(complexity), Integer.parseInt(level), time1_text.getText().toString() );
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

        // setting
        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to settings activity
                if (!gameOn) {
                    if (!best_pressed) {
                        intent = new Intent(v.getContext(), settings.class);
                    }
                    startActivity(intent);
                }
            }
        });

        // rest record
        text_best.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameOn) {
                    time1_text.setText(str_init);
                    intent = new Intent(v.getContext(), settings.class);
                    // reset level and complexity
                    intent.putExtra(key_xx, str_xx);
                    intent.putExtra(key_yy, str_yy);
                    best_pressed = true;
                    // remove from DB
                    dal.removeRow(Integer.parseInt(complexity) , Integer.parseInt(level));
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

            // restore record from DB
            String record = dal.getRecord(Integer.parseInt(complexity), Integer.parseInt(level)) ;
            time1_text.setText(record);

            // restore data from sharedPreferences
            time2_text.setText(sharedPreferences.getString(text_time2_key, RESTART_SETTING));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // save data
        saveData(sharedPreferences);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // restart game after press Home Button
        gameOn = false;
        cv.setGameOn(false);
        text_recent.setText(RECENT);
        counterPress = ZERO;
        timerHandler.removeCallbacks(timerRunnable);

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

            if (!gameOn) {
                if (!best_pressed) {
                    intent = new Intent(MainActivity.this, settings.class);
                }
                startActivity(intent);
            }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}