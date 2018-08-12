package com.compilado.johnm.citamedica2.remote;


import com.compilado.johnm.citamedica2.persistence.vo.HistorialPaciente;
import com.compilado.johnm.citamedica2.persistence.vo.Medico;
import com.compilado.johnm.citamedica2.persistence.vo.Paciente;
import com.compilado.johnm.citamedica2.remote.pojo.Citas;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface APIServiceC {


    @Headers("Content-Type: application/json")
    @POST("paciente/newpaciente")
    Call<Paciente> createPaciente(@Body Paciente paciente);

    @Headers("Content-Type: application/json")
    @POST("medico/newmedico")
    Call<Medico> createMedico(@Body Medico medico);


    @Headers("Content-Type: application/json")
    @POST("historialpaciente/newhistorial")
    Call<HistorialPaciente> createHistorial(@Body HistorialPaciente historial);

    @Headers("Content-Type: application/json")
    @POST("medico/allcita")
    Call<Citas> uploadData(@Body Citas citas);

}
