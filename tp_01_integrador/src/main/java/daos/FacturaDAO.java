package daos;

import entities.Factura;
import factories.MySqlFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        Connection conn = MySqlFactory.getInstance().getConnection();

        String drop_table = "DROP TABLE IF EXISTS Factura";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(drop_table)) {
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al eliminar la tabla Factura.", e);
        }
    }

    @Override
    public void createTable() throws SQLException {
        Connection conn = MySqlFactory.getInstance().getConnection();

        String table = "CREATE TABLE IF NOT EXISTS Factura(" +
                "idFactura INT," +
                "idCliente INT," +
                "PRIMARY KEY(idFactura))";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(table)) {
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al crear la tabla Factura.", e);
        }

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
