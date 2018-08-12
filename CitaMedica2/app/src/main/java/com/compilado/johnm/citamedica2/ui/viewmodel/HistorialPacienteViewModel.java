package com.compilado.johnm.citamedica2.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.compilado.johnm.citamedica2.persistence.localdatasource.HistorialPacienteDataSource;
import com.compilado.johnm.citamedica2.persistence.vo.HistorialPaciente;

import java.util.List;


public class HistorialPacienteViewModel extends ViewModel {

    private HistorialPacienteDataSource HistorialPacienteDataSource;


    private HistorialPaciente mHistorialPaciente;


    public HistorialPacienteViewModel(HistorialPacienteDataSource HistorialPacienteDataSource) {
        this.HistorialPacienteDataSource = HistorialPacienteDataSource;
    }

    public LiveData<List<HistorialPaciente>> getAllHistorialPacientes() {
        return HistorialPacienteDataSource.getAllHistorialPacientes();

    }

    public void updateHistorialPaciente(final HistorialPaciente historialPaciente) {

        mHistorialPaciente = mHistorialPaciente == null
                ? new HistorialPaciente()
                : historialPaciente;
        HistorialPacienteDataSource.updateHistorialPaciente(historialPaciente);

    }


    public void insertHistorialPaciente(HistorialPaciente historialPaciente) {

        mHistorialPaciente = mHistorialPaciente == null
                ? new HistorialPaciente()
                : historialPaciente;
        HistorialPacienteDataSource.insertHistorialPaciente(historialPaciente);

    }

    public void deleteHistorialPaciente(HistorialPaciente historialPaciente) {

        mHistorialPaciente = mHistorialPaciente == null
                ? new HistorialPaciente()
                : historialPaciente;
        HistorialPacienteDataSource.deleteHistorialPaciente(historialPaciente);

    }

    public void deleteAllHistorialPacientes() {
        HistorialPacienteDataSource.deleteAllHistorialPacientes();
    }

    public int getCitasAsignadas(Long idmedico) {
        return HistorialPacienteDataSource.getCitasAsignadas(idmedico);
    }

    public LiveData<List<HistorialPaciente>> getCitasPaciente(Long idpaciente) {
        return HistorialPacienteDataSource.getCitasPaciente(idpaciente);
    }

    public void deleteHistorialMedico(Long idmedico) {
        HistorialPacienteDataSource.deleteHistorialMedico(idmedico);
    }

    public LiveData<List<HistorialPaciente>> getHistorialPacienteById(Long idmedico) {
        return HistorialPacienteDataSource.getHistorialPacienteById(idmedico);
    }

}
