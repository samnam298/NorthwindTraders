package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static void main(String[] args) {

        // Read environment variables (HIDDEN, not in code)
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASS");

        // Safety check
        if (url == null || user == null || password == null) {
            System.out.println("ERROR: Environment variables not set.");
            System.out.println("You must set DB_URL, DB_USER, DB_PASS in Environment Variables.");
            return;
        }

        try {

            // Connect to MySQL
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to Northwind!");

            // SQL Query (Part 2 requirement)
            String sql = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Header for table format:
            System.out.printf("%-5s %-30s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
            System.out.println("--------------------------------------------------------------");

            // Loop through all products
            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("ProductName");
                double price = rs.getDouble("UnitPrice");
                int stock = rs.getInt("UnitsInStock");

                System.out.printf("%-5d %-30s %-10.2f %-10d%n", id, name, price, stock);
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
