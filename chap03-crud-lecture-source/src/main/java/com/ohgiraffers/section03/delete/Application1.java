package com.ohgiraffers.section03.delete;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        Connection con = getConnection();

        PreparedStatement pstmt = null;
        int result = 0;

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));

            String query = prop.getProperty("deleteMenu");

            Scanner sc = new Scanner(System.in);
            System.out.print("삭제할 메뉴 번호를 입력하세요 : ");
            int menuCode = sc.nextInt();

            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, menuCode);

            result = pstmt.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
            close(con);
        }

        if(result > 0){
            System.out.println("메뉴 삭제 성공");
        }else{
            System.out.println("메뉴 삭제 실패");
        }
    }
}
