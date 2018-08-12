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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.compilado.johnm.citamedica2.R;
import com.compilado.johnm.citamedica2.persistence.vo.HistorialPaciente;
import com.compilado.johnm.citamedica2.persistence.vo.Medico;
import com.compilado.johnm.citamedica2.persistence.vo.Paciente;
import com.compilado.johnm.citamedica2.ui.injection.Injection;
import com.compilado.johnm.citamedica2.ui.viewmodel.HistorialPacienteViewModel;
import com.compilado.johnm.citamedica2.ui.viewmodel.MedicoViewModel;
import com.compilado.johnm.citamedica2.ui.viewmodel.PacienteViewModel;
import com.compilado.johnm.citamedica2.ui.viewmodelfactory.HistorialPacienteVMFactory;
import com.compilado.johnm.citamedica2.ui.viewmodelfactory.MedicoVMFactory;
import com.compilado.johnm.citamedica2.ui.viewmodelfactory.PacienteVMFactory;

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

public class PacienteActivity extends AppCompatActivity {

    private static final String TAG = "CitaMedica";
    private android.support.design.widget.FloatingActionButton fbAddPaciente;
    private android.widget.ListView lvPaciente;

    //Datos Medico
    private AlertDialog alertDialog;


    //Adapter
    List<Paciente> listPaciente;
    ArrayAdapter adapter;

    //Database
    private CompositeDisposable compositeDisposable;
    private PacienteVMFactory pacienteVMFactory;
    private PacienteViewModel pacienteViewModel;
    //HistorialPaciente
    private HistorialPacienteVMFactory pacienteHVMFactory;
    private HistorialPacienteViewModel pacienteHViewModel;
    //Medico
    private MedicoVMFactory medicoVMFactory;
    private MedicoViewModel medicoViewModel;
    String[] items;


    private EditText etNombre;
    private EditText etApellido;
    private EditText etIdentificacion;
    private EditText cvFechaNacimiento;
    private Button btnPaciente;
    private Button btnCancelarP;
    private android.widget.CheckBox cbIsTratamiento;
    private EditText etCuotaModeradora;
    private android.widget.CheckBox cbAtencionCita;
    private Button btnCita;
    private Button btnCancelarC;

    private android.widget.Spinner opMedico;
    private android.widget.TextView tvMedico;
    private EditText cvFechaCita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
        this.lvPaciente = (ListView) findViewById(R.id.lvPaciente);
        this.fbAddPaciente = (FloatingActionButton) findViewById(R.id.fbAddPaciente);

        //Init
        listPaciente = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
        pacienteVMFactory = Injection.providePacienteViewModelFactory(this);
        pacienteViewModel = ViewModelProviders.of(this, pacienteVMFactory)
                .get(PacienteViewModel.class);

        //HistorialPaciente
        pacienteHVMFactory = Injection.provideHistorialPacienteViewModelFactory(this);
        pacienteHViewModel = ViewModelProviders.of(this, pacienteHVMFactory)
                .get(HistorialPacienteViewModel.class);

        //Medico
        medicoVMFactory = Injection.provideMedicoViewModelFactory(this);
        medicoViewModel = ViewModelProviders.of(this, medicoVMFactory)
                .get(MedicoViewModel.class);


        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listPaciente);
        registerForContextMenu(lvPaciente);
        lvPaciente.setAdapter(adapter);


        loadData();
        LoadMedicos();

        //Event
        fbAddPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add new Paciente
                Paciente paciente = new Paciente();
                try {

                    createCustomDialog(paciente, 1).show();

                } catch (InflateException e) {
                    Log.e(TAG, "subscribe: ", e);
                    e.printStackTrace();
                }
            }
        });

    }

    private void loadData() {
        //Use liveData
        pacienteViewModel.getAllPacientes()
                .observe(this, new Observer<List<Paciente>>() {
                    @Override
                    public void onChanged(@Nullable final List<Paciente> pacientes) {
                        // Update the cached copy of the words in the adapter.
                        OnGetAllMedicoSuccess(pacientes);
                        Collections.shuffle(listPaciente);
                    }
                });
    }


    private void OnGetAllMedicoSuccess(List<Paciente> pacientes) {
        listPaciente.clear();
        listPaciente.addAll(pacientes);
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
//                new AlertDialog.Builder(PacienteActivity.this)
//                        .setMessage("¿Seguro quieres eliminar todos los pacientes?")
//                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                deleteAllMedicos();
//                            }
//                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).create().show();
//
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void deleteAllMedicos() {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                pacienteViewModel.deleteAllPacientes();
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
                        Toast.makeText(PacienteActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
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
        menu.add(Menu.NONE, 2, Menu.NONE, "Asignar Cita");
        menu.add(Menu.NONE, 3, Menu.NONE, "Historial Citas Paciente");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Paciente paciente = listPaciente.get(info.position);

        switch (item.getItemId()) {
            case 0: {
                try {
                    createCustomDialog(paciente, 0).show();
                } catch (InflateException e) {
                    Log.e(TAG, "subscribe: ", e);
                    e.printStackTrace();
                }
            }
            break;

            case 1: {
                new AlertDialog.Builder(PacienteActivity.this)
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
            case 2: {
                try {
                    createCita(paciente).show();
                } catch (InflateException e) {
                    Log.e(TAG, "subscribe: ", e);
                    e.printStackTrace();
                }
            }
            break;
            case 3: {
                Intent historial = new Intent(PacienteActivity.this,HistorialPacienteActivity.class);
                historial.putExtra("idpaciente",paciente.getIdpaciente());
                historial.putExtra("idmedico",0l);
                historial.putExtra("nombre","Nombre: "+paciente.getNombre()
                        +"-Apellido:"+paciente.getApellidos());
                startActivity(historial);
            }
            break;

        }
        return true;
    }

    private void deleteMedico(final Paciente paciente) {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                pacienteViewModel.deletePaciente(paciente);
                emitter.onComplete();

            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(PacienteActivity.this, "Agrego Medico", Toast.LENGTH_SHORT).show();
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(PacienteActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        loadData(); //Refresh data
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void updatePaciente(final Paciente paciente) {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                pacienteViewModel.updatePaciente(paciente);
                emitter.onComplete();

            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(PacienteActivity.this, "Agrego Medico", Toast.LENGTH_SHORT).show();
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(PacienteActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
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

    public AlertDialog createCustomDialog(Paciente paciente, int Opcion) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // Inflar y establecer el layout para el dialogo
        // Pasar nulo como vista principal porque va en el diseño del diálogo
        View v = inflater.inflate(R.layout.registro_paciente, null);
        this.btnCancelarP = (Button) v.findViewById(R.id.btnCancelarP);
        this.btnPaciente = (Button) v.findViewById(R.id.btnPaciente);
        this.cvFechaNacimiento = (EditText) v.findViewById(R.id.cvFechaNacimiento);
        this.etIdentificacion = (EditText) v.findViewById(R.id.etIdentificacion);
        this.etApellido = (EditText) v.findViewById(R.id.etApellido);
        this.etNombre = (EditText) v.findViewById(R.id.etNombre);


        if (Opcion == 0) {
            etNombre.setText(paciente.getNombre());
            etApellido.setText(paciente.getApellidos());
            etIdentificacion.setText(paciente.getIdentificacion());
            cvFechaNacimiento.setText(paciente.getFecha_nacimiento());

        }


        builder.setView(v);
        alertDialog = builder.create();
        // Add action buttons

        btnPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (Opcion) {
                    case 0:
                        if (TextUtils.isEmpty(etNombre.getText().toString())) {
                            return;
                        } else {
                            paciente.setNombre(etNombre.getText().toString());
                            paciente.setApellidos(etApellido.getText().toString());
                            paciente.setIdentificacion(etIdentificacion.getText().toString());
                            paciente.setFecha_nacimiento(cvFechaNacimiento.getText().toString());
                            updatePaciente(paciente);
                            alertDialog.dismiss();
                        }
                        break;
                    case 1:
                        if (TextUtils.isEmpty(etNombre.getText().toString())) {
                            return;
                        } else {
                            Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {

                                @Override
                                public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                                    paciente.setNombre(etNombre.getText().toString());
                                    paciente.setApellidos(etApellido.getText().toString());
                                    paciente.setIdentificacion(etIdentificacion.getText().toString());
                                    paciente.setFecha_nacimiento(cvFechaNacimiento.getText().toString());
                                    listPaciente.add(paciente);
                                    pacienteViewModel.insertPaciente(paciente);
                                    alertDialog.dismiss();
                                    emitter.onComplete();
                                }
                            })
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new Consumer() {
                                        @Override
                                        public void accept(Object o) throws Exception {
                                            Toast.makeText(PacienteActivity.this, "Agrego Medico", Toast.LENGTH_SHORT).show();
                                        }

                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            Toast.makeText(PacienteActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
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
        btnCancelarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        return alertDialog;
    }

    private void LoadMedicos() {
        //Use liveData
        medicoViewModel.getAllmedicos()
                .observe(this, new Observer<List<Medico>>() {
                    @Override
                    public void onChanged(@Nullable final List<Medico> medicos) {
                        // Update the cached copy of the words in the adapter.
                        items = new String[medicos.size()];
                        int i = 0;
                        for (Medico medico :
                                medicos) {
                            items[i] = medico.getIdmedico() + "- " + medico.getCod_tarjetapro();
                            i++;
                        }
                    }
                });
    }

    public AlertDialog createCita(Paciente paciente) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // Inflar y establecer el layout para el dialogo
        // Pasar nulo como vista principal porque va en el diseño del diálogo
        View v = inflater.inflate(R.layout.registro_cita, null);
        this.cvFechaCita = (EditText) v.findViewById(R.id.cvFechaCita);
        this.tvMedico = (TextView) v.findViewById(R.id.tvMedico);
        this.opMedico = (Spinner) v.findViewById(R.id.opMedico);
        this.btnCancelarC = (Button) v.findViewById(R.id.btnCancelarC);
        this.btnCita = (Button) v.findViewById(R.id.btnCita);
        this.cbAtencionCita = (CheckBox) v.findViewById(R.id.cbAtencionCita);

        this.etCuotaModeradora = (EditText) v.findViewById(R.id.etCuotaModeradora);
        this.cbIsTratamiento = (CheckBox) v.findViewById(R.id.cbIsTratamiento);

        cbAtencionCita.setEnabled(false);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        opMedico.setAdapter(adapter);
        final String[] medico = {""};

        builder.setView(v);
        alertDialog = builder.create();
        // Add action buttons

        btnCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etCuotaModeradora.getText().toString())) {
                    return;
                } else {
                    Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {

                        @Override
                        public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                            medico[0] = opMedico.getSelectedItem().toString();
                            String[] parts = medico[0].split("-");

                            HistorialPaciente cita = new HistorialPaciente(Long.parseLong(parts[0]), paciente.getIdpaciente());
                            cita.setIs_tratamiento(cbIsTratamiento.isChecked());
                            cita.setVal_cuotamod(Float.parseFloat(etCuotaModeradora.getText().toString()));
                            cita.setFyhnuevacita(cvFechaCita.getText().toString());
                            pacienteHViewModel.insertHistorialPaciente(cita);
                            alertDialog.dismiss();
                            emitter.onComplete();
                        }
                    })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Consumer() {
                                @Override
                                public void accept(Object o) throws Exception {
                                    Toast.makeText(PacienteActivity.this, "Agrego Medico", Toast.LENGTH_SHORT).show();
                                }

                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    mensaje("ya tiene una cita asignada con el medico seleccinado: " + medico[0]);
                                    Log.e(TAG, "subscribe: ", throwable);
                                }
                            }, new Action() {
                                @Override
                                public void run() throws Exception {
                                    mensaje(medico[0]);
                                    loadData(); //Refresh data
                                }
                            });
                }
            }
        });

        //Boton para cerrar el cuadro de dialogo
        btnCancelarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        return alertDialog;
    }


    public void mensaje(String mensaje) {
        Toast.makeText(PacienteActivity.this, "Asigno Cita - medico: " + mensaje, Toast.LENGTH_SHORT).show();
    }

}
