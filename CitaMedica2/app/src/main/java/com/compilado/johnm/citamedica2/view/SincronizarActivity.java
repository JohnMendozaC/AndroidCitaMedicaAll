package com.compilado.johnm.citamedica2.view;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.compilado.johnm.citamedica2.R;
import com.compilado.johnm.citamedica2.remote.Config;
import com.compilado.johnm.citamedica2.remote.SincronizarInfo;
import com.compilado.johnm.citamedica2.remote.TiempoSincronizacion;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Timer;

public class SincronizarActivity extends AppCompatActivity {


    private static final String TAG = "retrofitCita";


    private android.widget.EditText etTiempoSin;
    private android.widget.Button btnSincronizar;
    private Button btnSincronizarNow;
    private android.widget.TimePicker timePickerTask;


    //Tiempo de sincronizacion

    private SincronizarInfo sincronizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincronizar);
        this.btnSincronizar = (Button) findViewById(R.id.btnSincronizar);
        this.btnSincronizarNow = (Button) findViewById(R.id.btnSincronizarNow);
        this.timePickerTask = findViewById(R.id.timePickerTask);

        sincronizar = new SincronizarInfo(this, true);

        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                btnSincronizar.setEnabled(false);
                Toast.makeText(SincronizarActivity.this,
                        "Se ha guardado con exito, la proxima sincronizacion!!", Toast.LENGTH_SHORT).show();
                createAlarm();
            }
        });

        btnSincronizarNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSincronizarNow.setEnabled(false);
                sincronizar.subirServidor();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createAlarm() {
        int hour = timePickerTask.getCurrentHour();
        int min = timePickerTask.getCurrentMinute();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, 0);
        startAlarm(c);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, TiempoSincronizacion.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }


}
