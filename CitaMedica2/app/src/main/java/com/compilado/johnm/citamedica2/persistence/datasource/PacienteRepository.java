package com.compilado.johnm.citamedica2.persistence.datasource;
import android.arch.lifecycle.LiveData;


import com.compilado.johnm.citamedica2.persistence.vo.Paciente;

import java.util.List;

public class PacienteRepository implements IPacienteDataSource{

    private IPacienteDataSource mPacienteDataSource;
    private static PacienteRepository mInstance;

    public PacienteRepository(IPacienteDataSource mPacienteDataSource) {
        this.mPacienteDataSource = mPacienteDataSource;
    }

    public static PacienteRepository getInstance(IPacienteDataSource mPacienteDataSource) {
        if (mInstance == null) {
            mInstance = new PacienteRepository(mPacienteDataSource);
        }
        return mInstance;
    }

    @Override
    public LiveData<Paciente> getPacienteById(Long idPaciente) {
        return mPacienteDataSource.getPacienteById(idPaciente);
    }

    @Override
    public LiveData<List<Paciente>> getAllPacientes() {
        return mPacienteDataSource.getAllPacientes();
    }

    @Override
    public List<Paciente> getAllPacientesRetro() {
        return null;
    }

    @Override
    public void insertPaciente(Paciente... paciente) {
        mPacienteDataSource.insertPaciente(paciente);
    }

    @Override
    public void updatePaciente(Paciente... paciente) {
        mPacienteDataSource.updatePaciente(paciente);
    }

    @Override
    public void deletePaciente(Paciente paciente) {
        mPacienteDataSource.deletePaciente(paciente);
    }

    @Override
    public void deleteAllPacientes() {
        mPacienteDataSource.deleteAllPacientes();
    }
}
