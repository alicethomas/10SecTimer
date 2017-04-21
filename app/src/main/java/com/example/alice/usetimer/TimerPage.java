package com.example.alice.usetimer;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class TimerPage extends AppCompatActivity {

    Button btnStart,btnStop,btnReset;
    TextView txtCounter;
    ProgressBar progBar;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();



    Timer timer;
    MyTimerTask myTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_page);
        btnStart = (Button)findViewById(R.id.RecordButton);
        btnStop = (Button)findViewById(R.id.StopButton);
        progBar= (ProgressBar)findViewById(R.id.progressBar);
        //btnReset=(Button)findViewById(R.id.ResetButton);

        txtCounter=(TextView)findViewById(R.id.textView);

        btnStart.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                if(timer!=null)
                    timer.cancel();
                while(mProgressStatus<100)
                {
                    mProgressStatus = doWork();

                    mHandler.post(new Runnable() {
                        public void run() {
                            progBar.setProgress(mProgressStatus);
                        }
                    });
                }
                timer = new Timer();
                myTimerTask= new MyTimerTask();
                timer.schedule(myTimerTask,10000);
                progBar.setVisibility(View.VISIBLE);
                txtCounter.setText("Timer in progress");
            }

        });
        btnStop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (timer != null)
                {
                    timer.cancel();
                    timer = null;
                    progBar.setVisibility(View.GONE);
                    txtCounter.setText("Recording Cancelled");
                }
            }
        });
    }

    private int doWork() {
        return 100;
    }

    class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            Calendar calendar= Calendar.getInstance();
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd:mm:yyyy HH:MM:SS a");
            final String strDate= simpleDateFormat.format(calendar.getTime());

            progBar.setVisibility(View.VISIBLE);
            runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    progBar.setVisibility(View.GONE);
                txtCounter.setText("10 seconds complete");
                }
            });

        }

    }}

