package com.eccelor.darshakcomputer.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


import com.eccelor.darshakcomputer.ConversationActivity;
import com.eccelor.darshakcomputer.DashboardActivity;
import com.eccelor.darshakcomputer.R;
import com.eccelor.darshakcomputer.helper.Helper;
import com.eccelor.darshakcomputer.model.Ticket;
import com.eccelor.darshakcomputer.model.User;

import com.eccelor.darshakcomputer.networking.DCService;
import com.eccelor.darshakcomputer.networking.RetrofitInterceptor;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFirebaseService extends FirebaseMessagingService
{
    private static final int MSG_NOTIFICATION_ID = 110088;
    private NotificationManager notificationManager;
    private User user;
    private String conId = null;
    private String ticketId = null;

    @Override
    public void onCreate()
    {
        super.onCreate();

        user = Helper.getUserContext(MyFirebaseService.this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);

        Log.e("dc_msg", remoteMessage.getData().toString());
        JSONObject jsonObject = null;
        try
        {
            jsonObject = new JSONObject(remoteMessage.getData().toString());
            conId = jsonObject.getString("CONVERSATION_ID");
            ticketId = jsonObject.getString("TICKET_ID");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if (ticketId != null)
            getTicketId(ticketId);

    }

    private void sendMessageNotification(Ticket ticket)
    {
        Intent intent = new Intent(getApplicationContext(), BackgroundService.class);
        intent.putExtra("checkMsg", 2);
        intent.putExtra("conId", conId);

        if (ConversationActivity.checkState == false)
            messageNotification(ticket.publicId, ticket.conversation.messages.get(0).message, ticket);

        startService(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void makeNotificationChannel(String id, String name, int importance)
    {
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setShowBadge(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }

    private void messageNotification(String ticketId, String message, Ticket ticket)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            makeNotificationChannel("CHANNEL_2", getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
        }

        Intent messageIntent = new Intent(this, ConversationActivity.class);
        messageIntent.putExtra("ticket", ticket);
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0, messageIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "CHANNEL_2");

        notification.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(ticketId + " : " + message)
                .setContentIntent(activityPendingIntent)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(new long[]{1000, 1000})
                .setWhen(System.currentTimeMillis());


        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        assert notificationManager != null;

        notificationManager.notify(MSG_NOTIFICATION_ID, notification.build());
    }

    private void getTicketId(String ticketId)
    {
        DCService service = RetrofitInterceptor.getClient().create(DCService.class);
        Call<String> call = service.getTicketId(ticketId);
        call.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if (response.isSuccessful())
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response.body());
                        Ticket ticket = new Ticket();
                        ticket.fromJson(jsonObject);

                        sendMessageNotification(ticket);

                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Log.e("error_code", response.code() + "");
                    try
                    {
                        Log.e("error_msg", response.errorBody().string());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                Log.e("failed", t.getMessage());
            }
        });
    }

}
