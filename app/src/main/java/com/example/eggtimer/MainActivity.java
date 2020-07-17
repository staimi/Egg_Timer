package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView textView, editTextTime;
    Button button, resetButton;
    SeekBar seekBar;
    CountDownTimer countDownTimer;
    long boilingTime;
    int maxTime = 10;
    int progressTime = 7;
    MediaPlayer alarm;

    public void updateTimer(){
        int minutes = (int) boilingTime/60000;
        int seconds = (int) boilingTime %60000 / 1000;
        String timeLeft;

        timeLeft = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        /*timeLeft = ""+minutes;
        timeLeft += ":";
        if(seconds < 10) timeLeft +=0;
        timeLeft += seconds;*/
        editTextTime.setText(timeLeft);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        editTextTime = findViewById(R.id.editTextTime);
        button = findViewById(R.id.button);
        resetButton = findViewById(R.id.resetButton);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(maxTime);
        seekBar.setProgress(progressTime);
        alarm = MediaPlayer.create(MainActivity.this,R.raw.alarm);
        updateTimer();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("Progress", " "+ i);
                boilingTime = i * 60000;
                if(i < 3){
                    textView.setText("Min: "+i+", (Soft)");
                }
                else if(i >3 && i <8){
                    textView.setText("Min: "+ i +", (Medium)");
                }
                else if(i>=8){
                    textView.setText("Min: "+i+", (Hard)");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer =new CountDownTimer(boilingTime, 1000) {
                    @Override
                    public void onTick(long l) {
                        boilingTime = l;
                        updateTimer();
                        button.setClickable(false);
                    }

                    @Override
                    public void onFinish() {
                        alarm.setLooping(true);
                        alarm.start();
                    }
                }.start();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextTime.setText("00:00");
                boilingTime = seekBar.getProgress() * 60000;
                countDownTimer.cancel();
                button.setClickable(true);
                alarm.pause();
                alarm.seekTo(0);
            }
        });

    }
}