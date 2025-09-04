package org.example;

import daos.ClienteDAO;
import daos.ProductoDAO;
import helpers.CSVreader;
import helpers.DatabaseLoader;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        CSVreader reader = new CSVreader();

        try {
            // Cargar todos los datos desde los archivos CSV a la base de datos
            DatabaseLoader.cargarDatos(reader);

            // Obtener instancias de DAOs para las consultas
            ClienteDAO clienteDAO = ClienteDAO.getInstance();
            ProductoDAO productoDAO = ProductoDAO.getInstance();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}