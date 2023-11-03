package com.ohgiraffers.section01.transaction;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application4 {
    public static void main(String[] args) {//여러개 INSERT할 때 오토커밋을 키면 pstmt2인 기타 까지는 들어가고 그 개구리뒷다리통조림은 안들어가지고 저장됨 둘 중 하나만 들어가고 저장됨
        Connection con = getConnection();

        try {
            System.out.println("autoCommit의 현재 설정값 ? "+ con.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;

        int result = 0;
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));

            String query = prop.getProperty("insertMenu");
            String query2 = prop.getProperty("insertCategory");

            pstmt2 = con.prepareStatement(query2);
            pstmt2.setString(1, "기타");
            pstmt2.setObject(2, null);

            int result2 = pstmt2.executeUpdate();

            System.out.println("result2 = " + result2);


            pstmt = con.prepareStatement(query);
            pstmt.setString(1,"개구리뒷다리통조림");
            pstmt.setInt(2, 70000);
            pstmt.setInt(3, 0);
            pstmt.setString(4, "Y");

            result = pstmt.executeUpdate();

            System.out.println("result = " + result);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
            close(con);
        }


    }

}
