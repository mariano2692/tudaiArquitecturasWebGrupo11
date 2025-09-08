package org.example;

import daos.ClienteDAO;
import daos.ProductoDAO;
import dtos.ClienteConFacturacionDTO;
import dtos.ProductoMayorRecaudacionDTO;
import helpers.CSVreader;
import helpers.DatabaseLoader;

import java.sql.SQLException;
import java.util.List;

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

            ProductoMayorRecaudacionDTO productoMayorRecaudacion =  productoDAO.selectMayorRecaudacion();

            System.out.println(productoMayorRecaudacion);



            //lista de clientes con mayor facturacion


            List<ClienteConFacturacionDTO> clientesConMayorFacturacion = clienteDAO.clientesConMayorFacturacion();
            System.out.println(clientesConMayorFacturacion);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}