package com.compilado.johnm.citamedica2.persistence.datasource;

import android.arch.lifecycle.LiveData;

import com.compilado.johnm.citamedica2.persistence.vo.HistorialPaciente;

import java.util.List;

public interface IHistorialPacienteDataSource {


    LiveData<List<HistorialPaciente>> getHistorialPacienteById(Long idmedico);

    int getCitasAsignadas(Long idmedico);

    LiveData<List<HistorialPaciente>> getCitasPaciente(Long idpaciente);


    LiveData<List<HistorialPaciente>> getAllHistorialPacientes();


    List<HistorialPaciente> getAllHistorialPacientesRetro();

    void insertHistorialPaciente(HistorialPaciente... historial_paciente);


    void updateHistorialPaciente(HistorialPaciente... historial_paciente);


    void deleteHistorialPaciente(HistorialPaciente historial_paciente);


    void deleteAllHistorialPacientes();

    void deleteHistorialMedico(Long idmedico);

}
