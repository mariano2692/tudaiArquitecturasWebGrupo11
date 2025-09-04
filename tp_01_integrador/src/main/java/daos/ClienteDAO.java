package daos;

import entities.Cliente;
import factories.MySqlFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ClienteDAO implements DAO<Cliente> {

    private static ClienteDAO unicaInstancia;

    private ClienteDAO() throws SQLException {

    }

    public static ClienteDAO getInstance() throws SQLException {
        if (unicaInstancia == null) {
            unicaInstancia = new ClienteDAO();
        }

        return unicaInstancia;
    }


    @Override
    public void dropTable() throws SQLException {
        Connection conn = MySqlFactory.getInstance().getConnection();

        String drop_table = "DROP TABLE IF EXISTS Cliente";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(drop_table)) {
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al eliminar la tabla Cliente.", e);
        }

    }

    @Override
    public void createTable() throws SQLException {
        Connection conn = MySqlFactory.getInstance().getConnection();

        String table = "CREATE TABLE IF NOT EXISTS Cliente(" +
                "idCliente INT," +
                "nombre VARCHAR(500)," +
                "email VARCHAR(150)," +
                "PRIMARY KEY(idCliente))";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(table)) {
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al crear la tabla Cliente.", e);
        }

    }

    @Override
    public void insert(Cliente cliente) throws SQLException {

    }

    @Override
    public Cliente select(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Cliente> selectAll() throws SQLException {
        return List.of();
    }

    @Override
    public boolean update(Cliente cliente) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }
}
