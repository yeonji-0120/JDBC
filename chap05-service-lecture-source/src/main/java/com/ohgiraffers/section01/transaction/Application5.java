package com.ohgiraffers.section01.transaction;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class Application5 {
    public static void main(String[] args) {//여러개 INSERT할때 오토커밋을 끄면 둘중 하나가 안되면 둘 다 저장이 안됨
        Connection con = getConnection();

        try {
            System.out.println("autoCommit의 현재 설정값 ? "+ con.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;

        int result = 0;
        int result2 = 0;
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));

            String query = prop.getProperty("insertMenu");
            String query2 = prop.getProperty("insertCategory");

            pstmt2 = con.prepareStatement(query2);
            pstmt2.setString(1, "기타");
            pstmt2.setObject(2, null);

            result2 = pstmt2.executeUpdate();

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
            close(pstmt2);
            if(result > 0 && result2 > 0 ){ //result가 성공했는지 여부를 보는 구문, 둘 다 성공하면 commit
                System.out.println("신규 카테고리와 메뉴 등록 성공");
                commit(con);
            }else{  //아니면 rollback
                System.out.println("신규 카테고리와 메뉴 등록 실패");
                rollback(con);
            }
            close(con);
        }


    }

}
