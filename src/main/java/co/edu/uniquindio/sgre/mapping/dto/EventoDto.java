package co.edu.uniquindio.sgre.mapping.dto;

import co.edu.uniquindio.sgre.model.Empleado;

import java.time.LocalDate;

public record EventoDto (
        String id,
        String nombre,
        String descripcion,
        LocalDate fecha,
        String capMax,
        Empleado empleadoAsignadoId) {


}