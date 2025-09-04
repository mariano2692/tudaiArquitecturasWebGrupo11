package daos;

import entities.Producto;
import factories.MySqlFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        Connection conn = MySqlFactory.getInstance().getConnection();

        String drop_table = "DROP TABLE IF EXISTS Producto";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(drop_table)) {
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al eliminar la tabla Producto.", e);
        }
    }

    @Override
    public void createTable() throws SQLException {
        Connection conn = MySqlFactory.getInstance().getConnection();

        String table = "CREATE TABLE IF NOT EXISTS Producto(" +
                "idProducto INT," +
                "nombre VARCHAR(45)," +
                "valor FLOAT," +
                "PRIMARY KEY(idProducto))";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(table)) {
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al crear la tabla Producto.", e);
        }
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
