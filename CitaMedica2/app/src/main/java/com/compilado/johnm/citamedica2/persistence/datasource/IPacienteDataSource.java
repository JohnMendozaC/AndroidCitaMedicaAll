package com.compilado.johnm.citamedica2.persistence.datasource;

import android.arch.lifecycle.LiveData;

import com.compilado.johnm.citamedica2.persistence.vo.Paciente;

import java.util.List;

public interface IPacienteDataSource {


    LiveData<Paciente> getPacienteById(Long idpaciente);


    LiveData<List<Paciente>> getAllPacientes();


    List<Paciente> getAllPacientesRetro();

    void insertPaciente(Paciente... paciente);


    void updatePaciente(Paciente... paciente);


    void deletePaciente(Paciente paciente);


    void deleteAllPacientes();
}
