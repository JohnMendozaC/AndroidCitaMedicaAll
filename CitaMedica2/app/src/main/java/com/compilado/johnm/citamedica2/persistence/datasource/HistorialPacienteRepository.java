package com.compilado.johnm.citamedica2.persistence.datasource;

import android.arch.lifecycle.LiveData;

import com.compilado.johnm.citamedica2.persistence.vo.HistorialPaciente;

import java.util.List;

public class HistorialPacienteRepository implements IHistorialPacienteDataSource{

    private IHistorialPacienteDataSource mHistorialPacienteDataSource;
    private static HistorialPacienteRepository mInstance;

    public HistorialPacienteRepository(IHistorialPacienteDataSource mHistorialPacienteDataSource) {
        this.mHistorialPacienteDataSource = mHistorialPacienteDataSource;
    }

    public static HistorialPacienteRepository getInstance(IHistorialPacienteDataSource mHistorialPacienteDataSource) {
        if (mInstance == null) {
            mInstance = new HistorialPacienteRepository(mHistorialPacienteDataSource);
        }
        return mInstance;
    }

    @Override
    public LiveData<List<HistorialPaciente>> getHistorialPacienteById(Long idmedico) {
        return mHistorialPacienteDataSource.getHistorialPacienteById(idmedico);
    }

    @Override
    public int getCitasAsignadas(Long idmedico) {
        return mHistorialPacienteDataSource.getCitasAsignadas(idmedico);
    }

    @Override
    public LiveData<List<HistorialPaciente>> getCitasPaciente(Long idpaciente) {
        return mHistorialPacienteDataSource.getCitasPaciente(idpaciente);
    }

    @Override
    public LiveData<List<HistorialPaciente>> getAllHistorialPacientes() {
        return mHistorialPacienteDataSource.getAllHistorialPacientes();
    }

    @Override
    public List<HistorialPaciente> getAllHistorialPacientesRetro() {
        return null;
    }

    @Override
    public void insertHistorialPaciente(HistorialPaciente... historialPaciente) {
        mHistorialPacienteDataSource.insertHistorialPaciente(historialPaciente);
    }

    @Override
    public void updateHistorialPaciente(HistorialPaciente... historialPaciente) {
        mHistorialPacienteDataSource.updateHistorialPaciente(historialPaciente);
    }

    @Override
    public void deleteHistorialPaciente(HistorialPaciente historialPaciente) {
        mHistorialPacienteDataSource.deleteHistorialPaciente(historialPaciente);
    }

    @Override
    public void deleteAllHistorialPacientes() {
        mHistorialPacienteDataSource.deleteAllHistorialPacientes();
    }

    @Override
    public void deleteHistorialMedico(Long idmedico) {
        mHistorialPacienteDataSource.deleteHistorialMedico(idmedico);
    }
}
