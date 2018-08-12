package com.compilado.johnm.citamedica2.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.TextView;
import android.widget.Toast;

import com.compilado.johnm.citamedica2.R;
import com.compilado.johnm.citamedica2.persistence.vo.Medico;
import com.compilado.johnm.citamedica2.ui.injection.Injection;
import com.compilado.johnm.citamedica2.ui.viewmodel.HistorialPacienteViewModel;
import com.compilado.johnm.citamedica2.ui.viewmodel.MedicoViewModel;
import com.compilado.johnm.citamedica2.ui.viewmodelfactory.HistorialPacienteVMFactory;
import com.compilado.johnm.citamedica2.ui.viewmodelfactory.MedicoVMFactory;

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

public class MedicoActivity extends AppCompatActivity {

    private static final String TAG = "CitaMedica";
    private android.support.design.widget.FloatingActionButton fbAddMedico;
    private android.widget.ListView lvMedico;

    //Datos Medico
    private AlertDialog alertDialog;

    //Adapter
    List<Medico> listMedico;
    ArrayAdapter adapter;

    //Database
    private CompositeDisposable compositeDisposable;
    private MedicoVMFactory medicoVMFactory;
    private MedicoViewModel medicoViewModel;
    private HistorialPacienteVMFactory pacienteHVMFactory;
    private HistorialPacienteViewModel pacienteHViewModel;
    private EditText etTarjeta;
    private EditText etEspeciliadad;
    private EditText etAnioExpe;
    private EditText etConsultorio;
    private android.widget.CheckBox cbAtencionDomicilio;
    private Button btnMedico;
    private Button btnCancelarM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico);
        this.lvMedico = (ListView) findViewById(R.id.lvMedico);
        this.fbAddMedico = (FloatingActionButton) findViewById(R.id.fbAddMedico);

        //Init
        listMedico = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
        medicoVMFactory = Injection.provideMedicoViewModelFactory(this);
        medicoViewModel = ViewModelProviders.of(this, medicoVMFactory)
                .get(MedicoViewModel.class);
        pacienteHVMFactory = Injection.provideHistorialPacienteViewModelFactory(this);
        pacienteHViewModel = ViewModelProviders.of(this, pacienteHVMFactory)
                .get(HistorialPacienteViewModel.class);


        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listMedico);
        registerForContextMenu(lvMedico);
        lvMedico.setAdapter(adapter);


        loadData();

        //Event
        fbAddMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add new Medico

                Medico medico = new Medico();
                try {
                    createCustomDialog(medico, 1).show();
                } catch (InflateException e) {
                    Log.e(TAG, "subscribe: ", e);
                    e.printStackTrace();
                }

            }
        });

    }

    private void loadData() {
        //Use liveData
        medicoViewModel.getAllmedicos()
                .observe(this, new Observer<List<Medico>>() {
                    @Override
                    public void onChanged(@Nullable final List<Medico> medicos) {
                        // Update the cached copy of the words in the adapter.
                        OnGetAllMedicoSuccess(medicos);
                        Collections.shuffle(listMedico);
                    }
                });
    }

    private void OnGetAllMedicoSuccess(List<Medico> medicos) {
        listMedico.clear();
        listMedico.addAll(medicos);
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
                medicoViewModel.deleteAllMedicos();
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
                        Toast.makeText(MedicoActivity.this, "Tiene citas pendientes", Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Toast.makeText(MedicoActivity.this, "Se elimino con exito", Toast.LENGTH_SHORT).show();
                        loadData(); //Refresh data
                    }
                });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("Seleccione accion:");

        menu.add(Menu.NONE, 0, Menu.NONE, "Actualizar");
        menu.add(Menu.NONE, 1, Menu.NONE, "Eliminar");
        menu.add(Menu.NONE, 2, Menu.NONE, "Historial Medico");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Medico medico = listMedico.get(info.position);

        switch (item.getItemId()) {
            case 0: {
                try {
                    createCustomDialog(medico, 0).show();
                } catch (InflateException e) {
                    Log.e(TAG, "onContextItemSelected: ", e);
                    e.printStackTrace();
                }
            }
            break;

            case 1: {
                new AlertDialog.Builder(MedicoActivity.this)
                        .setMessage("Quieres Eliminar los datos, Tambien se eliminaran historial\n"
                                + medico.toString())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteMedico(medico);
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
            break;
            case 2: {
                Intent historial = new Intent(MedicoActivity.this, HistorialPacienteActivity.class);
                historial.putExtra("idpaciente", 0L);
                historial.putExtra("idmedico", medico.getIdmedico());
                historial.putExtra("nombre", "Tarjeta:" + medico.getCod_tarjetapro()
                        + "-Consultorio:" + medico.getConsultorio());
                startActivity(historial);
            }
            break;

        }
        return true;
    }

    private void deleteMedico(final Medico medico) {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {

                if (pacienteHViewModel.getCitasAsignadas(medico.getIdmedico()) == 0) {
                    pacienteHViewModel.deleteHistorialMedico(medico.getIdmedico());
                    medicoViewModel.deleteMedico(medico);
                }
                emitter.onComplete();

            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(MedicoActivity.this, "Agrego Medico", Toast.LENGTH_SHORT).show();
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MedicoActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        loadData(); //Refresh data
                    }
                });
        compositeDisposable.add(disposable);
    }


    private void updateMedico(final Medico medico) {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                medicoViewModel.updateMedico(medico);
                emitter.onComplete();

            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MedicoActivity.this, "No se pudo Actualizar", Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Toast.makeText(MedicoActivity.this, "Se actualizo con exito", Toast.LENGTH_SHORT).show();
                        loadData(); //Refresh data
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public AlertDialog createCustomDialog(Medico medico, int Opcion) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // Inflar y establecer el layout para el dialogo
        // Pasar nulo como vista principal porque va en el diseño del diálogo
        View v = inflater.inflate(R.layout.registro_medico, null);
        this.btnCancelarM = (Button) v.findViewById(R.id.btnCancelarM);
        this.btnMedico = (Button) v.findViewById(R.id.btnMedico);
        this.cbAtencionDomicilio = (CheckBox) v.findViewById(R.id.cbAtencionDomicilio);
        this.etConsultorio = (EditText) v.findViewById(R.id.etConsultorio);
        this.etAnioExpe = (EditText) v.findViewById(R.id.etAnioExpe);
        this.etEspeciliadad = (EditText) v.findViewById(R.id.etEspeciliadad);
        this.etTarjeta = (EditText) v.findViewById(R.id.etTarjeta);

        if (Opcion == 0) {
            etTarjeta.setText(medico.getCod_tarjetapro());
            etEspeciliadad.setText(medico.getEspecialidad());
            etAnioExpe.setText(String.valueOf(medico.getAño_experiencia()));
            etConsultorio.setText(medico.getConsultorio());
            cbAtencionDomicilio.setChecked(medico.isAten_domicilio());
        }


        builder.setView(v);
        alertDialog = builder.create();
        // Add action buttons

        btnMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (Opcion) {
                    case 0:
                        if (TextUtils.isEmpty(etTarjeta.getText().toString())) {
                            return;
                        } else {
                            medico.setCod_tarjetapro(etTarjeta.getText().toString());
                            medico.setEspecialidad(etEspeciliadad.getText().toString());
                            medico.setAño_experiencia(Float.parseFloat(etAnioExpe.getText().toString()));
                            medico.setConsultorio(etConsultorio.getText().toString());
                            medico.setAten_domicilio(cbAtencionDomicilio.isChecked());
                            updateMedico(medico);
                            alertDialog.dismiss();
                        }
                        break;
                    case 1:
                        if (TextUtils.isEmpty(etTarjeta.getText().toString())) {
                            return;
                        } else {
                            Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {

                                @Override
                                public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                                    medico.setCod_tarjetapro(etTarjeta.getText().toString());
                                    medico.setEspecialidad(etEspeciliadad.getText().toString());
                                    medico.setAño_experiencia(Float.parseFloat(etAnioExpe.getText().toString()));
                                    medico.setConsultorio(etConsultorio.getText().toString());
                                    medico.setAten_domicilio(cbAtencionDomicilio.isChecked());
                                    listMedico.add(medico);
                                    medicoViewModel.insertMedico(medico);
                                    alertDialog.dismiss();
                                    emitter.onComplete();
                                }
                            })
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new Consumer() {
                                        @Override
                                        public void accept(Object o) throws Exception {
                                            Toast.makeText(MedicoActivity.this, "Agrego Medico", Toast.LENGTH_SHORT).show();
                                        }

                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            Toast.makeText(MedicoActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                            Log.e(TAG, "subscribe: ", throwable);
                                        }
                                    }, new Action() {
                                        @Override
                                        public void run() throws Exception {
                                            loadData(); //Refresh data
                                        }
                                    });
                        }
                        break;
                }

            }
        });

        //Boton para cerrar el cuadro de dialogo
        btnCancelarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        return alertDialog;
    }

}
