package co.edu.uniquindio.sgre.controller;

import co.edu.uniquindio.sgre.controller.service.IModelFactoryController;
import co.edu.uniquindio.sgre.exceptions.EmpleadoException;
import co.edu.uniquindio.sgre.exceptions.UsuarioException;
import co.edu.uniquindio.sgre.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.sgre.mapping.dto.EventoDto;
import co.edu.uniquindio.sgre.mapping.dto.ReservaDto;
import co.edu.uniquindio.sgre.mapping.dto.UsuarioDto;
import co.edu.uniquindio.sgre.mapping.mappers.SGREMapper;
import co.edu.uniquindio.sgre.model.*;
import co.edu.uniquindio.sgre.utils.Persistencia;
import co.edu.uniquindio.sgre.utils.SGREUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelFactoryController implements IModelFactoryController {

    public void setMapper(SGREMapper mapper) {
        this.mapper = mapper;
    }

    SGRE sgre;
    SGREMapper mapper = SGREMapper.INSTANCE;

    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        //1. inicializar datos y luego guardarlo en archivos
        System.out.println("invocación clase singleton");
        //cargarDatosBase();
        //salvarDatosPrueba();

        //2. Cargar los datos de los archivos
        //cargarDatosDesdeArchivos();

        //3. Guardar y Cargar el recurso serializable binario
        cargarResourceBinario();
        guardarResourceBinario();

        guardarResourceBinarioEventos();
        guardarResourceBinarioReservas();

        //4. Guardar y Cargar el recurso serializable XML
        // cargarResourceXML();
        // guardarResourceXML();

        if (sgre == null) {
            cargarDatosBase();
            // guardarResourceXML();
        }
        registrarAccionesSistema("Inicio de sesión", 1, "inicioSesión");
    }

    private void cargarDatosDesdeArchivos() {
        sgre = new SGRE();
        try {
            Persistencia.cargarDatosArchivos(sgre);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void salvarDatosPrueba() {
        try {
            Persistencia.guardarEmpleados(getSGRE().getListaEmpleados());
            Persistencia.guardarUsuarios(getSGRE().getListaUsuarios());
            Persistencia.guardarEventos(getSGRE().getListaEventos());
            Persistencia.guardarReservas(getSGRE().getListaReservas());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarDatosBase() {
        sgre = SGREUtils.inicializarDatos();
    }

    public SGRE getSGRE() {
        return sgre;
    }

    public void setSgre(SGRE sgre) {
        this.sgre = sgre;
    }

    @Override
    public List<EmpleadoDto> obtenerEmpleados() {
        return mapper.getEmpleadosDto(sgre.getListaEmpleados());
    }

    @Override
    public boolean agregarEmpleado(EmpleadoDto empleadoDto) {
        try {
            if (!sgre.verificarEmpleadoExistente(empleadoDto.id())) {
                Empleado empleado = mapper.empleadoDtoToEmpleado(empleadoDto);
                getSGRE().agregarEmpleado(empleado);
                guardarResourceBinario();
                guardarResourceXML();
                guardarListaEmpleados(getSGRE().getListaEmpleados());
            }
            return true;
        } catch (EmpleadoException e) {
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarEmpleado(String cedula) {
        boolean flagExiste = false;
        try {
            flagExiste = getSGRE().eliminarEmpleado(cedula);
            guardarResourceBinario();
            guardarResourceXML();
        } catch (EmpleadoException e) {
            e.printStackTrace();
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarEmpleado(String cedulaActual, EmpleadoDto empleadoDto) {
        try {
            Empleado empleado = mapper.empleadoDtoToEmpleado(empleadoDto);
            getSGRE().actualizarEmpleado(cedulaActual, empleado);
            guardarResourceBinario();
            guardarResourceXML();
            return true;
        } catch (EmpleadoException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UsuarioDto> obtenerUsuario() {
        return mapper.getUsuariosDto(sgre.getListaUsuarios());
    }

    @Override
    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        try {
            if (!sgre.verificarUsuarioExistente(usuarioDto.id())) {
                Usuario usuario = mapper.usuarioToUsuarioDto(usuarioDto);
                getSGRE().agregarUsuario(usuario);
                guardarResourceBinario();
                guardarResourceXML();
                guardarListaUsuario(getSGRE().getListaUsuarios());
            }
            return true;
        } catch (UsuarioException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean eliminarUsuario(String cedula) {
        boolean flagExiste = false;
        try {
            flagExiste = getSGRE().eliminarUsuario(cedula);
            guardarResourceBinario();
            guardarResourceXML();
        } catch (UsuarioException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarUsuario(String cedulaActual, UsuarioDto usuarioDto) {
        try {
            Usuario usuario = mapper.usuarioToUsuarioDto(usuarioDto);
            getSGRE().actualizarUsuario(cedulaActual, usuario);
            guardarResourceBinario();
            guardarResourceXML();
            return true;
        } catch (UsuarioException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<EventoDto> obtenerEventos() {
        return mapper.getEventosDto(sgre.getListaEventos());
    }

    @Override
    public boolean agregarEvento(EventoDto eventoDto) {
        if (!sgre.verificarEventoExistente(eventoDto.id())) {
            Evento evento = mapper.eventoDtoToEvento(eventoDto);
            getSGRE().agregarEvento(evento);
            guardarResourceBinario();
            guardarResourceXML();
            guardarListaEventos(getSGRE().getListaEventos());
        }
        return true;
    }

    @Override
    public boolean eliminarEvento(String id) {
        boolean flagExiste = false;
        try {
            flagExiste = getSGRE().eliminarEvento(id);
            guardarResourceBinario();
            guardarResourceXML();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarEvento(String idActual, EventoDto eventoDto) {
        try {
            Evento evento = mapper.eventoDtoToEvento(eventoDto);
            getSGRE().actualizarEvento(idActual, evento);
            guardarResourceBinario();
            guardarResourceXML();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ReservaDto> obtenerReservas() {
        return mapper.getReservasDto(sgre.getListaReservas());
    }

    @Override
    public boolean agregarReserva(ReservaDto reservaDto) {
        try {
            if (!sgre.verificarReservaExistente(reservaDto.id())) {
                Reserva reserva = mapper.reservaDtoToReserva(reservaDto);
                sgre.agregarReserva(reserva);
                guardarResourceBinario();
                guardarResourceXML();
                guardarListaReservas(sgre.getListaReservas());
            }
            return true;
        } catch (EmpleadoException e) {
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarReserva(String id) {
        boolean flagExiste = false;
        try {
            flagExiste = sgre.eliminarReserva(id);
            guardarResourceBinario();
            guardarResourceXML();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarReserva(String idActual, ReservaDto reservaDto) {
        try {
            Reserva reserva = mapper.reservaDtoToReserva(reservaDto);
            sgre.actualizarReserva(idActual, reserva);
            guardarResourceBinario();
            guardarResourceXML();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void guardarResourceBinario() {
        Thread thread = new Thread(() -> {
            Persistencia.guardarRecursoBancoBinario(sgre);
        });
        thread.start();
    }

    private void guardarResourceBinarioEventos() {
        Thread thread = new Thread(() -> {
            try {
                Persistencia.guardarEventos(getSGRE().getListaEventos());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void guardarResourceBinarioReservas() {
        Thread thread = new Thread(() -> {
            try {
                Persistencia.guardarReservas(getSGRE().getListaReservas());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void guardarListaEmpleados(ArrayList<Empleado> listaEmpleados) {
        Thread thread = new Thread(() -> {
            try {
                Persistencia.guardarEmpleados(listaEmpleados);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void guardarListaUsuario(ArrayList<Usuario> listaUsuario) {
        Thread thread = new Thread(() -> {
            try {
                Persistencia.guardarUsuarios(listaUsuario);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void guardarListaEventos(ArrayList<Evento> listaEventos) {
        Thread thread = new Thread(() -> {
            try {
                Persistencia.guardarEventos(listaEventos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void guardarListaReservas(ArrayList<Reserva> listaReservas) {
        Thread thread = new Thread(() -> {
            try {
                Persistencia.guardarReservas(listaReservas);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void cargarResourceXML() {
        sgre = Persistencia.cargarRecursoBancoXML();
    }

    private void guardarResourceXML() {
        Thread thread = new Thread(() -> {
            Persistencia.guardarRecursoBancoXML(sgre);
        });
        thread.start();
    }

    private void cargarResourceBinario() {
        sgre = Persistencia.cargarRecursoBancoBinario();
    }

    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }
}
