package dtos;

public class EstudianteDTO {
    private String nombres;
    private String apellido;
    private int edad;
    private String genero;
    private int dni;
    private String ciudadResidencia;
    private Long lu;

    public EstudianteDTO() {}

    public EstudianteDTO(String nombres, String apellido, int edad, String genero, int dni, String ciudadResidencia, Long lu) {
        this.nombres = nombres;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.dni = dni;
        this.ciudadResidencia = ciudadResidencia;
        this.lu = lu;
    }


    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public long getLu() {
        return lu;
    }

    public void setLu(long lu) {
        this.lu = lu;
    }

    @Override
    public String toString() {
        return "EstudianteDTO{" +
                ", nombres: '" + nombres + '\'' +
                ", apellido: '" + apellido + '\'' +
                ", edad: " + edad +
                ", genero: '" + genero + '\'' +
                ", dni: " + dni +
                ", ciudadResidencia: '" + ciudadResidencia + '\'' +
                ", lu: " + lu +
                '}';
    }
}
