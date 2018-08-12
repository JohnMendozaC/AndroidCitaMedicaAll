/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;



import bd.HistorialPacienteBD;
import com.google.gson.Gson;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import pojos.HistorialPaciente;



/**
 *
 * @author johnm
 */
@Path("historialpaciente")
public class HistorialPacienteResource {
    
    @Context
    private UriInfo context;

    public HistorialPacienteResource() {
    }
    
    
        // Añadir Registro. 
    @POST
    @Path("newhistorial")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCiclo(HistorialPaciente registro) {
        try {
            HistorialPacienteBD.addHPaciente(registro);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha añadido - addCiclo " + registro.getIdmedicofk());
            String json = "{ \"id\": \"" + String.valueOf(registro.getIdmedicofk()) + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
                return Response.status(Response.Status.SEE_OTHER).entity("No se pudo Insertar Registro: " + registro.getIdmedicofk()).build();
        }
    }
    
    
    
}
