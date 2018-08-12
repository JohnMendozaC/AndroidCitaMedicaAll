package com.compilado.johnm.citamedica2.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.compilado.johnm.citamedica2.persistence.vo.Medico;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MedicoDao {

    @Query("SELECT * FROM medico WHERE idmedico=:idmedico")
    LiveData<Medico> getMedicoById(Long idmedico);

    @Query("SELECT * FROM medico")
    LiveData<List<Medico>> getAllMedicos();

    @Query("SELECT * FROM medico")
    List<Medico> getAllMedicosRetro();

    @Insert
    void insertMedico(Medico... medico);

    @Update
    void updateMedico(Medico... medico);

    @Delete
    void deleteMedico(Medico medico);

    @Query("DELETE FROM medico")
    void deleteAllMedicos();

}
