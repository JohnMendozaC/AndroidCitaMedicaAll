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
public class HistorialPaciente  {
    
    private Long idmedicofk;
    private Long idpacientefk;
    private boolean is_tratamiento;
    private float val_cuotamod;
    private String fyhnuevacita;
    private boolean aten_cita;
    private byte[] firma;


    public HistorialPaciente() {
    }

    public boolean isIs_tratamiento() {
        return is_tratamiento;
    }

    public void setIs_tratamiento(boolean is_tratamiento) {
        this.is_tratamiento = is_tratamiento;
    }

    public float getVal_cuotamod() {
        return val_cuotamod;
    }

    public void setVal_cuotamod(float val_cuotamod) {
        this.val_cuotamod = val_cuotamod;
    }

    public String getFyhnuevacita() {
        return fyhnuevacita;
    }

    public void setFyhnuevacita(String fyhnuevacita) {
        this.fyhnuevacita = fyhnuevacita;
    }

    public boolean isAten_cita() {
        return aten_cita;
    }

    public void setAten_cita(boolean aten_cita) {
        this.aten_cita = aten_cita;
    }

    public byte[] getFirma() {
        return firma;
    }

    public void setFirma(byte[] firma) {
        this.firma = firma;
    }

    public Long getIdmedicofk() {
        return idmedicofk;
    }

    public void setIdmedicofk(Long idmedicofk) {
        this.idmedicofk = idmedicofk;
    }

    public Long getIdpacientefk() {
        return idpacientefk;
    }

    public void setIdpacientefk(Long idpacientefk) {
        this.idpacientefk = idpacientefk;
    }
    
    

    @Override
    public String toString() {
        return "HistorialPaciente{" + "idmedicofk=" + idmedicofk + ", idpacientefk=" + idpacientefk + ", is_tratamiento=" + is_tratamiento + ", val_cuotamod=" + val_cuotamod + ", fyhnuevacita=" + fyhnuevacita + ", aten_cita=" + aten_cita + ", firma=" + firma + '}';
    }
    
}
