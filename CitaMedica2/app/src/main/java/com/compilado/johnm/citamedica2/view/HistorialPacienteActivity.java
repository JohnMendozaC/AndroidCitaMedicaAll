package com.compilado.johnm.citamedica2.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.compilado.johnm.citamedica2.R;
import com.compilado.johnm.citamedica2.persistence.vo.HistorialPaciente;
import com.compilado.johnm.citamedica2.ui.injection.Injection;
import com.compilado.johnm.citamedica2.ui.viewmodel.HistorialPacienteViewModel;
import com.compilado.johnm.citamedica2.ui.viewmodelfactory.HistorialPacienteVMFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HistorialPacienteActivity extends AppCompatActivity {

    private static final String TAG = "CitaMedica";
    //    private android.support.design.widget.FloatingActionButton fbAddHPaciente;
    private android.widget.ListView lvHPaciente;

    //Datos Medico
    private AlertDialog alertDialog;


    //primary key
    Long idmedico;
    Long idpaciente;
    Bundle parametros;
    String datos;

    //Adapter
    List<HistorialPaciente> listHPaciente;
    ArrayAdapter adapter;
    String[] items;

    //Database
    private CompositeDisposable compositeDisposable;
    private HistorialPacienteVMFactory pacienteHVMFactory;
    private HistorialPacienteViewModel pacienteHViewModel;
    private android.widget.TextView titleHistorial;
    private Spinner opMedico;
    private CheckBox cbIsTratamiento;
    private EditText etCuotaModeradora;
    private EditText cvFechaCita;
    private CheckBox cbAtencionCita;
    private Button btnCita;
//    private Button btnCancelarC;
    private TextView tvMedico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_paciente);
        this.titleHistorial = (TextView) findViewById(R.id.titleHistorial);
        this.lvHPaciente = (ListView) findViewById(R.id.lvHPaciente);
//        this.fbAddHPaciente = (FloatingActionButton) findViewById(R.id.fbAddHPaciente);
        parametros = this.getIntent().getExtras();
        idpaciente = parametros.getLong("idpaciente");
        idmedico = parametros.getLong("idmedico");
        datos = parametros.getString("nombre");

        //Init
        listHPaciente = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
        pacienteHVMFactory = Injection.provideHistorialPacienteViewModelFactory(this);
        pacienteHViewModel = ViewModelProviders.of(this, pacienteHVMFactory)
                .get(HistorialPacienteViewModel.class);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listHPaciente);
        registerForContextMenu(lvHPaciente);
        lvHPaciente.setAdapter(adapter);


        if (idpaciente == 0L && idmedico == 0L) {
            titleHistorial.setText("Historial Global");
            loadData();
        }

        if (idpaciente != 0L) {
            titleHistorial.setText("Historial Paciente: \n" + datos);
            loadDatapaciente();
        }

        if (idmedico != 0L) {
            titleHistorial.setText("Historial Medico: \n" + datos);
            loadDatamedico();
        }


    }

    private void loadData() {
        //Use liveData
        pacienteHViewModel.getAllHistorialPacientes()
                .observe(this, new Observer<List<HistorialPaciente>>() {
                    @Override
                    public void onChanged(@Nullable final List<HistorialPaciente> pacientes) {
                        // Update the cached copy of the words in the adapter.
                        OnGetAllMedicoSuccess(pacientes);
                        Collections.shuffle(listHPaciente);
                    }
                });


    }

    private void loadDatamedico() {
        pacienteHViewModel.getHistorialPacienteById(idmedico)
                .observe(this, new Observer<List<HistorialPaciente>>() {
                    @Override
                    public void onChanged(@Nullable final List<HistorialPaciente> pmedicos) {
                        // Update the cached copy of the words in the adapter.
                        OnGetAllMedicoSuccess(pmedicos);
                        Collections.shuffle(listHPaciente);
                    }
                });
    }

    private void loadDatapaciente() {
        pacienteHViewModel.getCitasPaciente(idpaciente)
                .observe(this, new Observer<List<HistorialPaciente>>() {
                    @Override
                    public void onChanged(@Nullable final List<HistorialPaciente> pacientes) {
                        // Update the cached copy of the words in the adapter.
                        OnGetAllMedicoSuccess(pacientes);
                        Collections.shuffle(listHPaciente);
                    }
                });
    }


    private void OnGetAllMedicoSuccess(List<HistorialPaciente> pacientes) {
        listHPaciente.clear();
        listHPaciente.addAll(pacientes);
        adapter.notifyDataSetChanged();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menuh, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menuclear:
//                deleteAllMedicos();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void deleteAllMedicos() {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                pacienteHViewModel.deleteAllHistorialPacientes();
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
                        Toast.makeText(HistorialPacienteActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //Refresh data
                        if (idpaciente == 0L && idmedico == 0L) {
                            titleHistorial.setText("Historial Global");
                            loadData();
                        }

                        if (idpaciente != 0L) {
                            titleHistorial.setText("Historial Paciente: \n" + datos);
                            loadDatapaciente();
                        }

                        if (idmedico != 0L) {
                            titleHistorial.setText("Historial Medico: \n" + datos);
                            loadDatamedico();
                        }
                    }
                });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("Seleccione accion:");

        menu.add(Menu.NONE, 0, Menu.NONE, "Atender Cita");
        menu.add(Menu.NONE, 1, Menu.NONE, "Eliminar Cita");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final HistorialPaciente paciente = listHPaciente.get(info.position);

        switch (item.getItemId()) {
            case 0: {
                try {
                    createCita(paciente).show();
                } catch (InflateException e) {
                    Log.e(TAG, "subscribe: ", e);
                    e.printStackTrace();
                }
            }
            break;

            case 1: {
                new AlertDialog.Builder(HistorialPacienteActivity.this)
                        .setMessage("Quieres Eliminar los datos" + paciente.toString())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteMedico(paciente);
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
            break;

        }
        return true;
    }

    private void deleteMedico(final HistorialPaciente paciente) {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                pacienteHViewModel.deleteHistorialPaciente(paciente);
                emitter.onComplete();

            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(HistorialPacienteActivity.this, "Agrego Medico", Toast.LENGTH_SHORT).show();
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(HistorialPacienteActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //Refresh data
                        if (idpaciente == 0L && idmedico == 0L) {
                            titleHistorial.setText("Historial Global");
                            loadData();
                        }

                        if (idpaciente != 0L) {
                            titleHistorial.setText("Historial Paciente: \n" + datos);
                            loadDatapaciente();
                        }

                        if (idmedico != 0L) {
                            titleHistorial.setText("Historial Medico: \n" + datos);
                            loadDatamedico();
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void updateMedico(final HistorialPaciente paciente) {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                pacienteHViewModel.updateHistorialPaciente(paciente);
                emitter.onComplete();

            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(HistorialPacienteActivity.this, "Agrego Medico", Toast.LENGTH_SHORT).show();
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(HistorialPacienteActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //Refresh data
                        if (idpaciente == 0L && idmedico == 0L) {
                            titleHistorial.setText("Historial Global");
                            loadData();
                        }

                        if (idpaciente != 0L) {
                            titleHistorial.setText("Historial Paciente: \n" + datos);
                            loadDatapaciente();
                        }

                        if (idmedico != 0L) {
                            titleHistorial.setText("Historial Medico: \n" + datos);
                            loadDatamedico();
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public AlertDialog createCita(HistorialPaciente citaDatos) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // Inflar y establecer el layout para el dialogo
        // Pasar nulo como vista principal porque va en el diseño del diálogo
        View v = inflater.inflate(R.layout.registro_cita, null);
        this.tvMedico = (TextView) v.findViewById(R.id.tvMedico);
//        this.btnCancelarC = (Button) v.findViewById(R.id.btnCancelarC);
        this.btnCita = (Button) v.findViewById(R.id.btnCita);
        this.cbAtencionCita = (CheckBox) v.findViewById(R.id.cbAtencionCita);
        this.cvFechaCita = (EditText) v.findViewById(R.id.cvFechaCita);
        this.etCuotaModeradora = (EditText) v.findViewById(R.id.etCuotaModeradora);
        this.cbIsTratamiento = (CheckBox) v.findViewById(R.id.cbIsTratamiento);
        this.opMedico = (Spinner) v.findViewById(R.id.opMedico);

        tvMedico.setText("Indenficacion Medico y Paciente");
        items = new String[]{"Medicoid: " + citaDatos.getIdmedicofk() + "- Pacienteid:" + citaDatos.getIdpacientefk()};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        opMedico.setAdapter(adapter);
        final String[] medico = {""};
        cbIsTratamiento.setChecked(citaDatos.isIs_tratamiento());
        etCuotaModeradora.setText(String.valueOf(citaDatos.getVal_cuotamod()));
        cvFechaCita.setText(citaDatos.getFyhnuevacita());

        opMedico.setEnabled(false);
        cbIsTratamiento.setEnabled(false);
        etCuotaModeradora.setEnabled(false);
        cvFechaCita.setEnabled(false);
        if (idmedico != 0L) {
            cbAtencionCita.setEnabled(true);
        }else{
            cbAtencionCita.setEnabled(false);
        }

        builder.setView(v);
        alertDialog = builder.create();
        // Add action buttons

        btnCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idmedico != 0L) {
                    Intent firmaCita = new Intent(HistorialPacienteActivity.this, FirmaActivity.class);
                    firmaCita.putExtra("cita", (Serializable) citaDatos);
                    startActivity(firmaCita);
                    alertDialog.dismiss();
                }else{
                    alertDialog.dismiss();
                }
            }
        });

        //Boton para cerrar el cuadro de dialogo
//        btnCancelarC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.cancel();
//            }
//        });

        return alertDialog;
    }


}
