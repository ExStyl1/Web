package org.example.laba3;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DbUtil implements Serializable {

    public static Connection getConnection() {
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:/jdbc/PostgresDS");
            return ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при получении подключения: " + e.getMessage());
            return null;
        }
    }
}