package co.edu.uniquindio.sgre.viewController;

import co.edu.uniquindio.sgre.model.Admin;
import co.edu.uniquindio.sgre.model.Empleado;
import co.edu.uniquindio.sgre.model.Usuario;

public class SessionManager {
    private static SessionManager instance;
    private Admin admin;
    private Empleado empleado;
    private Usuario usuario;
    private String userType;

    private SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public Usuario getUsuario2() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setUsuario2(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}