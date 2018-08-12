package com.compilado.johnm.citamedica2.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.compilado.johnm.citamedica2.R;

public class Cargando {

    private AlertDialog  alerta;
    private Context context;


    public Cargando(Context context) {
        this.context = context;
    }

    public void mostrarCargando() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Cargando");
        View item = LayoutInflater.from(context).inflate(R.layout.item_cargando, null);
        builder.setView(item);
        builder.setCancelable(false);
        alerta = builder.create();
        alerta.show();
    }

    public void ocultarCargando() {
        if (alerta != null) {
            alerta.cancel();
        }
    }

}
