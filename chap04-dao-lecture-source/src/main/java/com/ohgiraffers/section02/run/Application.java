package com.ohgiraffers.section02.run;

import com.ohgiraffers.section02.model.dao.MenuDAO;
import com.ohgiraffers.section02.model.dto.MenuDTO;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {


        Connection con = getConnection();
        MenuDAO registDAO = new MenuDAO(); //이때 xml의 파일을 읽어서 properties 객체에 쿼리에 대한 정보를 저장


        //메뉴의 마지막 번호 조회
        int maxMenuCode = registDAO.selectLastManuCode(con);
        System.out.println("maxMenuCode = " + maxMenuCode);

        //카테고리 목록 조회
        List<Map<Integer, String>> categoryList = registDAO.selectAllCategory(con);

        for(Map<Integer, String > category : categoryList){
            System.out.println(category);
        }


        //신규 메뉴 등록

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
        int menuCode = maxMenuCode +1;
        MenuDTO newMenu = new MenuDTO(menuCode, menuName, menuPrice, categoryCode, orderableStatus);

        int result = registDAO.insertNemMenu(con, newMenu);

        if(result > 0){
            System.out.println("메뉴 등록 성공");
        }else{
            System.out.println("메뉴 등록 실패");
        }

    }
}
