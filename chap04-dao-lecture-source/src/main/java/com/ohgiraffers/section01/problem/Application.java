package com.ohgiraffers.section01.problem;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application {

    public static void main(String[] args) {

        Connection con = getConnection();
        //연결 객체 생성

        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        //최소 3개의 쿼리문을 작성하겠다는 뜻 쿼리 내용은 menu_query.xml 파일에 적어둠, 그래서 아래 try 부분에서 getProperty를 키값으로 가진 쿼리문을 불러옴

        ResultSet rset1 = null;
        ResultSet rset2 = null;
        //SELECT 두개를 하겠다 select는 resultset으로 값을 받으니까!

        int result = 0;
        //insert, update, delete 중 하나를 하겠다는 뜻

        Properties prop = new Properties();

        int maxMenuCode = 0;//현재 메뉴번호중 가장 큰 번호의 값을 저장할 변수, if 문에서 씀
        List<Map<Integer, String >> categoryList = null;
                //Map에는 키, 밸류 값이 들어가고 키에 오브젝트 타입이 들어가면 사용 가능, selectAllCategoryList에서 사용할건데 카테고리 코드는 Integer타입, 카테고리 명은 String라서 저렇게 씀

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query1 = prop.getProperty("selectLastMenuCode");
            String query2 = prop.getProperty("selectAllCategoryList");
            String query3 = prop.getProperty("insertMenu");


                pstmt1 = con.prepareStatement(query1);
                pstmt2 = con.prepareStatement(query2);
                pstmt3 = con.prepareStatement(query3);

                rset1 = pstmt1.executeQuery();


                if(rset1.next()) {
                    maxMenuCode = rset1.getInt("MAX(A.MENU_CODE)");//MAX(MENU_CODE)는 첫번째 쿼리문 실행했을때 행의 타이틀
                }
                System.out.println("maxMenuCode = " + maxMenuCode);

                    rset2 = pstmt2.executeQuery();
                    categoryList = new ArrayList<>();
                    while (rset2.next()) {//조회하는 거니까 while
                        Map<Integer, String> category = new HashMap<>();
                        category.put(rset2.getInt("CATEGORY_CODE"), rset2.getString("CATEGORY_NAME"));
                        categoryList.add(category);
                    }

                    System.out.println("categoryList = " + categoryList);

            Scanner sc = new Scanner(System.in);
            System.out.println("등록할 메뉴의 이름을 입력하세요 : ");
            String menuName = sc.nextLine();
            System.out.println("신규 메뉴의 가격을 입력하세요 : ");
            int menuPrice = sc.nextInt();
            System.out.println("카테고리를 선택해주세요(식사, 음료, 디저트, 한식, 중식) : ");
            /*보이기엔 식사, 음료, 디저트 이렇게 보이지만 자바에서는 1,2,3,4,5 숫자로 인식함
            그래서 아래에 스위치문으로 숫자로 출력해줌*/
            sc.nextLine();
            String categoryName = sc.nextLine();
            System.out.println("바로 판매 메뉴에 적용하시겠습니까?(예/아니요) : ");
            String  answer = sc.nextLine();

            int categoryCode = 0;
            switch (categoryName){
                case "식사" : categoryCode = 1 ; break;
                case "음료" : categoryCode = 2 ; break;
                case "디저트" : categoryCode = 3 ; break;
                case "한식" : categoryCode = 4 ; break;
                case "중식" : categoryCode = 5 ; break;
            }

            String orderableStatus = "";
            switch (answer){
                case "예" : orderableStatus = "Y"; break;
                case "아니요" : orderableStatus = "N"; break;

            }

            pstmt3.setInt(1, maxMenuCode +1);//오토 인크리먼트로 대체 가능하나 없으면 이렇게 직접 찾아서 적어줘야함
            pstmt3.setString(2, menuName);
            pstmt3.setInt(3, menuPrice);
            pstmt3.setInt(4, categoryCode);
            pstmt3.setString(5, orderableStatus);

            result = pstmt3.executeUpdate();



            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
            }finally {
            close(rset1);
            close(rset2);
            close(pstmt1);
            close(pstmt2);
            close(pstmt3);
            close(con);
        }

        if(result > 0){
            System.out.println("메뉴 등록 성공");
        }else{
            System.out.println("메뉴 등록 실패");
        }


    }
}
