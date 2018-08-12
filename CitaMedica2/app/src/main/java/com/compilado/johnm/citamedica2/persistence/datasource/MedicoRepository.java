package com.compilado.johnm.citamedica2.persistence.datasource;
import android.arch.lifecycle.LiveData;

import com.compilado.johnm.citamedica2.persistence.vo.Medico;

import java.util.List;

import io.reactivex.Flowable;

public class MedicoRepository implements IMedicoDataSource{

    private IMedicoDataSource mMedicoDataSource;
    private static MedicoRepository mInstance;

    public MedicoRepository(IMedicoDataSource mMedicoDataSource) {
        this.mMedicoDataSource = mMedicoDataSource;
    }

    public static MedicoRepository getInstance(IMedicoDataSource mMedicoDataSource) {
        if (mInstance == null) {
            mInstance = new MedicoRepository(mMedicoDataSource);
        }
        return mInstance;
    }

    @Override
    public LiveData<Medico> getMedicoById(Long idmedico) {
        return mMedicoDataSource.getMedicoById(idmedico);
    }

    @Override
    public LiveData<List<Medico>> getAllMedicos() {
        return mMedicoDataSource.getAllMedicos();
    }

    @Override
    public List<Medico> getAllMedicosRetro() {
        return null;
    }

    @Override
    public void insertMedico(Medico... medico) {
        mMedicoDataSource.insertMedico(medico);
    }

    @Override
    public void updateMedico(Medico... medico) {
        mMedicoDataSource.updateMedico(medico);
    }

    @Override
    public void deleteMedico(Medico medico) {
        mMedicoDataSource.deleteMedico(medico);
    }

    @Override
    public void deleteAllMedicos() {
        mMedicoDataSource.deleteAllMedicos();
    }
}
