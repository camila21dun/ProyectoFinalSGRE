package co.edu.uniquindio.sgre.model;

//import lombok.*;

/*@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

 */
public class Empleado {
    private String id;
    private String nombre;
    private String email;
    public static EmpleadoBuilder builder() {
        return new EmpleadoBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getEmail() {
        return this.email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Empleado(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public Empleado() {
    }

    public String toString() {
        String var10000 = this.getId();
        return "Empleado(id=" + var10000 + ", nombre=" + this.getNombre() + ", email=" + this.getEmail() + ")";
    }

    public static class EmpleadoBuilder {
        private String id;
        private String nombre;
        private String email;

        EmpleadoBuilder() {
        }

        public EmpleadoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public EmpleadoBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public EmpleadoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public Empleado build() {
            return new Empleado(this.id, this.nombre, this.email);
        }

        public String toString() {
            return "Empleado.EmpleadoBuilder(id=" + this.id + ", nombre=" + this.nombre + ", email=" + this.email + ")";
        }
    }
}