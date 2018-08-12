package com.compilado.johnm.citamedica2.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.compilado.johnm.citamedica2.persistence.vo.HistorialPaciente;


import java.util.List;

@Dao
public interface HistorialPacienteDao {

    @Query("SELECT * FROM historial_paciente WHERE idmedicofk=:idmedico")
    LiveData<List<HistorialPaciente>> getHistorialPacienteById(Long idmedico);

    @Query("SELECT Count(*) FROM historial_paciente WHERE idmedicofk=:idmedico and aten_cita = 0")
    int getCitasAsignadas(Long idmedico);

    @Query("SELECT * FROM historial_paciente WHERE idpacientefk=:idpaciente")
    LiveData<List<HistorialPaciente>> getCitasPaciente(Long idpaciente);

    @Query("SELECT * FROM historial_paciente")
    LiveData<List<HistorialPaciente>> getAllHistorialPacientes();

    @Query("SELECT * FROM historial_paciente")
    List<HistorialPaciente> getAllHistorialPacientesRetro();

    @Insert
    void insertHistorialPaciente(HistorialPaciente... historial_paciente);

    @Update
    void updateHistorialPaciente(HistorialPaciente... historial_paciente);

    @Delete
    void deleteHistorialPaciente(HistorialPaciente historial_paciente);

    @Query("DELETE FROM historial_paciente")
    void deleteAllHistorialPacientes();

    @Query("DELETE FROM historial_paciente WHERE idmedicofk=:idmedico")
    void deleteHistorialMedico(Long idmedico);

}
