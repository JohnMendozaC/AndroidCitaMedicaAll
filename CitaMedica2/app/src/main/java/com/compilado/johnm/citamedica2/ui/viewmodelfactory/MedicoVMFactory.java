package com.compilado.johnm.citamedica2.ui.viewmodelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.compilado.johnm.citamedica2.persistence.localdatasource.MedicoDataSource;
import com.compilado.johnm.citamedica2.ui.viewmodel.MedicoViewModel;

public class MedicoVMFactory implements ViewModelProvider.Factory {

    private final MedicoDataSource medicoDataSource;

    public MedicoVMFactory(MedicoDataSource medicoDataSource) {
        this.medicoDataSource = medicoDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MedicoViewModel.class)) {
            return (T) new MedicoViewModel(medicoDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
