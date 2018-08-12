package com.compilado.johnm.citamedica2.persistence.localdatasource;

import android.arch.lifecycle.LiveData;

import com.compilado.johnm.citamedica2.persistence.dao.HistorialPacienteDao;
import com.compilado.johnm.citamedica2.persistence.datasource.IHistorialPacienteDataSource;
import com.compilado.johnm.citamedica2.persistence.vo.HistorialPaciente;

import java.util.List;

public class HistorialPacienteDataSource implements IHistorialPacienteDataSource {


    private HistorialPacienteDao historialPacienteDao;
    private static HistorialPacienteDataSource mInstance;

    public HistorialPacienteDataSource(HistorialPacienteDao historialPacienteDao) {
        this.historialPacienteDao = historialPacienteDao;
    }

    public static HistorialPacienteDataSource getInstance(HistorialPacienteDao historialPacienteDao) {
        if (mInstance == null) {
            mInstance = new HistorialPacienteDataSource(historialPacienteDao);
        }
        return mInstance;
    }

    @Override
    public LiveData<List<HistorialPaciente>> getHistorialPacienteById(Long idmedico) {
        return historialPacienteDao.getHistorialPacienteById(idmedico);
    }

    @Override
    public int getCitasAsignadas(Long idmedico) {
        return historialPacienteDao.getCitasAsignadas(idmedico);
    }

    @Override
    public LiveData<List<HistorialPaciente>> getCitasPaciente(Long idpaciente) {
        return historialPacienteDao.getCitasPaciente(idpaciente);
    }

    @Override
    public LiveData<List<HistorialPaciente>> getAllHistorialPacientes() {
        return historialPacienteDao.getAllHistorialPacientes();
    }

    @Override
    public List<HistorialPaciente> getAllHistorialPacientesRetro() {
        return historialPacienteDao.getAllHistorialPacientesRetro();
    }

    @Override
    public void insertHistorialPaciente(HistorialPaciente... historialPaciente) {
        historialPacienteDao.insertHistorialPaciente(historialPaciente);
    }

    @Override
    public void updateHistorialPaciente(HistorialPaciente... historialPaciente) {
        historialPacienteDao.updateHistorialPaciente(historialPaciente);
    }

    @Override
    public void deleteHistorialPaciente(HistorialPaciente historialPaciente) {
        historialPacienteDao.deleteHistorialPaciente(historialPaciente);
    }

    @Override
    public void deleteAllHistorialPacientes() {
        historialPacienteDao.deleteAllHistorialPacientes();
    }

    @Override
    public void deleteHistorialMedico(Long idmedico) {
        historialPacienteDao.deleteHistorialMedico(idmedico);
    }
}
