package com.ohgiraffers.section01.insert;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt = null;
        //insert update delete는 resultset 필요 없음 정수로 반환 받기 때문에 정수를 선언
        int result = 0;

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));

            String query = prop.getProperty("insertMenu"); //키값
            System.out.println("query = " + query);
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, "봉골레청국장");
            pstmt.setInt(2, 50000);
            pstmt.setInt(3, 1);
            pstmt.setString(4,"Y");

            /*여기서 요청하는 메소드가
            * select 일 경우 : executeQuery()를 사용
            * insert,update, delete 일 경우 : executeUpdate()를 사용*/

            result = pstmt.executeUpdate();
            //결과를 받는 구문, 성공항 행의 수를 반환해서 정수로 받아야함




        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
            close(con);
        }
        System.out.println("result = " + result);

    }
}
