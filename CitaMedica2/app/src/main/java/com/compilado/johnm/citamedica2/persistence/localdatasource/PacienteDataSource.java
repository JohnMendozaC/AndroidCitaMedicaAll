package com.compilado.johnm.citamedica2.persistence.localdatasource;

import android.arch.lifecycle.LiveData;
import android.util.Log;


import com.compilado.johnm.citamedica2.persistence.dao.PacienteDao;
import com.compilado.johnm.citamedica2.persistence.datasource.IPacienteDataSource;
import com.compilado.johnm.citamedica2.persistence.datasource.PacienteRepository;
import com.compilado.johnm.citamedica2.persistence.vo.Paciente;

import java.util.List;

public class PacienteDataSource implements IPacienteDataSource {


    private PacienteDao pacienteDao;
    private static PacienteDataSource mInstance;

    public PacienteDataSource(PacienteDao pacienteDao) {
        this.pacienteDao = pacienteDao;
    }

    public static PacienteDataSource getInstance(PacienteDao mpacienteDS) {
        if (mInstance == null) {
            mInstance = new PacienteDataSource(mpacienteDS);
        }
        return mInstance;
    }

    @Override
    public LiveData<Paciente> getPacienteById(Long idPaciente) {
        return pacienteDao.getPacienteById(idPaciente);
    }

    @Override
    public LiveData<List<Paciente>> getAllPacientes() {
        return pacienteDao.getAllPacientes();
    }

    @Override
    public List<Paciente> getAllPacientesRetro() {
        return (List<Paciente>) pacienteDao.getAllPacientesRetro();
    }

    @Override
    public void insertPaciente(Paciente... paciente) {
        Log.i("CitaPaciente2", "insertPaciente:Entro "+paciente.toString());
        pacienteDao.insertPaciente(paciente);
    }

    @Override
    public void updatePaciente(Paciente... paciente) {
        pacienteDao.updatePaciente(paciente);
    }

    @Override
    public void deletePaciente(Paciente paciente) {
        pacienteDao.deletePaciente(paciente);
    }

    @Override
    public void deleteAllPacientes() {
        pacienteDao.deleteAllPacientes();
    }
}
