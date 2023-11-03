package com.ohgiraffers.section03.sqlInjection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application2 {
    private static String empId = "200";
    private static String empName = "' OR 1 = 1 AND EMP_ID = '200";

    public static void main(String[] args){
        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rset = null;

        String query = "SELECT * FROM EMPLOYEE WHERE EMP_ID = '" + empId + "' AND EMP_NAME = '" + empName + "'";
        System.out.println(query);
        //SELECT * FROM EMPLOYEE EMP_ID = '200' AND EMP_NAME = '' OR 1 = 1 AND EMP_ID = 200'
        //1과 1은 무조건 참임 AND랑 OR 중에 AND가 먼저라서 AND절은 참이 됨
        //OR절에서 뒤에가 참이기때문에 OR EMP_NAME = ''은 없어지는거임 그래서 아이디만 있어도 접속이 가능

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            if(rset.next()){
                System.out.println(rset.getString("EMP_NAME" + "님 환영합니다."));
            }else{
                System.out.println("회원정보가 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(stmt);
            close(con);

        }

    }
}
