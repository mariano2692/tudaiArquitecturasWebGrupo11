package daos;

import entities.Cliente;

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

    }

    @Override
    public void createTable() throws SQLException {

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
