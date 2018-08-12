package com.compilado.johnm.citamedica2.persistence.localdatasource;

import android.arch.lifecycle.LiveData;

import com.compilado.johnm.citamedica2.persistence.dao.MedicoDao;
import com.compilado.johnm.citamedica2.persistence.datasource.IMedicoDataSource;
import com.compilado.johnm.citamedica2.persistence.vo.Medico;

import java.util.List;

import io.reactivex.Flowable;

public class MedicoDataSource implements IMedicoDataSource {


    private MedicoDao medicoDao;
    private static MedicoDataSource mInstance;

    public MedicoDataSource(MedicoDao medicoDao) {
        this.medicoDao = medicoDao;
    }

    public static MedicoDataSource getInstance(MedicoDao medicoDao) {
        if (mInstance == null) {
            mInstance = new MedicoDataSource(medicoDao);
        }
        return mInstance;
    }

    @Override
    public LiveData<Medico> getMedicoById(Long idmedico) {
        return medicoDao.getMedicoById(idmedico);
    }

    @Override
    public LiveData<List<Medico>> getAllMedicos() {
        return medicoDao.getAllMedicos();
    }

    @Override
    public List<Medico> getAllMedicosRetro() {
        return medicoDao.getAllMedicosRetro();
    }

    @Override
    public void insertMedico(Medico... medico) {
        medicoDao.insertMedico(medico);
    }

    @Override
    public void updateMedico(Medico... medico) {
        medicoDao.updateMedico(medico);
    }

    @Override
    public void deleteMedico(Medico medico) {
        medicoDao.deleteMedico(medico);
    }

    @Override
    public void deleteAllMedicos() {
        medicoDao.deleteAllMedicos();
    }
}
