package co.edu.uniquindio.sgre.utils;



//import co.edu.uniquindio.banco.bancouq.exceptions.UsuarioExcepcion;
//import co.edu.uniquindio.banco.bancouq.model.*;

import co.edu.uniquindio.sgre.exceptions.UsuarioException;
import co.edu.uniquindio.sgre.model.*;

import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;




public class Persistencia {


    //bancoUq/src/main/resources/persistencia/archivoClientes.txt

    public static final String RUTA_ARCHIVO_EMPLEADOS = "src/main/resources/persistencia/archivoEmpleados.txt";
    public static final String RUTA_ARCHIVO_USUARIOS = "src/main/resources/persistencia/archivoUsuarios.txt";
    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/SGRELog.txt";
    public static final String RUTA_ARCHIVO_MODELO_BANCO_BINARIO = "src/main/resources/persistencia/mod.dat";
    public static final String RUTA_ARCHIVO_MODELO_BANCO_XML = "src/main/resources/persistencia/model.xml";
    public static final String RUTA_ARCHIVO_EVENTOS_BINARIO = "src/main/resources/persistencia/archivoEventos.dat";
    public static final String RUTA_ARCHIVO_RESERVA_BINARIO = "src/main/resources/persistencia/archivoReservas.dat";

    //	C:\td\persistencia



    public static void cargarDatosArchivos(SGRE sgre) throws FileNotFoundException, IOException {

        //cargar archivos empleados
        ArrayList<Empleado> empleadosCargados = cargarEmpleados();
        if (empleadosCargados.size() > 0)
            sgre.getListaEmpleados().addAll(empleadosCargados);

        ArrayList<Usuario> usuariosCargados = cargarUsuarios();
        if (usuariosCargados.size() > 0)
            sgre.getListaUsuarios().addAll(usuariosCargados);

        ArrayList<Evento> eventosCargados = cargarEventos();
        if (eventosCargados.size() > 0) {
            sgre.getListaEventos().addAll(eventosCargados);
            System.out.println("Eventos cargados exitosamente: " + eventosCargados.size());
        } else {
            System.out.println("No se encontraron eventos para cargar.");
        }
        ArrayList<Reserva> reservasCargadas = cargarReservas();
        if (reservasCargadas.size() > 0) {
            sgre.getListaReservas().addAll(reservasCargadas);
            System.out.println("Reservas cargadas exitosamente: " + reservasCargadas.size());
        } else {
            System.out.println("No se encontraron reservas para cargar.");
        }
    }




    /**
     * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
     * @param
     * @param
     * @throws IOException
     */
/*
    public static void guardarEmpleados(ArrayList<Empleado> listaEmpleados) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (Empleado empleado : listaEmpleados) {
            contenido.append(empleado.getId())
                    .append("@@").append(empleado.getNombre())
                    .append("@@").append(empleado.getEmail())
                    .append("@@").append(empleado.getUsuario())
                    .append("@@").append(empleado.getContrasenia())
                    .append("\n");
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_EMPLEADOS, contenido.toString(), false);
    }

 */


    public static void guardarUsuarios(ArrayList<Usuario> listaUsuarios) throws IOException {
        String contenido = "";
        for(Usuario usuario:listaUsuarios)
        {
            contenido+= usuario.getId()+
                    "@@"+usuario.getNombre()+
                    "@@"+usuario.getEmail()+
                    "@@"+usuario.getUsuario()+
                    "@@"+usuario.getContrasenia()+"\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_USUARIOS, contenido, false);
    }



//	----------------------LOADS------------------------

    /**
     *
     * @param
     * @param
     * @return un Arraylist de personas con los datos obtenidos del archivo de texto indicado
     * @throws FileNotFoundException
     * @throws IOException
     */


    public static void guardarEmpleados(ArrayList<Empleado> listaEmpleados) throws IOException {
        String contenido = "";
        for (Empleado empleado : listaEmpleados) {
            contenido += empleado.getId() + "@@" +
                    empleado.getNombre() + "@@" +
                    empleado.getEmail() + "@@" +
                    empleado.getUsuario() + "@@" +
                    empleado.getContrasenia() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_EMPLEADOS, contenido, false);
    }


    public static ArrayList<Empleado> cargarEmpleados() throws FileNotFoundException, IOException {
        ArrayList<Empleado> empleados = new ArrayList<>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_EMPLEADOS);
        for (String linea : contenido) {
            String[] datos = linea.split("@@");
            Empleado empleado = new Empleado();
            empleado.setId(datos[0]);
            empleado.setNombre(datos[1]);
            empleado.setEmail(datos[2]);
            empleado.setUsuario(datos[3]);
            empleado.setContrasenia(datos[4]);
            empleados.add(empleado);
        }
        return empleados;
    }


    public static ArrayList<Usuario> cargarUsuarios() throws FileNotFoundException, IOException {
        ArrayList<Usuario> empleados =new ArrayList<Usuario>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_USUARIOS);
        String linea="";
        for (int i = 0; i < contenido.size(); i++)
        {
            linea = contenido.get(i);
            Usuario empleado = new Usuario();
            empleado.setId(linea.split("@@")[0]);
            empleado.setNombre(linea.split("@@")[1]);
            empleado.setEmail(linea.split("@@")[2]);
            empleado.setUsuario(linea.split("@@")[3]);
            empleado.setContrasenia(linea.split("@@")[4]);
            empleados.add(empleado);
        }
        return empleados;
    }


    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion)
    {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }

/*
    public static boolean iniciarSesion(String usuario, String contrasenia) throws FileNotFoundException, IOException, UsuarioException {

        if(validarUsuario(usuario,contrasenia)) {
            return true;
        }else {
            throw new UsuarioException("Usuario no existe");
        }

    }

 */

   /* private static boolean validarUsuario(String usuario, String contrasenia) throws FileNotFoundException, IOException
    {
        ArrayList<Usuario> usuarios = Persistencia.cargarUsuarios(RUTA_ARCHIVO_USUARIOS);

        for (int indiceUsuario = 0; indiceUsuario < usuarios.size(); indiceUsuario++)
        {
            Usuario usuarioAux = usuarios.get(indiceUsuario);
            if(usuarioAux.getUsuario().equalsIgnoreCase(usuario) && usuarioAux.getContrasenia().equalsIgnoreCase(contrasenia)) {
                return true;
            }
        }
        return false;
    }

    */
/*
    public static ArrayList<Usuario> cargarUsuarios(String ruta) throws FileNotFoundException, IOException {
        ArrayList<Usuario> usuarios =new ArrayList<Usuario>();

        ArrayList<String> contenido = ArchivoUtil.leerArchivo(ruta);
        String linea="";

        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);

            Usuario usuario = new Usuario();
            usuario.setUsuario(linea.split(",")[0]);
            usuario.setContrasenia(linea.split(",")[1]);

            usuarios.add(usuario);
        }
        return usuarios;
    }


//	----------------------SAVES------------------------

    /**
     * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
     * @param
     * @param ruta
     * @throws IOException
     */
/*
    public static void guardarObjetos(ArrayList<Cliente> listaClientes, String ruta) throws IOException  {
        String contenido = "";

        for(Cliente clienteAux:listaClientes) {
            contenido+= clienteAux.getNombre()+","+clienteAux.getApellido()+","+clienteAux.getCedula()+clienteAux.getDireccion()
                    +","+clienteAux.getCorreo()+","+clienteAux.getFechaNacimiento()+","+clienteAux.getTelefono()+"\n";
        }
        ArchivoUtil.guardarArchivo(ruta, contenido, true);
    }

 */






    //------------------------------------SERIALIZACIÓN  y XML


    public static SGRE cargarRecursoBancoBinario() {

        SGRE sgre = null;

        try {
            sgre = (SGRE) ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_BANCO_BINARIO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sgre;
    }

    public static void guardarRecursoBancoBinario(SGRE sgre) {
        try {
            ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_BANCO_BINARIO, sgre);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static SGRE cargarRecursoBancoXML() {

        SGRE sgre = null;

        try {
            sgre = (SGRE) ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_BANCO_XML);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sgre;

    }



    public static void guardarRecursoBancoXML(SGRE sgre) {

        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_BANCO_XML, sgre);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    ///////////////////////////////////////////////////////////////


    public static void eliminarEmpleado(String idEmpleado) throws IOException {
        ArrayList<Empleado> empleados = cargarEmpleados();
        Empleado empleadoAEliminar = null;
        for (Empleado empleado : empleados) {
            if (empleado.getId().equals(idEmpleado)) {
                empleadoAEliminar = empleado;
                break;
            }
        }
        if (empleadoAEliminar != null) {
            empleados.remove(empleadoAEliminar);
            guardarEmpleados(empleados);
            System.out.println("Empleado eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún empleado con el ID especificado.");
        }
    }
    public static void eliminarUsuario(String id) throws IOException {
        ArrayList<Usuario> usuarios = cargarUsuarios();
        Usuario usuarioAEliminar = null;
        for (Usuario empleado : usuarios) {
            if (empleado.getId().equals(id)) {
                usuarioAEliminar = empleado;
                break;
            }
        }
        if (usuarioAEliminar != null) {
            usuarios.remove(usuarioAEliminar);
            guardarUsuarios(usuarios);
            System.out.println("Empleado eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún empleado con el ID especificado.");
        }


    }

    public static void eliminarEmpleadoBinario(String idEmpleado) throws IOException {
        SGRE sgre = cargarRecursoBancoBinario();
        if (sgre != null) {
            ArrayList<Empleado> empleados = sgre.getListaEmpleados();
            Empleado empleadoAEliminar = null;
            for (Empleado empleado : empleados) {
                if (empleado.getId().equals(idEmpleado)) {
                    empleadoAEliminar = empleado;
                    break;
                }
            }
            if (empleadoAEliminar != null) {
                empleados.remove(empleadoAEliminar);
                guardarRecursoBancoBinario(sgre);
                System.out.println("Empleado eliminado correctamente del archivo binario.");
            } else {
                System.out.println("No se encontró ningún empleado con el ID especificado en el archivo binario.");
            }
        } else {
            System.out.println("No se pudo cargar el archivo binario.");
        }
    }
    public static void eliminarUsuarioBinario(String id){
        SGRE sgre = cargarRecursoBancoBinario();
        if (sgre != null) {
            ArrayList<Usuario> empleados = sgre.getListaUsuarios();
            Usuario usuarioAEliminar = null;
            for (Usuario empleado : empleados) {
                if (empleado.getId().equals(id)) {
                    usuarioAEliminar = empleado;
                    break;
                }
            }
            if (usuarioAEliminar != null) {
                empleados.remove(usuarioAEliminar);
                guardarRecursoBancoBinario(sgre);
                System.out.println("Empleado eliminado correctamente del archivo binario.");
            } else {
                System.out.println("No se encontró ningún empleado con el ID especificado en el archivo binario.");
            }
        } else {
            System.out.println("No se pudo cargar el archivo binario.");
        }
    }

    public static void eliminarEmpleadoXML(String idEmpleado) throws IOException {
        SGRE sgre = cargarRecursoBancoXML();
        if (sgre != null) {
            ArrayList<Empleado> empleados = sgre.getListaEmpleados();
            Empleado empleadoAEliminar = null;
            for (Empleado empleado : empleados) {
                if (empleado.getId().equals(idEmpleado)) {
                    empleadoAEliminar = empleado;
                    break;
                }
            }
            if (empleadoAEliminar != null) {
                empleados.remove(empleadoAEliminar);
                guardarRecursoBancoXML(sgre);
                System.out.println("Empleado eliminado correctamente del archivo XML.");
            } else {
                System.out.println("No se encontró ningún empleado con el ID especificado en el archivo XML.");
            }
        } else {
            System.out.println("No se pudo cargar el archivo XML.");
        }
    }
    public static void eliminarUsuarioXML(String id){
        SGRE sgre = cargarRecursoBancoXML();
        if (sgre != null) {
            ArrayList<Usuario> empleados = sgre.getListaUsuarios();
            Usuario usuarioAEliminar = null;
            for (Usuario empleado : empleados) {
                if (empleado.getId().equals(id)) {
                    usuarioAEliminar = empleado;
                    break;
                }
            }
            if (usuarioAEliminar != null) {
                empleados.remove(usuarioAEliminar);
                guardarRecursoBancoXML(sgre);
                System.out.println("Empleado eliminado correctamente del archivo XML.");
            } else {
                System.out.println("No se encontró ningún empleado con el ID especificado en el archivo XML.");
            }
        } else {
            System.out.println("No se pudo cargar el archivo XML.");
        }
    }

    public static void actualizarEmpleadoBinario(String idEmpleado, Empleado empleadoActualizado) throws IOException {
        SGRE sgre = cargarRecursoBancoBinario();
        if (sgre != null) {
            ArrayList<Empleado> empleados = sgre.getListaEmpleados();
            for (int i = 0; i < empleados.size(); i++) {
                if (empleados.get(i).getId().equals(idEmpleado)) {
                    empleados.set(i, empleadoActualizado);
                    guardarRecursoBancoBinario(sgre);
                    System.out.println("Empleado actualizado correctamente en el archivo binario.");
                    return;
                }
            }
            System.out.println("No se encontró ningún empleado con el ID especificado en el archivo binario.");
        } else {
            System.out.println("No se pudo cargar el archivo binario.");
        }
    }
    public static void actualizarUsuarioBinario(String id, Usuario empleadoActualizado){
        SGRE sgre = cargarRecursoBancoBinario();
        if (sgre != null) {
            ArrayList<Usuario> empleados = sgre.getListaUsuarios();
            for (int i = 0; i < empleados.size(); i++) {
                if (empleados.get(i).getId().equals(id)) {
                    empleados.set(i, empleadoActualizado);
                    guardarRecursoBancoBinario(sgre);
                    System.out.println("Empleado actualizado correctamente en el archivo binario.");
                    return;
                }
            }
            System.out.println("No se encontró ningún empleado con el ID especificado en el archivo binario.");
        } else {
            System.out.println("No se pudo cargar el archivo binario.");
        }

    }

    public static void actualizarEmpleadoXML(String idEmpleado, Empleado empleadoActualizado) throws IOException {
        SGRE sgre = cargarRecursoBancoXML();
        if (sgre != null) {
            ArrayList<Empleado> empleados = sgre.getListaEmpleados();
            for (int i = 0; i < empleados.size(); i++) {
                if (empleados.get(i).getId().equals(idEmpleado)) {
                    empleados.set(i, empleadoActualizado);
                    guardarRecursoBancoXML(sgre);
                    System.out.println("Empleado actualizado correctamente en el archivo XML.");
                    return;
                }
            }
            System.out.println("No se encontró ningún empleado con el ID especificado en el archivo XML.");
        } else {
            System.out.println("No se pudo cargar el archivo XML.");
        }
    }
    public static void actualizarUsuarioXML(String idUsuario, Usuario empleadoActualizado){
        SGRE sgre = cargarRecursoBancoXML();
        if (sgre != null) {
            ArrayList<Usuario> empleados = sgre.getListaUsuarios();
            for (int i = 0; i < empleados.size(); i++) {
                if (empleados.get(i).getId().equals(idUsuario)) {
                    empleados.set(i, empleadoActualizado);
                    guardarRecursoBancoXML(sgre);
                    System.out.println("Empleado actualizado correctamente en el archivo XML.");
                    return;
                }
            }
            System.out.println("No se encontró ningún empleado con el ID especificado en el archivo XML.");
        } else {
            System.out.println("No se pudo cargar el archivo XML.");
        }
    }

    public static void actualizarEmpleadoTxt(String idEmpleado, Empleado empleadoActualizado) throws IOException {
        ArrayList<Empleado> empleados = cargarEmpleados();
        boolean empleadoEncontrado = false;

        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getId().equals(idEmpleado)) {
                empleados.set(i, empleadoActualizado);
                empleadoEncontrado = true;
                break;
            }
        }

        if (empleadoEncontrado) {
            guardarEmpleados(empleados);
            System.out.println("Empleado actualizado correctamente en el archivo de texto.");
        } else {
            System.out.println("No se encontró ningún empleado con el ID especificado en el archivo de texto.");


        }
    }

        public static void actualizarUsuarioTxt(String idUsuario, Usuario empleadoActualizado) throws IOException {
            ArrayList<Usuario> empleados = cargarUsuarios();
            boolean empleadoEncontrado = false;

            for (int i = 0; i < empleados.size(); i++) {
                if (empleados.get(i).getId().equals(idUsuario)) {
                    empleados.set(i, empleadoActualizado);
                    empleadoEncontrado = true;
                    break;
                }
            }

            if (empleadoEncontrado) {
                guardarUsuarios(empleados);
                System.out.println("Empleado actualizado correctamente en el archivo de texto.");
            } else {
                System.out.println("No se encontró ningún empleado con el ID especificado en el archivo de texto.");
            }

    }

    public static ArrayList<Evento> cargarEventos() throws IOException {
        ArrayList<Evento> eventos = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(RUTA_ARCHIVO_EVENTOS_BINARIO))) {
            eventos = (ArrayList<Evento>) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return eventos;
    }



    public static void guardarEventos(ArrayList<Evento> listaEventos) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO_EVENTOS_BINARIO))) {
            oos.writeObject(listaEventos);
        }
    }

    public static void eliminarEvento(String idEvento) throws IOException {
        ArrayList<Evento> eventos = cargarEventos();
        Evento eventoAEliminar = null;
        for (Evento evento : eventos) {
            if (evento.getId().equals(idEvento)) {
                eventoAEliminar = evento;
                break;
            }
        }
        if (eventoAEliminar != null) {
            eventos.remove(eventoAEliminar);
            guardarEventos(eventos);
            System.out.println("Evento eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún evento con el ID especificado.");
        }
    }

    public static void actualizarEvento(String idEvento, Evento eventoActualizado) throws IOException {
        ArrayList<Evento> eventos = cargarEventos();
        for (int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i).getId().equals(idEvento)) {
                eventos.set(i, eventoActualizado);
                guardarEventos(eventos);
                System.out.println("Evento actualizado correctamente.");
                return;
            }
        }
        System.out.println("No se encontró ningún evento con el ID especificado.");
    }

    public static ArrayList<Reserva> cargarReservas() throws IOException {
        ArrayList<Reserva> reservas = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA_ARCHIVO_RESERVA_BINARIO))) {
            reservas = (ArrayList<Reserva>) ois.readObject();
            System.out.println("Reservas cargadas exitosamente.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("El archivo de reservas no existe, creando uno nuevo.");
        }
        return reservas;
    }

    public static void guardarReservas(ArrayList<Reserva> listaReservas) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO_RESERVA_BINARIO))) {
            System.out.println("Escribiendo reservas al archivo...");
            oos.writeObject(listaReservas);
            System.out.println("Reservas escritas exitosamente en el archivo.");
        }
    }


    public static void eliminarReserva(String idReserva) throws IOException {
        ArrayList<Reserva> reservas = cargarReservas();
        Reserva reservaAEliminar = null;
        for (Reserva reserva : reservas) {
            if (reserva.getId().equals(idReserva)) {
                reservaAEliminar = reserva;
                break;
            }
        }
        if (reservaAEliminar != null) {
            reservas.remove(reservaAEliminar);
            guardarReservas(reservas);
            System.out.println("Reserva eliminada correctamente.");
        } else {
            System.out.println("No se encontró ninguna reserva con el ID especificado.");
        }
    }

    public static void actualizarReserva(String idReserva, Reserva reservaActualizada) throws IOException {
        ArrayList<Reserva> reservas = cargarReservas();
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getId().equals(idReserva)) {
                reservas.set(i, reservaActualizada);
                guardarReservas(reservas);
                System.out.println("Reserva actualizada correctamente.");
                return;
            }
        }
        System.out.println("No se encontró ninguna reserva con el ID especificado.");
    }


}












