/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import pojos.Medico;

/**
 *
 * @author johnm
 */
public class MedicoBD {

    static public void addMedico(Medico registro) throws Exception {

        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = ConexionBD.getConexion();
            String str;
            if (registro.getIdmedico()!= null) {
                str = "INSERT INTO public.medico(\n" +
            "            idmedico, cod_tarjetapro, especialidad, \"año_experiencia\", consultorio, \n" +
            "            aten_domicilio)\n" +
            "    VALUES ("+registro.getIdmedico()+",'"+registro.getCod_tarjetapro()+"','"+registro.getEspecialidad()+"'"
                        + ","+registro.getAño_experiencia()+",'"+registro.getConsultorio()+"', \n" +
            "            "+registro.isAten_domicilio()+");";
            } else {
                str = "INSERT INTO public.medico(\n" +
            "            cod_tarjetapro, especialidad, \"año_experiencia\", consultorio, \n" +
            "            aten_domicilio)\n" +
            "    VALUES ('"+registro.getCod_tarjetapro()+"','"+registro.getEspecialidad()+"'"
                        + ","+registro.getAño_experiencia()+",'"+registro.getConsultorio()+"', \n" +
            "            "+registro.isAten_domicilio()+");";
            }

            ps = conexion.prepareStatement(str);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("ErrorBDmedico"+ e.getMessage());
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
    
    static public void updatePaciente(Medico registro) throws Exception {
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = ConexionBD.getConexion();
            String str = "UPDATE public.medico\n" +
            "   SET cod_tarjetapro='"+registro.getCod_tarjetapro()+"', especialidad='"+registro.getEspecialidad()+"', \"año_experiencia\"="+registro.getAño_experiencia()+", \n" +
            "       consultorio='"+registro.getConsultorio()+"', aten_domicilio='"+registro.isAten_domicilio()+"'\n" +
            " WHERE idmedico="+registro.getIdmedico()+";";
                    
            ps = conexion.prepareStatement(str);

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
