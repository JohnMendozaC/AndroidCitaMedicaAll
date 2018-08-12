package com.compilado.johnm.citamedica2.persistence.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "medico")
public class Medico {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idmedico")
    @SerializedName("idmedico")
    @Expose
    private Long idmedico;

    @ColumnInfo(name = "cod_tarjetapro")
    @SerializedName("cod_tarjetapro")
    @Expose
    private String cod_tarjetapro;

    @ColumnInfo(name = "especialidad")
    @SerializedName("especialidad")
    @Expose
    private String especialidad;

    @ColumnInfo(name = "año_experiencia")
    @SerializedName("año_experiencia")
    @Expose
    private float año_experiencia;

    @ColumnInfo(name = "consultorio")
    @SerializedName("consultorio")
    @Expose
    private String consultorio;

    @ColumnInfo(name = "aten_domicilio")
    @SerializedName("aten_domicilio")
    @Expose
    private boolean aten_domicilio;


    public Medico() {

    }

    @Ignore
    public Medico(String cod_tarjetapro, String especialidad, float año_experiencia, String consultorio, boolean aten_domicilio) {
        this.cod_tarjetapro = cod_tarjetapro;
        this.especialidad = especialidad;
        this.año_experiencia = año_experiencia;
        this.consultorio = consultorio;
        this.aten_domicilio = aten_domicilio;
    }

    @NonNull
    public Long getIdmedico() {
        return idmedico;
    }

    public void setIdmedico(@NonNull Long idmedico) {
        this.idmedico = idmedico;
    }

    public String getCod_tarjetapro() {
        return cod_tarjetapro;
    }

    public void setCod_tarjetapro(String cod_tarjetapro) {
        this.cod_tarjetapro = cod_tarjetapro;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public float getAño_experiencia() {
        return año_experiencia;
    }

    public void setAño_experiencia(float año_experiencia) {
        this.año_experiencia = año_experiencia;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public boolean isAten_domicilio() {
        return aten_domicilio;
    }

    public void setAten_domicilio(boolean aten_domicilio) {
        this.aten_domicilio = aten_domicilio;
    }

    @Override
    public String toString() {
        String medico = "ID medico: "+ idmedico+
                "\nCodigo tarjeta profecional: "+cod_tarjetapro+
                "\nEspecialidad: "+especialidad+
                "\nAños experiencia: "+año_experiencia+
                "\nConsultorio: "+consultorio+
                "\nAtencion a domicilio: "+((aten_domicilio)? "si" : "no");
        return medico;
    }
}
