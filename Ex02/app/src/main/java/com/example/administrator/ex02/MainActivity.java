package com.example.administrator.ex02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncStatusObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   //views
    private Button strat_button,setting_button,red_butoon;
    private TextView text_recent,text_best, time1_text,time2_text;
    private boolean inPress  =false;
    private boolean best_press =false;
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
    static final String str_time1= "00:000";
    private  String level;
    private  String comlexity;
    private final String TAG = getClass().getSimpleName();
    private  int counterPress=0;
    private Intent intent;
    private  Bundle bundle;

    AppEntryTimeDAL dal;

    private String formatSSMM(){
        String s = "" ;
        s = Integer.toString((int)stopWatch.getTimeSecs());
        s += ":";
        s +=Integer.toString((int)stopWatch.getTimeMilli() % 1000 );
        return s ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        strat_button = (Button) findViewById(R.id.button_start);
        setting_button = (Button) findViewById(R.id.button_sttings);
        red_butoon = (Button) findViewById(R.id.button_red);
        time1_text = (TextView) findViewById(R.id.text_time1);
        time2_text = (TextView) findViewById(R.id.text_time2);
        text_recent = (TextView) findViewById(R.id.textView_resnt);
        text_best = (TextView) findViewById(R.id.textView_best);
        best_press=false;



        // create shared pref...
        sharedPreferences = getPreferences(MODE_PRIVATE);

        // create dal
        dal = new AppEntryTimeDAL(this);

        bundle = getIntent().getExtras();

        Log.d(TAG,"@@@mainActivity");

        if(bundle!=null)
        {

            level  = bundle.getString(Rxx);
            comlexity =  bundle.getString(Ryy);
            if (level.contains(""))
            {
                level="1";
            }
            if (comlexity.contains(""))
            {
                comlexity="0";
            }
            Log.d(TAG,"@@@_xx:"+ level.toString() );
            Log.d(TAG, "@@@_yy:" + comlexity.toString());

        }

        strat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!inPress)
                {
                    stopWatch.start();
                    setting_button.setEnabled(false);
                    inPress = true;
                    text_recent.setText("Current time");
                    time1_text.setText(formatSSMM());
                }


            }
        });

        red_butoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    int t1,t2;

                    if(inPress)
                    {
                        setting_button.setEnabled(true);
                        counterPress++;
                        stopWatch.stop();//todo stop when prees n time on red (n = level)
                        time2_text.setText(formatSSMM());
                        inPress=false;
                        text_recent.setText(RECENT);

                        // get time

                        System.out.println("!!!before");
                        int index = (time1_text.getText().toString()).indexOf(":");
                        System.out.println("!!!"+index);
                        int s1 = Integer.parseInt((time1_text.getText().toString()).substring(0, index));


                        index = time1_text.getText().toString().indexOf(":");
                        System.out.println("!!!"+index);
                        int s2 = Integer.parseInt((time2_text.getText().toString()).substring(0 , index ));

                        int m1 = Integer.parseInt((time1_text.getText().toString()).substring(index + 1, time1_text.getText().length()));
                        int m2 = Integer.parseInt((time2_text.getText().toString()).substring(index + 1, time2_text.getText().length()));

                        if (s2 > s1 || (s2==s1 && m2 > m1)){
                            time1_text.setText(time2_text.getText().toString());
                            //appEntryTimeDAL = new AppEntryTimeDAL(this ,Integer.parseInt(level) , comlexity );
                        }

                    }
            }
        });

        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to settings activity
                if(!best_press) {
                    intent = new Intent(v.getContext(), settings.class);
        //            intent.removeExtra(key_xx);
          //          intent.removeExtra(key_yy);

                    Log.d(TAG, "@@@@@@@@@@@@@@@ ");
                }
                startActivity(intent);

            }
        });


        text_best.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 time1_text.setText(str_time1);
                 intent = new Intent(v.getContext(), settings.class);
                 intent.putExtra(key_xx,str_xx);
                 intent.putExtra(key_yy,str_yy);
                 best_press=true;
            }
        });




    }






    private void saveData(SharedPreferences sharedPreferences){
        // create aditor
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


}
