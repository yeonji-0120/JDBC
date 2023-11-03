package com.ohgiraffers.section03.sqlInjection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application1 {
    private static String empId = "200";
    private static String empName = "홍길동";
    //다른곳에서 아이디랑 이름을 입력받아서 가지고 있다는 가정을 위해 static으로 만듬
    public static void main(String[] args){
        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rset = null;

        String query = "SELECT * FROM EMPLOYEE WHERE EMP_ID = '" + empId + "' AND EMP_NAME = '" + empName + "'";
        System.out.println(query);
        //SELECT * FROM EMPLOYEE EMP_ID = '200' AND EMP_NAME = '홍길동' 이렇게 나오길 원해서 코드 짠거임

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
    //쿼리에 AND 조건이라 Id에 200은 있으나 이름에 홍길동이 없기때문에 회원정보가 없다고 나옴
}
