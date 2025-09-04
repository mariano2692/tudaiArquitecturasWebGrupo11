package daos;

import entities.Producto;

import java.sql.SQLException;
import java.util.List;

public class ProductoDAO implements DAO<Producto> {

    private static ProductoDAO unicaInstancia;

    private ProductoDAO() throws SQLException {

    }

    public static ProductoDAO getInstance() throws SQLException {
        if (unicaInstancia == null) {
            unicaInstancia = new ProductoDAO();
        }

        return unicaInstancia;
    }

    @Override
    public void dropTable() throws SQLException {

    }

    @Override
    public void createTable() throws SQLException {

    }

    @Override
    public void insert(Producto producto) throws SQLException {

    }

    @Override
    public Producto select(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Producto> selectAll() throws SQLException {
        return List.of();
    }

    @Override
    public boolean update(Producto producto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }
}
