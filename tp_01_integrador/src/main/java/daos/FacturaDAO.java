package daos;

import entities.Factura;

import java.sql.SQLException;
import java.util.List;

public class FacturaDAO implements DAO<Factura> {

    private static FacturaDAO unicaInstancia;

    private FacturaDAO() throws SQLException {

    }

    public static FacturaDAO getInstance() throws SQLException {
        if (unicaInstancia == null) {
            unicaInstancia = new FacturaDAO();
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
    public void insert(Factura factura) throws SQLException {

    }

    @Override
    public Factura select(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Factura> selectAll() throws SQLException {
        return List.of();
    }

    @Override
    public boolean update(Factura factura) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }
}
