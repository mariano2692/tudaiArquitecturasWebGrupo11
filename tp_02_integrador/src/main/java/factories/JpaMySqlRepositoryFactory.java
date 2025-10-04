package factories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import repositories.JpaCarreraRepository;
import repositories.JpaEstudianteRepository;
import repositories.JpaInscripcionRepository;
import repositories.interfaces.RepositoryCarrera;
import repositories.interfaces.RepositoryEstudiante;
import repositories.interfaces.RepositoryInscripcion;

public class JpaMySqlRepositoryFactory extends RepositoryFactory {
    private static final String PERSISTENCE_UNIT_NAME = "Integrador 2";
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    @Override
    public RepositoryCarrera getCarreraRepository() {
        EntityManager em = emf.createEntityManager();
        return JpaCarreraRepository.getInstance(em);
    }

    @Override
    public RepositoryEstudiante getEstudianteRepository() {
        EntityManager em = emf.createEntityManager();
        return JpaEstudianteRepository.getInstance(em);
    }

    @Override
    public RepositoryInscripcion getInscripcionRepository() {
        EntityManager em = emf.createEntityManager();
        return JpaInscripcionRepository.getInstance(em);
    }
}
