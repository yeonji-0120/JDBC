package com.ohgiraffers.section01.model.service;

import com.ohgiraffers.section01.model.dao.OrderDAO;
import com.ohgiraffers.section01.model.dto.CategoryDTO;
import com.ohgiraffers.section01.model.dto.MenuDTO;
import com.ohgiraffers.section01.model.dto.OrderDTO;
import com.ohgiraffers.section01.model.dto.OrderMenuDTO;

import java.sql.Connection;
import java.util.List;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class OrderService {

    private OrderDAO orderDAO = new OrderDAO();

    /* 설명. 카테고리 전체 조회용 메소드 */
    public List<CategoryDTO> selectAllCategory() {

        /* 순서 1. Connection 생성 */
        Connection con = getConnection();

        /* 순서 2. DAO의 모든 카테고리 조회용 메소드를 호출하여 결과 리턴받기 */
        List<CategoryDTO> categoryList = orderDAO.selectAllCategory(con);

        /* 순서 3. 커넥션 닫기 */
        close(con);

        /* 순서 4. 반환받은 값 리턴하기 */
        return categoryList;
    }

    /* 설명. 카테고리별 메뉴 조회용 메소드 */
    public List<MenuDTO> selectMenuBy(int categoryCode) {

        /* 순서 1. Connection 생성 */
        Connection con = getConnection();

        /* 순서 2. DAO의 해당 카테고리 메뉴를 조회하는 메소드로 categoryCode 전달하며 조회 */
        List<MenuDTO> menuList = orderDAO.selectMenuBy(con, categoryCode);

        /* 순서 3. Connection 닫기 */
        close(con);

        /* 순서 4. 반환받은 값 리턴하기 */
        return menuList;
    }

    /* 설명. 주문 정보 등록용 메소드 */
    public int registOrder(OrderDTO order) {

        /* 순서 1. Connection 생성 */
        Connection con = getConnection();

        /* 순서 2. 리턴할 값 초기화 */
        int result = 0;

        /* 순서 3. DAO 메소드로 전달 받은 값 넘겨서 insert */
        /* 순서 3-1. Order table insert */
        int orderResult = orderDAO.insertOrder(con, order);

        /* 순서 3-2. 마지막 발생한 시퀀스 조회 */
        int orderCode = orderDAO.selectLastOrderCode(con);

        /* 순서 3-3. orderList에 orderCode 추가 */
        List<OrderMenuDTO> orderMenuList = order.getOrderMenuList();
        for(int i = 0; i < orderMenuList.size(); i++) {
            OrderMenuDTO orderMenu = orderMenuList.get(i);
            orderMenu.setCode(orderCode);
        }

        /* 순서 3-4. ORDER_MENU 테이블에 insert */
        int orderMenuResult = 0;
        for(int i = 0; i < orderMenuList.size(); i++) {
            OrderMenuDTO orderMenu = orderMenuList.get(i);
            orderMenuResult += orderDAO.insertOrderMenu(con, orderMenu);
        }

        /* 순서 4. 성공 여부 판단 후 트랜젝션 처리 */
        if(orderResult > 0 && orderMenuResult == orderMenuList.size()) {
            commit(con);
            result = 1;
        } else {
            rollback(con);
        }

        /* 순서 5. Connection 닫기 */
        close(con);

        /* 순서 6. 결과값 반환 */
        return result;
    }

}
