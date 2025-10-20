package org.example.tp_03_integrador.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EstudianteCarreraRequestDTO {
    private int inscripcion;
    private int graduacion;
    private int antiguedad;
    private int dniAlumno;
    private int idCarrera;

    public EstudianteCarreraRequestDTO(int inscripcion, int graduacion, int antiguedad, int dniAlumno, int idCarrera) {
        this.inscripcion = inscripcion;
        this.graduacion = graduacion;
        this.antiguedad = antiguedad;
        this.dniAlumno = dniAlumno;
        this.idCarrera = idCarrera;
    }
}
