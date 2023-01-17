package com.example.tzancashootingv1;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public Connection connection;
    public Connection getConnection(){
        String databaseName = "tzanca";
        String databaseUser = "root";
        String databasePassword = "";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+databaseName,databaseUser,databasePassword);
        }catch(Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
