package com.eccelor.dc.testdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity
{

    private static final int sec = 1500;
    private boolean flag = false;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (flag == false)
                {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, sec);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        flag = true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        flag = false;
    }
}
