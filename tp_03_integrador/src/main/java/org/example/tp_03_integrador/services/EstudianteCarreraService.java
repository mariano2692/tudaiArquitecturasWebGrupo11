package org.example.tp_03_integrador.services;

import jakarta.transaction.Transactional;
import org.example.tp_03_integrador.dtos.CarreraSimpleDTO;
import org.example.tp_03_integrador.dtos.EstudianteCarreraDTO;
import org.example.tp_03_integrador.dtos.EstudianteCarreraRequestDTO;
import org.example.tp_03_integrador.dtos.EstudianteDTO;
import org.example.tp_03_integrador.entities.Carrera;
import org.example.tp_03_integrador.entities.Estudiante;
import org.example.tp_03_integrador.entities.EstudianteCarrera;
import org.example.tp_03_integrador.repositories.CarreraRepository;
import org.example.tp_03_integrador.repositories.EstudianteCarreraRepository;
import org.example.tp_03_integrador.repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("EstudianteCarreraServicio")
public class EstudianteCarreraService {

    @Autowired
    EstudianteCarreraRepository EstudianteCarreraRepository;

    @Autowired
    CarreraRepository CarreraRepository;

    @Autowired
    EstudianteRepository EstudianteRepository;

    @Autowired
    EstudianteService EstudianteService;


    @Transactional
    public EstudianteDTO matricularEstudiante(EstudianteCarreraRequestDTO ecrdto) {
        int dni = ecrdto.getDniAlumno();
        int id = ecrdto.getIdCarrera();

        // Verificar que existen el estudiante y la carrera
        Optional<Estudiante> estudianteOpt = EstudianteRepository.findByDni(dni);
        Optional<Carrera> carreraOpt = CarreraRepository.findCarreraById(id);

        if (estudianteOpt.isPresent() && carreraOpt.isPresent()) {
            EstudianteCarrera estudianteCarrera = new EstudianteCarrera(ecrdto.getInscripcion(), ecrdto.getGraduacion(), ecrdto.getAntiguedad(), estudianteOpt.get(), carreraOpt.get());

            // Verificar si ya existe la relación
            List<EstudianteCarrera> existingEstudianteCarrera = EstudianteCarreraRepository.findByEstudianteAndCarrera(estudianteOpt.get(), carreraOpt.get());
            if (existingEstudianteCarrera.isEmpty()) {
                EstudianteCarreraRepository.save(estudianteCarrera);
            } else {
                System.out.println("La relación ya existe para el estudiante con DNI: " + dni + " y la carrera: " + carreraOpt.get().getNombre());
                return null;
            }

        }
        else {
            System.out.println("No se encontró estudiante o carrera con los IDs proporcionados.");
            return null;
        }

        CarreraSimpleDTO carreranueva= new CarreraSimpleDTO(carreraOpt.get().getNombre());
        ArrayList<CarreraSimpleDTO> carrerasInscriptas = EstudianteService.getCarrerasInscriptas(estudianteOpt.get());
        carrerasInscriptas.add(carreranueva);

        System.out.println("Se guardo estudiante nuevo");
        return new EstudianteDTO(estudianteOpt.get().getDni(),
                estudianteOpt.get().getNombre(),
                estudianteOpt.get().getApellido(),
                estudianteOpt.get().getEdad(),
                estudianteOpt.get().getGenero(),
                estudianteOpt.get().getCiudadResidencia(),
                estudianteOpt.get().getLu());

    }

    private EstudianteCarreraDTO convertirADTO(EstudianteCarrera inscripcion) {
        EstudianteCarreraDTO dto = new EstudianteCarreraDTO();
        dto.setNombreCarrera(inscripcion.getCarrera().getNombre());
        dto.setAntiguedad(inscripcion.getAntiguedad());
        dto.setSeGraduo(inscripcion.isGraduado());
        return dto;
    }


}


