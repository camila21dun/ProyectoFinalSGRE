package co.edu.uniquindio.sgre.model;

//import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
/*
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

 */
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String capacidad;
    private Usuario usuario;
    private Evento evento;
    private LocalDate fecha;
    private Estado estado;

    public Reserva() {
    }

    public Reserva(String id, String capacidad, Evento evento, LocalDate fecha, Estado estado) {
        this.id = id;
        this.capacidad = capacidad;
     //   this.usuario = usuario;
        this.evento = evento;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Reserva(Estado estado) {
        this.estado = estado;

    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id='" + id + '\'' +
                ", capacidad='" + capacidad + '\'' +
                ", usuario=" + usuario +
                ", evento=" + evento +
                ", fecha=" + fecha +
                ", estado=" + estado +
                '}';
    }
}
