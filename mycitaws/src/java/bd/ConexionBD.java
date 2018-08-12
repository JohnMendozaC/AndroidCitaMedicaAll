/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author John Mendoza
 */
public class ConexionBD {
    public static final String url = "jdbc:postgresql://35.232.205.145:5432/citadb";
    public static final String user = "postgres";
    public static final String password = "";
    
    public static Connection getConexion() throws Exception{
        Connection conexion = null;
        try {
          Class.forName("org.postgresql.Driver");
          conexion = DriverManager.getConnection(url, user, password);
          
        } catch (Exception ex) { 
            throw(ex);
        } 
        return conexion; 
        
    }
    
    
}
