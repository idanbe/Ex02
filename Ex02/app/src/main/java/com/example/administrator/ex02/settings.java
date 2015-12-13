package com.example.administrator.ex02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class settings extends AppCompatActivity {

    //views
    private EditText text_xx,text_yy;
    SharedPreferences sharedPreferences;
    static final String text_xx_key ="key1";
    static final String text_yy_key ="key2";
    static final String Rxx ="key3";
    static final String Ryy ="key4";
    static final String TAG ="debug";
    private   Bundle bundle;
    private String bundle_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //init views
        text_xx = (EditText) findViewById(R.id.text_xx);
        text_yy = (EditText) findViewById(R.id.text_yy);


        // create shared pref...
        sharedPreferences = getPreferences(MODE_PRIVATE);

        bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            bundle_value = bundle.getString(Rxx);
            text_xx.setHint(bundle_value);
            bundle_value = bundle.getString(Ryy);
            text_yy.setHint(bundle_value);
            //getIntent().removeExtra(Rxx);
            //getIntent().removeExtra(Ryy);
        }


        //limit input
        text_xx.setFilters(new InputFilter[]{new InputFilterMinMax("1", "10")});
        text_yy.setFilters(new InputFilter[]{new InputFilterMinMax("0", "4")});


    }

    private void saveData(SharedPreferences sharedPreferences){
        // create aditor
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // add data
        editor.putString(text_xx_key, text_xx.getText().toString());
        editor.putString(text_yy_key, text_yy.getText().toString());

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
            if(bundle==null)
            {
                text_xx.setText(sharedPreferences.getString(text_xx_key, ""));
                text_yy.setText(sharedPreferences.getString(text_yy_key ,""));
            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData(sharedPreferences);

        Log.d(TAG, "@@@@@onStop ");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onPause(){
        super.onPause();
    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
