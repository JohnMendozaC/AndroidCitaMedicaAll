package com.compilado.johnm.citamedica2.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.compilado.johnm.citamedica2.R;
import com.compilado.johnm.citamedica2.persistence.vo.HistorialPaciente;
import com.compilado.johnm.citamedica2.ui.injection.Injection;
import com.compilado.johnm.citamedica2.ui.viewmodel.HistorialPacienteViewModel;
import com.compilado.johnm.citamedica2.ui.viewmodelfactory.HistorialPacienteVMFactory;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FirmaActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "CitaMedica";

    ImageButton negro;
    ImageButton blanco;
    ImageButton rojo;
    ImageButton verde;
    ImageButton azul;
    private static Lienzo lienzo;
    float ppequenyo;
    float pmediano;
    float pgrande;
    float pdefecto;
    ImageButton trazo;
    ImageButton anyadir;
    ImageButton borrar;
    ImageButton guardar;

    //Database
    private CompositeDisposable compositeDisposable;
    private HistorialPacienteVMFactory pacienteHVMFactory;
    private HistorialPacienteViewModel pacienteHViewModel;
    byte[] firmabd;
    HistorialPaciente pacientefirma;

    //Permiso de escribir
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        negro = (ImageButton) findViewById(R.id.colornegro);
        blanco = (ImageButton) findViewById(R.id.colorblanco);
        rojo = (ImageButton) findViewById(R.id.colorrojo);
        verde = (ImageButton) findViewById(R.id.colorverde);
        azul = (ImageButton) findViewById(R.id.colorazul);
        trazo = (ImageButton) findViewById(R.id.trazo);
        anyadir = (ImageButton) findViewById(R.id.anyadir);
        borrar = (ImageButton) findViewById(R.id.borrar);
        guardar = (ImageButton) findViewById(R.id.guardar);

        negro.setOnClickListener((View.OnClickListener) this);
        blanco.setOnClickListener((View.OnClickListener) this);
        rojo.setOnClickListener((View.OnClickListener) this);
        verde.setOnClickListener((View.OnClickListener) this);
        azul.setOnClickListener((View.OnClickListener) this);
        trazo.setOnClickListener((View.OnClickListener) this);
        anyadir.setOnClickListener((View.OnClickListener) this);
        borrar.setOnClickListener((View.OnClickListener) this);
        guardar.setOnClickListener((View.OnClickListener) this);

        //Recoger los datos del paciente
        compositeDisposable = new CompositeDisposable();
        pacienteHVMFactory = Injection.provideHistorialPacienteViewModelFactory(this);
        pacienteHViewModel = ViewModelProviders.of(this, pacienteHVMFactory)
                .get(HistorialPacienteViewModel.class);
        pacientefirma = (HistorialPaciente) getIntent().getExtras().get("cita");
        lienzo = (Lienzo) findViewById(R.id.lienzo);

        ppequenyo = 10;
        pmediano = 20;
        pgrande = 30;

        pdefecto = pmediano;

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void guardarFirmaRoom() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {

                pacientefirma.setAten_cita(true);
                pacientefirma.setFirma(firmabd);
                pacienteHViewModel.updateHistorialPaciente(pacientefirma);

                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(FirmaActivity.this, "Firmo cita", Toast.LENGTH_SHORT).show();
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "subscribe: ", throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //Refresh data

                    }
                });
    }


    @Override
    public void onClick(View v) {
        String color = null;


        switch (v.getId()) {
            case R.id.colornegro:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            case R.id.colorblanco:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            case R.id.colorazul:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            case R.id.colorverde:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            case R.id.colorrojo:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            case R.id.trazo:
                final Dialog tamanyopunto = new Dialog(this);
                tamanyopunto.setTitle("Tamaño del punto:");
                tamanyopunto.setContentView(R.layout.tamanyo_punto);
                //listen for clicks on tamaños de los botones
                TextView smallBtn = (TextView) tamanyopunto.findViewById(R.id.tpequenyo);
                smallBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setBorrado(false);
                        Lienzo.setTamanyoPunto(ppequenyo);

                        tamanyopunto.dismiss();
                    }
                });
                TextView mediumBtn = (TextView) tamanyopunto.findViewById(R.id.tmediano);
                mediumBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setBorrado(false);
                        Lienzo.setTamanyoPunto(pmediano);

                        tamanyopunto.dismiss();
                    }
                });
                TextView largeBtn = (TextView) tamanyopunto.findViewById(R.id.tgrande);
                largeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setBorrado(false);
                        Lienzo.setTamanyoPunto(pgrande);

                        tamanyopunto.dismiss();
                    }
                });
                //show and wait for user interaction
                tamanyopunto.show();


                break;
            case R.id.anyadir:


                AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
                newDialog.setTitle("Nuevo Dibujo");
                newDialog.setMessage("¿Comenzar nuevo dibujo (perderás el dibujo actual)?");
                newDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        lienzo.NuevoDibujo();
                        dialog.dismiss();
                    }
                });
                newDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                newDialog.show();


                break;
            case R.id.borrar:

                final Dialog borrarpunto = new Dialog(this);
                borrarpunto.setTitle("Tamaño de borrado:");
                borrarpunto.setContentView(R.layout.tamanyo_punto);
                //listen for clicks on tamaños de los botones
                TextView smallBtnBorrar = (TextView) borrarpunto.findViewById(R.id.tpequenyo);
                smallBtnBorrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setBorrado(true);
                        Lienzo.setTamanyoPunto(ppequenyo);

                        borrarpunto.dismiss();
                    }
                });
                TextView mediumBtnBorrar = (TextView) borrarpunto.findViewById(R.id.tmediano);
                mediumBtnBorrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setBorrado(true);
                        Lienzo.setTamanyoPunto(pmediano);

                        borrarpunto.dismiss();
                    }
                });
                TextView largeBtnBorrar = (TextView) borrarpunto.findViewById(R.id.tgrande);
                largeBtnBorrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setBorrado(true);
                        Lienzo.setTamanyoPunto(pgrande);

                        borrarpunto.dismiss();
                    }
                });
                //show and wait for user interaction
                borrarpunto.show();


                break;
            case R.id.guardar:

                AlertDialog.Builder salvarDibujo = new AlertDialog.Builder(this);
                salvarDibujo.setTitle("Salvar dibujo");
                salvarDibujo.setMessage("¿Salvar Dibujo a la galeria?");
                salvarDibujo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //Salvar dibujo
                        lienzo.setDrawingCacheEnabled(true);

                        Bitmap bitmap = lienzo.getCanvasBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        firmabd = baos.toByteArray();


                        //attempt to save
                        String imgSaved = MediaStore.Images.Media.insertImage(
                                getContentResolver(), lienzo.getDrawingCache(),
                                UUID.randomUUID().toString() + ".png", "drawing");
                        //Mensaje de todo correcto
                        if (imgSaved != null) {
                            guardarFirmaRoom(); //Guardar byte[] en base room
                            //Devolverse a la activity anterior y cerrar la actual
                            onBackPressed();
                            finish();

                            Toast savedToast = Toast.makeText(getApplicationContext(),
                                    "¡Dibujo salvado en la galeria!", Toast.LENGTH_SHORT);
                            savedToast.show();
                        } else {
                            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                    "¡Error! La imagen no ha podido ser salvada.", Toast.LENGTH_SHORT);
                            unsavedToast.show();
                        }
                        lienzo.destroyDrawingCache();


                    }
                });
                salvarDibujo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                salvarDibujo.show();
                break;
            default:

                break;
        }
    }
}