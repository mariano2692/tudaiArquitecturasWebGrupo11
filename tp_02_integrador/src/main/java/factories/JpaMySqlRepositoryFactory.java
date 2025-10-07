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
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    //EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    public RepositoryInscripcion getInscripcionRepository(EntityManager em) {
        return new JpaInscripcionRepository(em);
    }

    public RepositoryCarrera getCarreraRepository(EntityManager em) {
        return new JpaCarreraRepository(em);
    }

    public RepositoryEstudiante getEstudianteRepository(EntityManager em) {
        return new JpaEstudianteRepository(em);
    }
    // --- NUEVO MÉTODO ESTÁTICO ---
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
    /*
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

     */
}
