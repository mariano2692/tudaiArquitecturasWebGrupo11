package daos;

import entities.FacturaProducto;

import java.sql.SQLException;
import java.util.List;

public class FacturaProductoDAO implements DAO<FacturaProducto> {

    private static FacturaProductoDAO unicaInstancia;

    private FacturaProductoDAO() throws SQLException {

    }

    public static FacturaProductoDAO getInstance() throws SQLException {
        if (unicaInstancia == null) {
            unicaInstancia = new FacturaProductoDAO();
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
    public void insert(FacturaProducto facturaProducto) throws SQLException {

    }

    @Override
    public FacturaProducto select(int id) throws SQLException {
        return null;
    }

    @Override
    public List<FacturaProducto> selectAll() throws SQLException {
        return List.of();
    }

    @Override
    public boolean update(FacturaProducto facturaProducto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }
}
