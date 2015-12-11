package com.example.administrator.ex02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    static final String str_xx ="xx";

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(new MyView(this));
        //init views
        strat_button = (Button) findViewById(R.id.button_start);
        setting_button = (Button) findViewById(R.id.button_sttings);
        red_butoon = (Button) findViewById(R.id.button_red);
        time1_text = (TextView) findViewById(R.id.text_time1);
        time2_text = (TextView) findViewById(R.id.text_time2);
        text_recent = (TextView) findViewById(R.id.textView_resnt);
        text_best = (TextView) findViewById(R.id.textView_best);


        // create shared pref...
        sharedPreferences = getPreferences(MODE_PRIVATE);


        strat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inPress = true;
                text_recent.setText("Current time");



            }
        });

        red_butoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to settings activity
                intent = new Intent(v.getContext(), settings.class);
                startActivity(intent);
            }
        });


        text_best.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time1_text.setText("ss:mmm");
                intent.putExtra(key_xx,str_xx);
                //// TODO: clear XX and YY
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
