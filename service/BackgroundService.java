package com.eccelor.darshakcomputer.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class BackgroundService extends Service
{

    private MessageReceive receive;
    private MyBinder myBinder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        if (myBinder == null)
            myBinder = new MyBinder();
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (intent != null)
        {
            int status = intent.getIntExtra("checkMsg", 0);
            switch (status)
            {
                case 1:
                    Log.e("status", "service start");
                    break;
                case 2:
                    String id = intent.getStringExtra("conId");
                    Log.e("conId", id + " rec");
                    if (receive != null)
                        receive.receiveMessage(id);
                    Log.e("status", "1" + " msg receive");
                    break;
            }
        }
        return START_STICKY;
    }

    public class MyBinder extends Binder
    {
        public void getMessage(MessageReceive messageReceive)
        {
            receive = messageReceive;
        }
    }

    public interface MessageReceive
    {
        void receiveMessage(String conversationId);
    }
}
