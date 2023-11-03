package com.ohgiraffers.section02.model.dao;

import com.ohgiraffers.section02.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class MenuDAO {
    private Properties prop = new Properties();
    public MenuDAO(){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));

        //xml 파일에 있는 쿼리문 불러오는 코드

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public int selectLastManuCode(Connection con){ //메뉴의 마지막 번호 조회 메소드
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        int maxMenuCode = 0;//현재 메뉴번호중 가장 큰 번호의 값을 저장할 변수 if 문에서 씀
        String query = prop.getProperty("selectLastMenuCode");

        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();

            if(rset.next()){
                maxMenuCode = rset.getInt(1);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(pstmt);
        }
        return maxMenuCode;


    }

    public List<Map<Integer, String>> selectAllCategory(Connection con) {   //카테고리 목록 조회 메소드

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        List<Map<Integer, String >> categoryList = null;

        String query = prop.getProperty("selectAllCategoryList");

        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();

            categoryList = new ArrayList<>();

            while (rset.next()) {//조회하는 거니까 while
                Map<Integer, String> category = new HashMap<>();
                category.put(rset.getInt("CATEGORY_CODE"), rset.getString("CATEGORY_NAME"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return categoryList;

    }

    public int insertNemMenu(Connection con, MenuDTO newMenu) {//메뉴 추가 하는 메소드
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("insertMenu");

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setInt(1, newMenu.getCode());//오토 인크리먼트로 대체 가능하나 없으면 이렇게 직접 찾아서 적어줘야함
            pstmt.setString(2, newMenu.getName());
            pstmt.setInt(3, newMenu.getPrice());
            pstmt.setInt(4, newMenu.getCategoryCode());
            pstmt.setString(5, newMenu.getOrderableStatus());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
        }

        return result;
    }
}
