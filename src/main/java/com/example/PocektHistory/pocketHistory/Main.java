package com.example.PocektHistory.pocketHistory;
import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args){
        try{
            Properties properties = new Properties();
            properties.put("user", "admin");
            properties.put("ssl", true);
            Connection conn = DriverManager.getConnection(
                    "crate://pink.ig-88.ask1.eastus2.azure.cratedb.net:5432/",
                    properties
            );

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT FROM sys.cluster");
            resultSet.next();
            String name = resultSet.getString("name");

            System.out.println(name);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
