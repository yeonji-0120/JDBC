package com.ohgiraffers.section01.insert;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application2 {
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



            Scanner sc = new Scanner(System.in);
            System.out.print("메뉴의 이름을 입력하세요 : ");
            String menuName = sc.nextLine();
            System.out.print("메뉴의 가격을 입력하세요 : ");
            int menuPrice = sc.nextInt();
            System.out.print("카테고리 코드를 입력하세요 : ");
            int categoryCode = sc.nextInt();
            System.out.println("판매 여부를 결정해주세요(Y/N) : ");
            sc.nextLine();
            String orderableStatus = sc.nextLine().toUpperCase();


            pstmt = con.prepareStatement(query);
            pstmt.setString(1, menuName);
            pstmt.setInt(2, menuPrice);
            pstmt.setInt(3, categoryCode);
            pstmt.setString(4,orderableStatus);

            result = pstmt.executeUpdate();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
            close(con);
        }
        System.out.println("result = " + result);

        //성공, 실패 결과를 표현하는 식
        if(result > 0 ){//메뉴 추가에 성공했을 경우
            System.out.println("메뉴 등록 성공");
        }else{
            System.out.println("메뉴 등록 실패");
        }

    }
}
