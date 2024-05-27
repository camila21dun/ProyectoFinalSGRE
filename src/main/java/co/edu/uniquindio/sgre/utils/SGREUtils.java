package co.edu.uniquindio.sgre.utils;

import co.edu.uniquindio.sgre.model.*;

import java.time.LocalDate;

public class SGREUtils {

    public static SGRE inicializarDatos() {
        SGRE sgre = new SGRE();

        Empleado empleado1 = new Empleado();
        empleado1.setNombre("Julieta");
        empleado1.setId("13579");
        empleado1.setEmail("cam21dun@gmail.com");
        empleado1.setUsuario("juli");
        empleado1.setContrasenia("1234");
        sgre.getListaEmpleados().add(empleado1);

        Empleado empleado2 = new Empleado();
        empleado2.setNombre("Ana");
        empleado2.setId("12545");
        empleado2.setEmail("cam21dun@gmail.com");
        empleado2.setUsuario("Pablo");
        empleado2.setContrasenia("12323");
        sgre.getListaEmpleados().add(empleado2);

        Empleado empleado3 = new Empleado();
        empleado3.setNombre("Alejandro");
        empleado3.setId("58388");
        empleado3.setEmail("cam21dun@gmail.com");
        empleado3.setUsuario("Alex");
        empleado3.setContrasenia("76549");
        sgre.getListaEmpleados().add(empleado3);

        Empleado empleado4 = new Empleado();
        empleado4.setNombre("Juan");
        empleado4.setId("12544");
        empleado4.setEmail("cam21dun@gmail.com");
        empleado4.setUsuario("juanito");
        empleado4.setContrasenia("111");
        sgre.getListaEmpleados().add(empleado4);

        Empleado empleado5 = new Empleado();
        empleado5.setNombre("Nick");
        empleado5.setId("12000");
        empleado5.setEmail("cam21dun@gmail.com");
        empleado5.setUsuario("nicki");
        empleado5.setContrasenia("65433");
        sgre.getListaEmpleados().add(empleado5);

        Usuario usuario = new Usuario();
        usuario.setNombre("Sebastian");
        usuario.setId("66565");
        usuario.setEmail("cam21dun@gmail.com");
        usuario.setUsuario("sebas");
        usuario.setContrasenia("123342");
        sgre.getListaUsuarios().add(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Manuela");
        usuario2.setId("12767");
        usuario2.setEmail("cam21dun@gmail.com");
        usuario2.setUsuario("Manu");
        usuario2.setContrasenia("123");
        sgre.getListaUsuarios().add(usuario2);

        Usuario usuario3 = new Usuario();
        usuario3.setNombre("Miguel");
        usuario3.setId("12522");
        usuario3.setEmail("cam21dun@gmail.com");
        usuario3.setUsuario("migue");
        usuario3.setContrasenia("123746");
        sgre.getListaUsuarios().add(usuario3);

        Usuario usuario4 = new Usuario();
        usuario4.setNombre("Alma");
        usuario4.setId("12521");
        usuario4.setEmail("cam21dun@gmail.com");
        usuario4.setUsuario("111");
        usuario4.setContrasenia("111");
        sgre.getListaUsuarios().add(usuario4);


        Usuario usuario5 = new Usuario();
        usuario5.setNombre("Alison");
        usuario5.setId("23143");
        usuario5.setEmail("cam21dun@gmail.com");
        usuario5.setUsuario("ali");
        usuario5.setContrasenia("12388");
        sgre.getListaUsuarios().add(usuario5);

        Evento evento1 = new Evento();
        evento1.setId("1");
        evento1.setNombre("Conferencia de Tecnología");
        evento1.setDescripcion("Conferencia sobre los últimos avances en tecnología.");
        evento1.setFecha(LocalDate.now().plusDays(7));
        evento1.setCapMax("100");
        evento1.setEmpleadoAsignado(empleado1);
        sgre.getListaEventos().add(evento1);

        Evento evento2 = new Evento();
        evento2.setId("2");
        evento2.setNombre("Taller de Programación");
        evento2.setDescripcion("Taller práctico sobre programación en Java.");
        evento2.setFecha(LocalDate.now().plusMonths(1));
        evento2.setCapMax("40");
        evento2.setEmpleadoAsignado(empleado2);
        sgre.getListaEventos().add(evento2);

        Evento evento3 = new Evento();
        evento3.setId("3");
        evento3.setNombre("Taller de Fotografía");
        evento3.setDescripcion("Aprende las técnicas básicas de fotografía en una sesión de dos horas.");
        evento3.setFecha(LocalDate.now().plusDays(20));
        evento3.setCapMax("30");
        evento3.setEmpleadoAsignado(empleado1);
        sgre.getListaEventos().add(evento3);

        Evento evento4 = new Evento();
        evento4.setId("4");
        evento4.setNombre("Clase de Yoga al Aire Libre");
        evento4.setDescripcion("Sesión de yoga en el parque.");
        evento4.setFecha(LocalDate.now().plusMonths(15));
        evento4.setCapMax("20");
        evento4.setEmpleadoAsignado(empleado2);
        sgre.getListaEventos().add(evento4);

        Evento evento5 = new Evento();
        evento5.setId("1");
        evento5.setNombre("Proyección de Cortometrajes");
        evento5.setDescripcion("Serie de cortos de cine en una sesión de una hora.");
        evento5.setFecha(LocalDate.now().plusDays(23));
        evento5.setCapMax("20");
        evento5.setEmpleadoAsignado(empleado5);
        sgre.getListaEventos().add(evento1);

        Evento evento6 = new Evento();
        evento6.setId("2");
        evento6.setNombre("Clase de Cocina Rápida");
        evento6.setDescripcion("Aprende a preparar una receta sencilla en 45 minutos.");
        evento6.setFecha(LocalDate.now().plusMonths(13));
        evento6.setCapMax("35");
        evento6.setEmpleadoAsignado(empleado3);
        sgre.getListaEventos().add(evento6);


        Reserva reserva1 = new Reserva();
        reserva1.setId("11");
        reserva1.setEvento(evento1);
        reserva1.setUsuario(usuario);
        reserva1.setCapacidad("5");
        reserva1.setFecha(LocalDate.now());
        reserva1.setEstado(Estado.APROBADA);
        sgre.getListaReservas().add(reserva1);

        Reserva reserva2 = new Reserva();
        reserva2.setId("22");
        reserva2.setEvento(evento2);
        reserva2.setUsuario(usuario);
        reserva2.setCapacidad("10");
        reserva2.setFecha(LocalDate.now().plusDays(1));
        reserva2.setEstado(Estado.PENDIENTE);
        sgre.getListaReservas().add(reserva2);

        Reserva reserva3 = new Reserva();
        reserva3.setId("33");
        reserva3.setEvento(evento1);
        reserva3.setUsuario(usuario);
        reserva3.setCapacidad("2");
        reserva3.setFecha(LocalDate.now());
        reserva3.setEstado(Estado.APROBADA);
        sgre.getListaReservas().add(reserva3);

        Reserva reserva4 = new Reserva();
        reserva4.setId("44");
        reserva4.setEvento(evento2);
        reserva4.setUsuario(usuario);
        reserva4.setCapacidad("1");
        reserva4.setFecha(LocalDate.now().plusDays(1));
        reserva4.setEstado(Estado.PENDIENTE);
        sgre.getListaReservas().add(reserva4);

        Reserva reserva5 = new Reserva();
        reserva5.setId("55");
        reserva5.setEvento(evento1);
        reserva5.setUsuario(usuario);
        reserva5.setCapacidad("13");
        reserva5.setFecha(LocalDate.now());
        reserva5.setEstado(Estado.RECHAZADA);
        sgre.getListaReservas().add(reserva5);


        Admin admin1 = new Admin();
        admin1.setUsuario("1");
        admin1.setContrasenia("1");
        sgre.getListaAdmins().add(admin1);

        Admin admin2 = new Admin();
        admin2.setUsuario("admin");
        admin2.setContrasenia("321");
        sgre.getListaAdmins().add(admin2);


        System.out.println("Información inicializada del sgre creada");
        return sgre;


    }
}
