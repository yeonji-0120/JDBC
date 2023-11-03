package com.ohgiraffers.section02.service.mode.service;

import com.ohgiraffers.section02.service.mode.dao.MenuDAO;
import com.ohgiraffers.section02.service.mode.dto.CategoryDTO;
import com.ohgiraffers.section02.service.mode.dto.MenuDTO;

import java.sql.Connection;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class MenuService {

    public void registNewMenu() {
        //Connection 생성
        Connection con = getConnection();

        //DAO 메소드 호출
        MenuDAO menuDAO = new MenuDAO();

        //2-1 카테고리 등록
        CategoryDTO newCategory = new CategoryDTO();
        newCategory.setName("기타");
        newCategory.setRefCategoryCode(null);

        int result1 = menuDAO.insertNewCategory(con, newCategory);

        int newCategoryCode = menuDAO.selectLastCategoryCode(con);

        //2-2 메뉴 등록
        MenuDTO newMenu = new MenuDTO();
        newMenu.setName("메롱메롱스튜");
        newMenu.setPrice(4000);
        newMenu.setCategoryCode(newCategoryCode);
        newMenu.setOrderableStatus("Y");

        int result2 = menuDAO.insertNewMenu(con, newMenu);

        //트랜젝션 제어
        if(result1 > 0 && result2 > 0){
            System.out.println("신규 카테고리와 메뉴를 추가하였습니다.");
            commit(con);

        }else {
            System.out.println("추가 싶해");
            rollback(con);
        }
        //4. Connection 반납
        close(con);
    }
}
