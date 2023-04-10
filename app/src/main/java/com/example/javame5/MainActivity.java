package com.example.javame5;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    MyTask mt;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("qwe", "create MainActivity: " + this.hashCode());

        tv = findViewById(R.id.tv);
        mt = (MyTask) getLastNonConfigurationInstance();

        if (mt == null) {
            mt = new MyTask();
            mt.execute();
        }
        mt.link(this);
        Log.d("qwe", "create MyTask: " + mt.hashCode());
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        mt.unLink();
        return mt;
    }

    class MyTask extends AsyncTask<String, Integer, Void> {

        MainActivity activity;

        @Override
        protected Void doInBackground(String... params) {
            try {
                for (int i = 1; i <= 10; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    publishProgress(i);
                    Log.d("qwe", "i = " + i
                            + ", MyTask: " + this.hashCode()
                            + ", MainActivity: " + MainActivity.this.hashCode());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tv.setText("i = " + values[0]);
        }

        void link(MainActivity act) {
            activity = act;
        }

        void unLink() {
            activity = null;
        }
    }
}