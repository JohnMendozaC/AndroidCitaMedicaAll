package com.compilado.johnm.citamedica2.ui.injection;

import android.content.Context;

import com.compilado.johnm.citamedica2.persistence.DataBaseCita;
import com.compilado.johnm.citamedica2.persistence.localdatasource.HistorialPacienteDataSource;
import com.compilado.johnm.citamedica2.persistence.localdatasource.MedicoDataSource;
import com.compilado.johnm.citamedica2.persistence.localdatasource.PacienteDataSource;
import com.compilado.johnm.citamedica2.persistence.vo.HistorialPaciente;
import com.compilado.johnm.citamedica2.ui.viewmodelfactory.HistorialPacienteVMFactory;
import com.compilado.johnm.citamedica2.ui.viewmodelfactory.MedicoVMFactory;
import com.compilado.johnm.citamedica2.ui.viewmodelfactory.PacienteVMFactory;

public class Injection {

    //Injection Medico
    public static MedicoDataSource provideMedicoDataSource(Context context){
        DataBaseCita dataBaseCita = DataBaseCita.getInstance(context);
        return new MedicoDataSource(dataBaseCita.medicoDao());
    }

    public static MedicoVMFactory provideMedicoViewModelFactory(Context context){
        MedicoDataSource dataSource = provideMedicoDataSource(context);
        return new MedicoVMFactory(dataSource);
    }

    //Injection Paciente
    public static PacienteDataSource providePacienteDataSource(Context context){
        DataBaseCita dataBaseCita = DataBaseCita.getInstance(context);
        return new PacienteDataSource(dataBaseCita.pacienteDao());
    }

    public static PacienteVMFactory providePacienteViewModelFactory(Context context){
        PacienteDataSource dataSource = providePacienteDataSource(context);
        return new PacienteVMFactory(dataSource);
    }

    //Injection Historial Paciente
    public static HistorialPacienteDataSource provideHistorialPacienteDataSource(Context context){
        DataBaseCita dataBaseCita = DataBaseCita.getInstance(context);
        return new HistorialPacienteDataSource(dataBaseCita.historialPacienteDao());
    }

    public static HistorialPacienteVMFactory provideHistorialPacienteViewModelFactory(Context context){
        HistorialPacienteDataSource dataSource = provideHistorialPacienteDataSource(context);
        return new HistorialPacienteVMFactory(dataSource);
    }


}
