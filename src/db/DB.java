package db;

import java.sql.*;

public class DB {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/pos_db",
            "root",
            "password"
        );
    }
}

//DB is not created yet