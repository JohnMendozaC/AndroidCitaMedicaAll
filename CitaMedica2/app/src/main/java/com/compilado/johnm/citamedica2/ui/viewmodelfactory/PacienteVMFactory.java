package com.compilado.johnm.citamedica2.ui.viewmodelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.compilado.johnm.citamedica2.persistence.localdatasource.PacienteDataSource;
import com.compilado.johnm.citamedica2.ui.viewmodel.PacienteViewModel;

public class PacienteVMFactory implements ViewModelProvider.Factory {

    private final PacienteDataSource PacienteDataSource;

    public PacienteVMFactory(PacienteDataSource pacienteDataSource) {
        this.PacienteDataSource = pacienteDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PacienteViewModel.class)) {
            return (T) new PacienteViewModel(PacienteDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
