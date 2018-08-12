package com.compilado.johnm.citamedica2.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.compilado.johnm.citamedica2.persistence.localdatasource.MedicoDataSource;
import com.compilado.johnm.citamedica2.persistence.vo.Medico;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class MedicoViewModel extends ViewModel {

    private MedicoDataSource medicoDataSource;
    private Medico mMedico;


    public MedicoViewModel(MedicoDataSource medicoDataSource) {
        this.medicoDataSource = medicoDataSource;
    }

    public LiveData<List<Medico>> getAllmedicos() {
        return medicoDataSource.getAllMedicos();

    }

    public void updateMedico(final Medico medico) {

        mMedico = mMedico == null
                ? new Medico()
                : medico;
        medicoDataSource.updateMedico(medico);

    }


    public void insertMedico(Medico medico) {

            mMedico = mMedico == null
                    ? new Medico()
                    : medico;
            medicoDataSource.insertMedico(medico);

    }

    public void deleteMedico(Medico medico) {

            mMedico = mMedico == null
                    ? new Medico()
                    : medico;
            medicoDataSource.deleteMedico(medico);

    }

    public void deleteAllMedicos() {
        medicoDataSource.deleteAllMedicos();
    }
}
