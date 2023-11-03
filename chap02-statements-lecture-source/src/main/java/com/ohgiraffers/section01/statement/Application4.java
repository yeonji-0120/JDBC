package com.ohgiraffers.section01.statement;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application4 {
    public static void main(String[] args) {


        //순서1 Connection 생성
        Connection con = getConnection();

        //순서2 Statemnet 생성
        Statement stmt = null;

        //순서3 ResultSet 생성
        ResultSet rset = null;


        EmployeeDTO selectedEmp = null;


        try {
            //순서4 Connection의 createStatement()를 이용한 Statment 인스턴스 생성
            stmt = con.createStatement();

            Scanner sc = new Scanner(System.in);
            System.out.print("조회하려는 사번을 입력해주세요 : ");
            String empId = sc.nextLine();
            String query = "SELECT * FROM EMPLOYEE WHERE EMP_ID = '" + empId + "'";
            //순서5 executeQuery()로 쿼리문 실행하고 결과를 ResultSet으로 반환 받음
            System.out.println("query = " + query);
            rset = stmt.executeQuery(query);
            //순서6 ResultSet에 담긴 결과물을 컬럼 이름을 이용해서 꺼내어 출력
            if (rset.next()) {

                selectedEmp = new EmployeeDTO();

                selectedEmp.setEmpId(rset.getString("EMP_ID"));
                selectedEmp.setEmpName(rset.getString("EMP_NAME"));
                selectedEmp.setEmpNo(rset.getString("EMP_NO"));
                selectedEmp.setEmail(rset.getString("EMAIL"));
                selectedEmp.setPhone(rset.getString("PHONE"));
                selectedEmp.setDeptCode(rset.getString("DEPT_CODE"));
                selectedEmp.setJobCode(rset.getString("JOB_CODE"));
                selectedEmp.setSalLevel(rset.getString("SAL_LEVEL"));
                selectedEmp.setSalary(rset.getInt("SALARY"));
                selectedEmp.setBonus(rset.getDouble("BONUS"));
                selectedEmp.setManagerId(rset.getString("MANAGER_ID"));
                selectedEmp.setHireDate(rset.getDate("HIRE_DATE"));
                selectedEmp.setEntDate(rset.getDate("ENT_DATE"));
                selectedEmp.setEntYn(rset.getString("ENT_YN"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //순서7 사용한 자원 반납
            close(rset);
            close(stmt);
            close(con);
        }
        System.out.println("selectedEmp = " + selectedEmp);

    }

    }
