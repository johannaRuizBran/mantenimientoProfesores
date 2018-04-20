package com.example.joha.mantenimiento.Firebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.joha.mantenimiento.Firebase.MyService;



public class RebootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, MyService.class));
    }
}