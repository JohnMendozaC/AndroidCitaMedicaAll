package com.compilado.johnm.citamedica2.remote;

import android.app.Activity;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.compilado.johnm.citamedica2.view.SincronizarActivity;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TiempoSincronizacion extends BroadcastReceiver implements Runnable {

    //Tiempo de sincronizacion
    private SincronizarInfo sincronizar;

    //Sincronizar

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        //Time
        this.context = (Context) context;
        sincronizar = new SincronizarInfo(this.context,false);
        Toast.makeText(context, "Cargando info para subir", Toast.LENGTH_SHORT).show();

        new Thread(this).start();

        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void run() {
        try {

            sincronizar.subirServidor();
        } catch (Exception e) {
            Log.e("TimeSin", "run: ", e);
        }

    }




}
