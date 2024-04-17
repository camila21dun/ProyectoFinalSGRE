package co.edu.uniquindio.sgre.utils;



//import co.edu.uniquindio.banco.bancouq.exceptions.UsuarioExcepcion;
//import co.edu.uniquindio.banco.bancouq.model.*;

import co.edu.uniquindio.sgre.exceptions.UsuarioException;
import co.edu.uniquindio.sgre.model.Empleado;
import co.edu.uniquindio.sgre.model.SGRE;

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
  //  public static final String RUTA_ARCHIVO_USUARIOS = "/src/main/resources/persistencia/archivoUsuarios.txt";
    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/SGRELog.txt";
    public static final String RUTA_ARCHIVO_MODELO_BANCO_BINARIO = "src/main/resources/persistencia/mod.dat";
    public static final String RUTA_ARCHIVO_MODELO_BANCO_XML = "src/main/resources/persistencia/model.xml";
//	C:\td\persistencia



    public static void cargarDatosArchivos(SGRE sgre) throws FileNotFoundException, IOException {

        //cargar archivos empleados
        ArrayList<Empleado> empleadosCargados = cargarEmpleados();
        if(empleadosCargados.size() > 0)
            sgre.getListaEmpleados().addAll(empleadosCargados);

        //cargar archivo transcciones

        //cargar archivo empleados

        //cargar archivo prestamo

    }

    /**
     * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
     * @param
     * @param
     * @throws IOException
     */

    public static void guardarEmpleados(ArrayList<Empleado> listaEmpleados) throws IOException {
        String contenido = "";
        for(Empleado empleado:listaEmpleados)
        {
            contenido+= empleado.getId()+
                    "@@"+empleado.getNombre()+
                    "@@"+empleado.getEmail()+ "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_EMPLEADOS, contenido, false);
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


    public static ArrayList<Empleado> cargarEmpleados() throws FileNotFoundException, IOException {
        ArrayList<Empleado> empleados =new ArrayList<Empleado>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_EMPLEADOS);
        String linea="";
        for (int i = 0; i < contenido.size(); i++)
        {
            linea = contenido.get(i);
            Empleado empleado = new Empleado();
            empleado.setId(linea.split("@@")[0]);
            empleado.setNombre(linea.split("@@")[1]);
            empleado.setEmail(linea.split("@@")[2]);
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
            // TODO Auto-generated catch block
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











}