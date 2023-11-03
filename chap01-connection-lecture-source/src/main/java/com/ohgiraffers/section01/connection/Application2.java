package com.ohgiraffers.section01.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application2 {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/employee";
        String user = "ohgiraffers";
        String password = "ohgiraffes";
        
        Connection con = null;

        try {
            Class.forName(driver);
            
            con = DriverManager.getConnection(url, user, password);

            System.out.println("con = " + con);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
