package com.compilado.johnm.citamedica2.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.compilado.johnm.citamedica2.persistence.vo.Paciente;

import java.util.List;

@Dao
public interface PacienteDao {

    @Query("SELECT * FROM paciente WHERE idpaciente=:idpaciente")
    LiveData<Paciente> getPacienteById(Long idpaciente);

    @Query("SELECT * FROM paciente")
    LiveData<List<Paciente>> getAllPacientes();

    @Query("SELECT * FROM paciente")
    List<Paciente> getAllPacientesRetro();

    @Insert
    void insertPaciente(Paciente... paciente);

    @Update
    void updatePaciente(Paciente... paciente);

    @Delete
    void deletePaciente(Paciente paciente);

    @Query("DELETE FROM paciente")
    void deleteAllPacientes();

}
