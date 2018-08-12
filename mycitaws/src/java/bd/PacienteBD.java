/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import pojos.Paciente;

/**
 *
 * @author johnm
 */
public class PacienteBD {

    static public void addPaciente(Paciente registro) throws Exception {

        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = ConexionBD.getConexion();
            String str;
            if (registro.getIdpaciente()!= null) {
                str = "INSERT INTO public.paciente(\n" +
                "            idpaciente, nombre, apellidos, fecha_nacimiento, identificacion)\n" +
                "    VALUES ("+registro.getIdpaciente()+",'"+registro.getNombre()+"','"+registro.getApellidos()+"'"
                        + ",'"+registro.getFecha_nacimiento()+"','"+registro.getIdentificacion()+"');";
            } else {
                str = "INSERT INTO public.paciente(\n" +
                "            idpaciente, nombre, apellidos, fecha_nacimiento, identificacion)\n" +
                "    VALUES ("+registro.getIdpaciente()+",'"+registro.getNombre()+"','"+registro.getApellidos()+"'"
                        + ",'"+registro.getFecha_nacimiento()+"','"+registro.getIdentificacion()+"');";
            }

            ps = conexion.prepareStatement(str);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("bd.PacienteBD.addPaciente()" + e.getMessage());
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
    
    static public void updatePaciente(Paciente registro) throws Exception {
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = ConexionBD.getConexion();
            String str = "UPDATE public.paciente\n" +
            "   SET nombre='"+registro.getNombre()+"', apellidos='"+registro.getApellidos()+"', fecha_nacimiento='"+registro.getFecha_nacimiento()+"'"
                    + ", identificacion='"+registro.getIdentificacion()+"'\n" +
            " WHERE idpaciente="+registro.getIdpaciente()+";";
                    
            ps = conexion.prepareStatement(str);

            System.out.println("bd.PacienteBD.addPaciente() actuliazo"+registro.getIdpaciente());
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
