/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;



import bd.MedicoBD;
import bd.PacienteBD;
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
import pojos.Medico;
import pojos.Paciente;


/**
 *
 * @author johnm
 */
@Path("paciente")
public class PacienteResource {
    
    @Context
    private UriInfo context;

    public PacienteResource() {
    }
    
    
        // Añadir Registro. 
    @POST
    @Path("newpaciente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCiclo(Paciente registro) {
        try {
            PacienteBD.addPaciente(registro);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha añadido - addCiclo " + registro.getIdpaciente());
            String json = "{ \"id\": \"" + String.valueOf(registro.getIdpaciente()) + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
                return Response.status(Response.Status.SEE_OTHER).entity("No se pudo Insertar Registro: " + registro.getIdpaciente()).build();
        }
    }
    
}
