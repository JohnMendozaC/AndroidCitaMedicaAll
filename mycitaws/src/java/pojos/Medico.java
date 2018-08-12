/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author johnm
 */
@XmlRootElement
public class Medico  {
    
    private Long idmedico;

    
    private String cod_tarjetapro;

    
    private String especialidad;

    
    private float año_experiencia;

    
    private String consultorio;

    
    private boolean aten_domicilio;


    public Medico() {

    }

    public Long getIdmedico() {
        return idmedico;
    }

    public void setIdmedico(Long idmedico) {
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
        return "Medico{" + "idmedico=" + idmedico + ", cod_tarjetapro=" + cod_tarjetapro + ", especialidad=" + especialidad + ", a\u00f1o_experiencia=" + año_experiencia + ", consultorio=" + consultorio + ", aten_domicilio=" + aten_domicilio + '}';
    }
    
   

    
    
}
