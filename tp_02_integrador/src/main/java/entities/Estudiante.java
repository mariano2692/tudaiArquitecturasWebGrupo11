package entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String nombres;

    @Column
    private String apellido;

    @Column
    private int edad;

    @Column
    private String genero;

    @Column
    private int dni;

    @Column
    private String ciudadResidencia;

    @Column
    private Long lu;

    // Relación uno a muchos con la entidad Inscripcion
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // CascadeType.ALL significa que todas las operaciones de cascada se aplicarán a las entidades relacionadas
    private List<Inscripcion> inscripciones;

    // Constructores
    public Estudiante() {
        this.inscripciones = new ArrayList<Inscripcion>();
    }

    public Estudiante(String nombres, String apellido, int edad, String genero, int dni, String ciudadResidencia, Long lu) {
        this.nombres = nombres;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.dni = dni;
        this.ciudadResidencia = ciudadResidencia;
        this.lu = lu;
        this.inscripciones = new ArrayList<Inscripcion>();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public Long getLu() {
        return lu;
    }

    public void setLu(Long lu) {
        this.lu = lu;
    }

    public List<Inscripcion> getInscripciones() {
        return new ArrayList<>(inscripciones);
    }

    public void addInscripcion(Inscripcion inscripcion) {
        if (!inscripciones.contains(inscripcion)) {
            inscripciones.add(inscripcion);
            inscripcion.setEstudiante(this); // Mantener la relación bidireccional
        }
    }

    public void removeInscripcion(Inscripcion inscripcion) {
        if (inscripciones.contains(inscripcion)) {
            inscripciones.remove(inscripcion);
            inscripcion.setEstudiante(null); // Mantener la relación bidireccional
        }
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", genero='" + genero + '\'' +
                ", dni='" + dni + '\'' +
                ", ciudadResidencia='" + ciudadResidencia + '\'' +
                ", lu=" + lu +
                ", inscripciones=" + inscripciones +
                '}';
    }
}