package co.edu.uniquindio.parfin.controller.service;

import co.edu.uniquindio.parfin.mapping.dto.EmpleadoDto;

import java.util.List;

public interface IEmpladoControllerService {

    List<EmpleadoDto> obtenerEmpleados();

    boolean agregarEmpleado(EmpleadoDto empleadoDto);

    boolean eliminarEmpleado(String id);

    boolean actualizarEmpleado(String cedulaActual, EmpleadoDto empleadoDto);

}