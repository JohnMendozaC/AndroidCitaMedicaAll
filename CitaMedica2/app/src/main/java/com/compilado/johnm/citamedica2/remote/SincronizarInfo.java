package com.compilado.johnm.citamedica2.remote;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.compilado.johnm.citamedica2.persistence.localdatasource.HistorialPacienteDataSource;
import com.compilado.johnm.citamedica2.persistence.localdatasource.MedicoDataSource;
import com.compilado.johnm.citamedica2.persistence.localdatasource.PacienteDataSource;
import com.compilado.johnm.citamedica2.persistence.vo.HistorialPaciente;
import com.compilado.johnm.citamedica2.persistence.vo.Medico;
import com.compilado.johnm.citamedica2.persistence.vo.Paciente;
import com.compilado.johnm.citamedica2.remote.pojo.Citas;
import com.compilado.johnm.citamedica2.ui.injection.Injection;
import com.compilado.johnm.citamedica2.view.Cargando;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SincronizarInfo {

    private static final String TAG = "retrofitCita";
    //Database
    private Injection injection;
    private PacienteDataSource pacienteDao;

    //HistorialPaciente
    private HistorialPacienteDataSource pacienteHDao;
    //Medico
    private MedicoDataSource medicoDao;


    //Datos Locales
    List<Paciente> datosPaciente;
    List<Medico> datosMedico;
    List<HistorialPaciente> datosHistorial;

    //Retrofit
    private APIServiceC mAPIService;
    private Citas citas;

    //Tiempo de sincronizacion
    private Cargando cargando;

    //Contexto
    private Context context;
    private Boolean now;

    public SincronizarInfo(Context context, Boolean now) {

        this.context = context;
        //Init
        mAPIService = ApiUtilsC.getAPIService();

        injection = new Injection();

        pacienteDao = injection.providePacienteDataSource(context);
        pacienteHDao = injection.provideHistorialPacienteDataSource(context);
        medicoDao = injection.provideMedicoDataSource(context);

        //List
        datosPaciente = new ArrayList<>();
        datosMedico = new ArrayList<>();
        datosHistorial = new ArrayList<>();
        this.now = now;
        //La carga se realizara ahora true
        if (now) {
            cargando = new Cargando(context);
        }
        descargarLocal();
        citas = new Citas(datosMedico, datosPaciente, datosHistorial);

    }

    public void descargarLocal() {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                datosHistorial.addAll(pacienteHDao.getAllHistorialPacientesRetro());
                datosPaciente.addAll(pacienteDao.getAllPacientesRetro());
                datosMedico.addAll(medicoDao.getAllMedicosRetro());
                emitter.onComplete();

            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        //  Toast.makeText(MedicoActivity.this, "Agrego Medico", Toast.LENGTH_SHORT).show();
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //Refresh data
                    }
                });

    }

    public void subirServidor() {
        if (now) {
            cargando.mostrarCargando();
        }
        //Subir Datos de medico
        if (citas != null) {
            mAPIService.uploadData(citas).enqueue(new Callback<Citas>() {
                @Override
                public void onResponse(Call<Citas> call, Response<Citas> response) {
                    if (response.message().equals("OK")) {
                        Toast.makeText(context, "Subio correctamente la info", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onResponse: " + response.message());
                        if (now) {
                            cargando.ocultarCargando();
                        }
                    } else {
                        Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onResponse: " + response.message());
                        if (now) {
                            cargando.ocultarCargando();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Citas> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(context, "No hay datos", Toast.LENGTH_SHORT).show();
        }

    }


}
