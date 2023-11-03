package com.ohgiraffers.section01.model.dao;

import com.ohgiraffers.common.JDBCTemplate;
import com.ohgiraffers.section01.model.dto.CategoryDTO;
import com.ohgiraffers.section01.model.dto.MenuDTO;
import com.ohgiraffers.section01.model.dto.OrderDTO;
import com.ohgiraffers.section01.model.dto.OrderMenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OrderDAO {

    private Properties prop = new Properties();

    public OrderDAO() {

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/order-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<CategoryDTO> selectAllCategory(Connection con) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        List<CategoryDTO> categoryList = null;

        String query = prop.getProperty("selectAllCategory");

        try {
            pstmt = con.prepareStatement(query);

            rset = pstmt.executeQuery();

            categoryList = new ArrayList<>();

            while(rset.next()) {
                CategoryDTO category = new CategoryDTO();
                category.setCode(rset.getInt("CATEGORY_CODE"));
                category.setName(rset.getString("CATEGORY_NAME"));

                categoryList.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }

        return categoryList;
    }

    public List<MenuDTO> selectMenuBy(Connection con, int categoryCode) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        List<MenuDTO> menuList = null;

        String query = prop.getProperty("selectMenuByCategory");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, categoryCode);

            rset = pstmt.executeQuery();

            menuList = new ArrayList<>();

            while(rset.next()) {
                MenuDTO menu = new MenuDTO();
                menu.setCode(rset.getInt("MENU_CODE"));
                menu.setName(rset.getString("MENU_NAME"));
                menu.setPrice(rset.getInt("MENU_PRICE"));
                menu.setCategoryCode(rset.getInt("CATEGORY_CODE"));
                menu.setOrderableStatus(rset.getString("ORDERABLE_STATUS"));

                menuList.add(menu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }

        return menuList;
    }

    /* 설명. 주문 정보 입력용 메소드 */
    public int insertOrder(Connection con, OrderDTO order) {

        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("insertOrder");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, order.getDate());
            pstmt.setString(2, order.getTime());
            pstmt.setInt(3, order.getTotalOrderPrice());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }

        return result;
    }

    /* 설명. 주문코드 시퀀스 조회용 메소드 */
    public int selectLastOrderCode(Connection con) {

        Statement stmt = null;
        ResultSet rset = null;

        int lastOrderCode = 0;

        String query = prop.getProperty("selectLastOrderCode");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            if(rset.next()) {
                lastOrderCode = rset.getInt("CURRVAL");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(stmt);
        }

        return lastOrderCode;
    }

    /* 설명. 주문별 메뉴 입력용 메소드 */
    public int insertOrderMenu(Connection con, OrderMenuDTO orderMenu) {

        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("insertOrderMenu");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, orderMenu.getCode());
            pstmt.setInt(2, orderMenu.getMenuCode());
            pstmt.setInt(3, orderMenu.getOrderAmount());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }

        return result;
    }
}
