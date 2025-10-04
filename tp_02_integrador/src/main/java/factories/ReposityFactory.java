package factories;

public abstract class ReposityFactory {
    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;

    public abstract RepositoryCarrera getCarreraRepository();
    public abstract RepositoryEstudiante getEstudianteRepository();
    public abstract RepositoryInscripcion getInscripcionRepository();

    public static RepositoryFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC:
                return new JpaMySqlRepositoryFactory();
            default:
                return null;
        }
    }
}
