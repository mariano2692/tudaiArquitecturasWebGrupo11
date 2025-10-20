package org.example.tp_03_integrador.services;


import org.example.tp_03_integrador.dtos.EstudianteDTO;
import org.example.tp_03_integrador.entities.Estudiante;
import org.example.tp_03_integrador.repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class EstudianteService {

@Autowired
EstudianteRepository estudianteRepository;

    @Transactional(readOnly = true)
    public List<EstudianteDTO> obtenerEstudiantesOrdenadosPorApellidoASC() throws Exception {
        try {
            List<Estudiante> estudiantes = estudianteRepository.obtenerEstudiantesOrdenadosPorApellidoASC();
            List<EstudianteDTO> estudianteDTOS = new ArrayList<>();
            for (Estudiante e : estudiantes) {
                estudianteDTOS.add(this.convertirADTO(e));
            }
            return estudianteDTOS;
        } catch (Exception e) {
            throw new Exception("Error al obtener estudiantes!" + e.getMessage());
        }
    }


    public EstudianteDTO getEstudianteByLu(int lu) {
        try {
            List<Estudiante> estudiantes = estudianteRepository.findByLu(lu);

            if (estudiantes.isEmpty()) {
                throw new RuntimeException("No se encontró estudiante con LU: " + lu);
            }

            return convertirADTO(estudiantes.get(0));

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar estudiante con LU: " + lu, e);
        }
    }

    public List<EstudianteDTO> getAllEstudiantesByGenero(String genero) {
        try {
            List<Estudiante> estudiantes = estudianteRepository.getAllEstudiantesByGenero(genero);

            if (estudiantes.isEmpty()) {
                throw new RuntimeException("No se encontraron estudiantes con el genero: " + genero);
            }

            List<EstudianteDTO> estudiantesDTO = new ArrayList<>();

            for (Estudiante estudiante : estudiantes) {
                EstudianteDTO dto = convertirADTO(estudiante);
                estudiantesDTO.add(dto);
            }

            return estudiantesDTO;

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar estudiantes con el genero: " + genero, e);
        }
    }


   /* public List<EstudianteDTO> getAllEstudiantesByCarreraAndCiudad(String carrera, String ciudad) {
        try {
            List<Estudiante> estudiantes = estudianteRepository.getAllEstudiantesByCarreraAndCiudad(carrera,ciudad);

            if (estudiantes.isEmpty()) {
                throw new RuntimeException("No se encontraron estudiantes en esta carrera: " + carrera + "o en esta ciudad"+ ciudad );
            }

            List<EstudianteDTO> estudiantesDTO = new ArrayList<>();

            for (Estudiante estudiante : estudiantes) {
                EstudianteDTO dto = convertirADTO(estudiante);
                estudiantesDTO.add(dto);
            }

            return estudiantesDTO;

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar estudiantes en esta carrera: " + carrera + "y/o en esta ciudad"+ ciudad, e);
        }
    }*/
/*
* metodo para convetir a dto lo q proviene de la base de datos
* @return estudianteDto
* */
    private EstudianteDTO convertirADTO(Estudiante estudiante) {
        EstudianteDTO dto = new EstudianteDTO();
        dto.setDni(estudiante.getDni());
        dto.setNombre(estudiante.getNombre());
        dto.setApellido(estudiante.getApellido());
        dto.setEdad(estudiante.getEdad());
        dto.setGenero(estudiante.getGenero());
        dto.setCiudadResidencia(estudiante.getCiudadResidencia());
        dto.setLu(estudiante.getLu());

        return dto;
    }

    public EstudianteDTO saveEstudiante(EstudianteDTO estudianteDTO) {
        Estudiante estudiante = new Estudiante();
        estudiante.setDni(estudianteDTO.getDni());
        estudiante.setNombre(estudianteDTO.getNombre());
        estudiante.setApellido(estudianteDTO.getApellido());
        estudiante.setEdad(estudianteDTO.getEdad());
        estudiante.setGenero(estudianteDTO.getGenero());
        estudiante.setCiudadResidencia(estudianteDTO.getCiudadResidencia());
        estudiante.setLu(estudianteDTO.getLu());

        Estudiante saved = estudianteRepository.save(estudiante);
        System.out.println("el estudiante fue guardado: " + saved.getNombre());
        return estudianteDTO;
    }
}
