/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import bd.HistorialPacienteBD;
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
import pojos.Citas;
import pojos.HistorialPaciente;
import pojos.Medico;
import pojos.Paciente;

/**
 *
 * @author johnm
 */
@Path("medico")
public class MedicoResource {

    @Context
    private UriInfo context;

    public MedicoResource() {
    }

    // Añadir Registro. 
    @POST
    @Path("newmedico")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCiclo(Medico registro) {
        try {
            MedicoBD.addMedico(registro);
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se ha añadido - addCiclo " + registro.getIdmedico());
            String json = "{ \"id\": \"" + String.valueOf(registro.getIdmedico()) + "\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo Insertar Registro: " + registro.getIdmedico()).build();
        }
    }

    @POST
    @Path("allcita")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCiclos(Citas cita) {
        try {
            for (Medico med : cita.getMedicos()) {
                try {
                    MedicoBD.addMedico(med);
                } catch (Exception e) {
                    System.out.println("Actulizar medico");
                    MedicoBD.updatePaciente(med);
                }

            }

            for (Paciente pac : cita.getPacientes()) {
                try {
                    PacienteBD.addPaciente(pac);
                } catch (Exception e) {
                    System.out.println("Actulizar paciente");
                    PacienteBD.updatePaciente(pac);
                }

            }

            for (HistorialPaciente hpac : cita.getHpacientes()) {
                try {
                    HistorialPacienteBD.addHPaciente(hpac);
                } catch (Exception e) {
                    HistorialPacienteBD.updatePaciente(hpac);
                }

            }
            Date fecha = new Date();
            System.out.println(fecha.toString() + ": Se registraron todos ");
            String json = "{ \"id\": \"allRegistros\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();

        } catch (Exception ex) {
            return Response.status(Response.Status.SEE_OTHER).entity("No se pudo Insertar Registro: ").build();
        }
    }

}
