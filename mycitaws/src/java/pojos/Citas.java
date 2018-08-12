/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

/**
 *
 * @author johnm
 */


import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Citas {

    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<HistorialPaciente> hpacientes;

    public Citas() {
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

