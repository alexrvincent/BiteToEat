package com.example.ai_danica.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ai_Danica on 2/17/2016.
 */
public class Timer extends AppCompatActivity {


    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private Button startB;
    public TextView text;
    private EditText userNum;
    private EditText minuteText;
    private long startTime = 30 * 1000;
    private final long interval = 1 * 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_layout);
        startB = (Button) this.findViewById(R.id.button);
        text = (TextView) this.findViewById(R.id.timer);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Setting the title and back button
        getSupportActionBar().setTitle("Timer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    /*
    When users click on the start timer, count down.
     */

    public void onClick(View v) {
        userNum = (EditText) findViewById(R.id.hours);
        long hours = Long.valueOf(userNum.getText().toString()) * 1000 * 60 * 60;
        minuteText = (EditText) findViewById(R.id.minutes);
        long minutes = Long.valueOf(minuteText.getText().toString()) * 60000;
        startTime = hours + minutes;

        //text.setText(text.getText() + String.valueOf(startTime / 1000));
        countDownTimer = new MyCountDownTimer(startTime, interval);
        if (!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;
            startB.setText("STOP");
        } else if (timerHasStarted) {
            countDownTimer.cancel();
            timerHasStarted = false;
            startB.setText("RESTART");
        }
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
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        //return true;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Class for the countdown timer
     */
    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            text.setText("Time's up!");
        }

        //Seeing the hours, minutes, and seconds of the timer.
        @Override
        public void onTick(long millisUntilFinished) {
            text.setText("" + String.format("  %d: %d: %d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
        }
    }


}