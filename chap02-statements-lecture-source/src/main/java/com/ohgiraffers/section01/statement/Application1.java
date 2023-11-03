package com.ohgiraffers.section01.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        Connection con = getConnection();
        System.out.println("con = " + con);

        Statement stmt = null;
        ResultSet rset = null;


        try {
            stmt = con.createStatement();

            rset = stmt.executeQuery("SELECT EMP_ID, EMP_NAME FROM EMPLOYEE");

            while(rset.next()){
                System.out.println(rset.getString("EMP_ID") +"," + rset.getString("EMP_NAME"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(stmt);
            close(con);
        }
    }
}
