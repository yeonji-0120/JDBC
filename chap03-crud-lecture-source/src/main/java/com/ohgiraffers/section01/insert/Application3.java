package com.ohgiraffers.section01.insert;

import com.ohgiraffers.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application3 {
    public static void main(String[] args) {
        /*여기서부터는 다른 클래스에서 보내는 내용이라고 가정한다*/
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

        MenuDTO newMenu = new MenuDTO();
        newMenu.setName(menuName);
        newMenu.setPrice(menuPrice);
        newMenu.setCategoryCode(categoryCode);
        newMenu.setOrderableStatus(orderableStatus);
        //추후에는 newMenu에 필요한 정보값을 다 담아서 다른 클래스에 있는 메소드의 인자로 전달해서 사용
        /*여기서부터는 다른 클래스에서 보내는 내용이라고 가정한다*/


        //메소드 시작점
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
            pstmt.setString(1, newMenu.getName());
            pstmt.setInt(2, newMenu.getPrice());
            pstmt.setInt(3, newMenu.getCategoryCode());
            pstmt.setString(4, newMenu.getOrderableStatus());

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
