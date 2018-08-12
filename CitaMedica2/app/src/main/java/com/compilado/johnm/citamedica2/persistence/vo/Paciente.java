package com.compilado.johnm.citamedica2.persistence.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

@Entity(tableName = "paciente")
public class Paciente {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idpaciente")
    @SerializedName("idpaciente")
    @Expose
    private Long idpaciente;

    @ColumnInfo(name = "nombre")
    @SerializedName("nombre")
    @Expose
    private String nombre;

    @ColumnInfo(name = "apellidos")
    @SerializedName("apellidos")
    @Expose
    private String apellidos;

    @ColumnInfo(name = "fecha_nacimiento")
    @SerializedName("fecha_nacimiento")
    @Expose
    private String fecha_nacimiento;

    @ColumnInfo(name = "identificacion")
    @SerializedName("identificacion")
    @Expose
    private String identificacion;

    public Paciente() {
        this.nombre = "";
        this.apellidos = "";
        this.fecha_nacimiento = "1996-02-02";
        this.identificacion = "";
    }

    @Ignore
    public Paciente(String nombre, String apellidos, String fecha_nacimiento, String identificacion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fecha_nacimiento = fecha_nacimiento.toString();
        this.identificacion = identificacion;
    }

    @NonNull
    public Long getIdpaciente() {
        return idpaciente;
    }

    public void setIdpaciente(@NonNull Long idpaciente) {
        this.idpaciente = idpaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    @Override
    public String toString() {
        String paciente = "Nombre: "+nombre+
                "\nApellidos: "+apellidos+
                "\nFecha de nacimiento: "+fecha_nacimiento+
                "\nIdentificacion: "+identificacion;
        return paciente;
    }
}
