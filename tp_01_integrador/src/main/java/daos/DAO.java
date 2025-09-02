package daos;

public interface DAO<T> {

    public abstract void createTable();
    public abstract void deleteTable();
}
