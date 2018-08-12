package com.compilado.johnm.citamedica2.persistence.datasource;

import android.arch.lifecycle.LiveData;

import com.compilado.johnm.citamedica2.persistence.vo.Medico;

import java.util.List;

import io.reactivex.Flowable;

public interface IMedicoDataSource {

    LiveData<Medico> getMedicoById(Long idmedico);


    LiveData<List<Medico>> getAllMedicos();


    List<Medico> getAllMedicosRetro();

    void insertMedico(Medico... medico);


    void updateMedico(Medico... medico);


    void deleteMedico(Medico medico);


    void deleteAllMedicos();
}
