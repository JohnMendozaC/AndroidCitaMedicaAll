package com.compilado.johnm.citamedica2.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.compilado.johnm.citamedica2.persistence.dao.HistorialPacienteDao;
import com.compilado.johnm.citamedica2.persistence.dao.MedicoDao;
import com.compilado.johnm.citamedica2.persistence.dao.PacienteDao;
import com.compilado.johnm.citamedica2.persistence.vo.HistorialPaciente;
import com.compilado.johnm.citamedica2.persistence.vo.Medico;
import com.compilado.johnm.citamedica2.persistence.vo.Paciente;

import static com.compilado.johnm.citamedica2.persistence.DataBaseCita.DATABASE_VERSION;

@Database(entities = {Paciente.class,Medico.class, HistorialPaciente.class}, version = DATABASE_VERSION)
public abstract class DataBaseCita extends RoomDatabase {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "citadb";

    public abstract PacienteDao pacienteDao();
    public abstract MedicoDao medicoDao();
    public abstract HistorialPacienteDao historialPacienteDao();

    public static DataBaseCita mInstance;

    public static DataBaseCita getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, DataBaseCita.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return mInstance;
    }

}
