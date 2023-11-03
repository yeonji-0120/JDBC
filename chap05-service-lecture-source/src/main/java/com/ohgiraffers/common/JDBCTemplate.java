package com.ohgiraffers.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {
    public static Connection getConnection(){
        Connection con = null;

        Properties prop = new Properties();

        try {
            prop.load(new FileReader("src/main/java/com/ohgiraffers/config/connection-info.properties"));

            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");


            Class.forName(driver);

            con = DriverManager.getConnection(url, prop);
            con.setAutoCommit(false); //autocommit 끄는 메소드

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }



    public static void close(Connection con){
        try {
            if(con != null && !con.isClosed())
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void close(Statement stmt){
        try {
            if(stmt != null && !stmt.isClosed())
                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void close(ResultSet rset){
        try {
            if(rset != null && !rset.isClosed())
                rset.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //수동으로 commit할때 사용 하는 메소드
    public static void commit(Connection con ){
        try {
            if(con != null && !con.isClosed()){
                con.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //수동으로 rollback 할 때 사용하는 메소드
    public static void rollback(Connection con ){
        try {
            if(con != null && !con.isClosed()){
                con.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
