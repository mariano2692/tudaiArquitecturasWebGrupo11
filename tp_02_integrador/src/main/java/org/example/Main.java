package org.example;

import helpers.CSVreader;
import helpers.DatabaseLoader;

import java.io.IOException;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CSVreader reader = new CSVreader();

        try {
            DatabaseLoader.cargarDatos(reader);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}