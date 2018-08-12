/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import pojos.HistorialPaciente;


/**
 *
 * @author johnm
 */
public class HistorialPacienteBD {

    static public void addHPaciente(HistorialPaciente registro) throws Exception {

        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = ConexionBD.getConexion();
            String str;
            if ((registro.getIdmedicofk() != null)&&(registro.getIdpacientefk()!= null)) {
                str = "INSERT INTO public.historial_paciente(\n" +
                "            idmedicofk, idpacientefk, is_tratamiento, val_cuotamod, fyhnuevacita, \n" +
                "            aten_cita, firma)\n" +
                "    VALUES ("+registro.getIdmedicofk()+","+registro.getIdpacientefk()+",'"+registro.isIs_tratamiento()+"',"+registro.getVal_cuotamod()+","
                               + "'"+registro.getFyhnuevacita()+"', \n" +
                "            '"+registro.isAten_cita()+"', '"+registro.getFirma()+"');";
            } else {
                str = "INSERT INTO public.historial_paciente(\n" +
                "            idmedicofk, idpacientefk, is_tratamiento, val_cuotamod, fyhnuevacita, \n" +
                "            aten_cita, firma)\n" +
                "    VALUES ("+registro.getIdmedicofk()+","+registro.getIdpacientefk()+",'"+registro.isIs_tratamiento()+"',"+registro.getVal_cuotamod()+","
                               + "'"+registro.getFyhnuevacita()+"', \n" +
                "            '"+registro.isAten_cita()+"', '"+registro.getFirma()+"');";
            }

            ps = conexion.prepareStatement(str);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("ErrorBDhistorial"+ e.getMessage());
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }
    
     static public void updatePaciente(HistorialPaciente registro) throws Exception {
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = ConexionBD.getConexion();
            String str = "UPDATE public.historial_paciente\n" +
            "   SET is_tratamiento= '"+registro.isIs_tratamiento()+"', val_cuotamod= "+registro.getVal_cuotamod()+", \n" +
            "       fyhnuevacita='"+registro.getFyhnuevacita()+"', aten_cita='"+registro.isAten_cita()+"', firma='"+registro.getFirma()+"'\n" +
            " WHERE idmedicofk="+registro.getIdmedicofk()+" AND idpacientefk="+registro.getIdpacientefk()+";";
                    
            ps = conexion.prepareStatement(str);
            System.out.println("bd.PacienteBD.addPaciente() actuliazo"+registro.getIdpacientefk());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("bd.PacienteBD.addPaciente()" + e.getMessage());
            throw e;
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conexion != null) conexion.close();
        }
    }
  
}
