package daos;

import entities.FacturaProducto;
import factories.MySqlFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        Connection conn = MySqlFactory.getInstance().getConnection();

        String drop_table = "DROP TABLE IF EXISTS Factura_Producto";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(drop_table)) {
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al eliminar la tabla Factura_Producto.", e);
        }
    }

    @Override
    public void createTable() throws SQLException {
        Connection conn = MySqlFactory.getInstance().getConnection();

        String table = "CREATE TABLE IF NOT EXISTS Factura_Producto(" +
                "idFactura INT," +
                "idProducto INT," +
                "cantidad INT," +
                "PRIMARY KEY(idFactura, idProducto)," +
                "FOREIGN KEY(idFactura) REFERENCES Factura(idFactura)," +
                "FOREIGN KEY(idProducto) REFERENCES Producto(idProducto))";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(table);) {
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al crear la tabla Factura_Producto.", e);
        }
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
