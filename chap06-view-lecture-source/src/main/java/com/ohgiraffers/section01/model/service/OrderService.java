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
    //Application -> OrderMenu -> OrderService -> OrderDAO
    public List<CategoryDTO> selectAllCategory() {//데이터베이스에 카테고리 목록 다 가져와줘
        Connection con = getConnection();

        List<CategoryDTO> categoryList = orderDAO.selectAllCategory(con);

        close(con);

        return categoryList;
    }

    public List<MenuDTO> selectMenuBy(int categoryCode) {
        Connection con = getConnection();

        List<MenuDTO> menuList = orderDAO.selectMenuBy(con, categoryCode);

        close(con);

        return menuList;
    }

    public int registOrder(OrderDTO order) {
        Connection con = getConnection();
        int result = 0;
        int orderResult = orderDAO.insertOrder(con, order);
        int orderCode = orderDAO.selectLastOrderCode(con);
        List<OrderMenuDTO> orderMenuList = order.getOrderMenuList();
        for(int i = 0; i < orderMenuList.size(); i++){
            OrderMenuDTO orderMenu = orderMenuList.get(i);
            orderMenu.setCode(orderCode);
        }

        //order_menu 테이블에 insert
        int orderMenuResult = 0; //insert하는 갯수를 체키하기 위한 변수
        for(int i = 0; i < orderMenuList.size(); i++){
            OrderMenuDTO ordermenu = orderMenuList.get(i);
            orderMenuResult += orderDAO.insertOrderMenu(con, ordermenu);
        }
        //성공여부판단 후 트랜잭션 처리
        if(orderResult > 0 && orderMenuResult == orderMenuList.size()){
            commit(con);
            result = 1;
        }else{
            rollback(con);
        }
        //Connectio 닫기
        close(con);

        //결과값 반환
        return result;
    }
}
