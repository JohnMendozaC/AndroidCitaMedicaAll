package com.compilado.johnm.citamedica2.remote.pojo;


import com.compilado.johnm.citamedica2.persistence.vo.HistorialPaciente;
import com.compilado.johnm.citamedica2.persistence.vo.Medico;
import com.compilado.johnm.citamedica2.persistence.vo.Paciente;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Citas {

    @SerializedName("medicos")
    @Expose
    private List<Medico> medicos;

    @SerializedName("pacientes")
    @Expose
    private List<Paciente> pacientes;

    @SerializedName("hpacientes")
    @Expose
    private List<HistorialPaciente> hpacientes;

    public Citas(List<Medico> medicos, List<Paciente> pacientes, List<HistorialPaciente> hpacientes) {
        this.medicos = medicos;
        this.pacientes = pacientes;
        this.hpacientes = hpacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public List<HistorialPaciente> getHpacientes() {
        return hpacientes;
    }

    public void setHpacientes(List<HistorialPaciente> hpacientes) {
        this.hpacientes = hpacientes;
    }

    @Override
    public String toString() {
        return "Citas{" +
                "medicos=" + medicos +
                ", pacientes=" + pacientes +
                ", hpacientes=" + hpacientes +
                '}';
    }
}
