package factories;

import daos.ClienteDAO;
import daos.FacturaDAO;
import daos.FacturaProductoDAO;
import daos.ProductoDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class MySqlFactory extends DBFactory {
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URI = "jdbc:mysql://localhost:3306/Entregable1";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "yes";

    private static MySqlFactory unicaInstancia = null;
    private static Connection conn;

    private MySqlFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static MySqlFactory getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new MySqlFactory();
        }
        return unicaInstancia;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public void closeConnection() throws SQLException {

    }

    @Override
    public ClienteDAO getClienteDAO() throws SQLException {
        return null;
    }

    @Override
    public FacturaDAO getFacturaDAO() throws SQLException {
        return null;
    }

    @Override
    public FacturaProductoDAO getFacturaProductoDAO() throws SQLException {
        return null;
    }

    @Override
    public ProductoDAO getProductoDAO() throws SQLException {
        return null;
    }
}
