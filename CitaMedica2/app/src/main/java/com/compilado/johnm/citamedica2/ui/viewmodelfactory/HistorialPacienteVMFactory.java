package com.compilado.johnm.citamedica2.ui.viewmodelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.compilado.johnm.citamedica2.persistence.localdatasource.HistorialPacienteDataSource;
import com.compilado.johnm.citamedica2.ui.viewmodel.HistorialPacienteViewModel;

public class HistorialPacienteVMFactory implements ViewModelProvider.Factory {

    private final HistorialPacienteDataSource historialPacienteDataSource;

    public HistorialPacienteVMFactory(HistorialPacienteDataSource historialPacienteDataSource) {
        this.historialPacienteDataSource = historialPacienteDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HistorialPacienteViewModel.class)) {
            return (T) new HistorialPacienteViewModel(historialPacienteDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
