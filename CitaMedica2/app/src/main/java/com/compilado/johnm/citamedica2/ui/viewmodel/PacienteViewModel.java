package com.compilado.johnm.citamedica2.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.widget.Toast;

import com.compilado.johnm.citamedica2.persistence.localdatasource.PacienteDataSource;
import com.compilado.johnm.citamedica2.persistence.localdatasource.PacienteDataSource;
import com.compilado.johnm.citamedica2.persistence.vo.Paciente;
import com.compilado.johnm.citamedica2.persistence.vo.Paciente;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class PacienteViewModel extends ViewModel {

    private PacienteDataSource pacienteDataSource;
    private Paciente mPaciente;


    public PacienteViewModel(PacienteDataSource pacienteDataSource) {
        this.pacienteDataSource = pacienteDataSource;
    }

    public LiveData<List<Paciente>> getAllPacientes() {
        return pacienteDataSource.getAllPacientes();

    }

    public void updatePaciente(final Paciente paciente) {

        mPaciente = mPaciente == null
                ? new Paciente()
                : paciente;
        pacienteDataSource.updatePaciente(paciente);

    }


    public void insertPaciente(Paciente paciente) {
        Log.i("CitaPaciente", "insertPaciente:Entro "+paciente.toString());
        mPaciente = mPaciente == null
                ? new Paciente()
                : paciente;
        pacienteDataSource.insertPaciente(paciente);

    }

    public void deletePaciente(Paciente paciente) {


        mPaciente = mPaciente == null
                ? new Paciente()
                : paciente;
        pacienteDataSource.deletePaciente(paciente);

    }

    public void deleteAllPacientes() {
        pacienteDataSource.deleteAllPacientes();
    }
}
