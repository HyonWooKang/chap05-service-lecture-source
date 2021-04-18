package com.greedy.section01.transaction;

import static com.greedy.common.JDBCTemplate.close;
import static com.greedy.common.JDBCTemplate.commit;
import static com.greedy.common.JDBCTemplate.getConnection;
import static com.greedy.common.JDBCTemplate.rollback;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class Application5 {
	
	public static void main(String[] args) {
		
		Connection con = getConnection();

		PreparedStatement pstmt1 = null; // 1, 2 등의 이름은 되도록 지향하세요
		PreparedStatement pstmt2 = null;
		
		Properties prop = new Properties();
		
		// 전역변수로 활용하기 위해서 try-catch문 안에서 바깥으로 선언문 이동
		int result1 = 0;
		int result2 = 0;
		
		try {
			prop.loadFromXML(new FileInputStream("mapper/menu-query.xml"));
			String query1 = prop.getProperty("insertCategory");
			String query2 = prop.getProperty("insertMenu");
			
			pstmt1 = con.prepareStatement(query1);
			pstmt1.setString(1, "기타2");
			pstmt1.setObject(2, null);
			
			result1 = pstmt1.executeUpdate();

			System.out.println("result1 : " + result1);
			
			pstmt2 = con.prepareStatement(query2);	
			pstmt2.setString(1, "치즈정어리통조림");
			pstmt2.setInt(2, 8000);
			pstmt2.setInt(3, 10);
			pstmt2.setString(4, "Y");
			
			result2 = pstmt2.executeUpdate();
			
			System.out.println("result2 : " + result2);
			
			/* 해당 자리에 commit, rollback을 작성하였는데
			 * 이 위에서 exception이 발생하면 commit과 rollback이 동작하지 않는다.
			 * => finally 구문에 성공, 실패 여부에 따라 실행하도록 넣어준다.
			 */
			
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt1);
			close(pstmt2);
			
			// 두 가지 모두 다 만족해야만 COMM IT이 되어서 SQL에서 검색이 가능하다.
			// 단, QUERY문의 SEQUENCE는 진행되기 때문에(NEXTVAL) 숫자가 바뀔 것이다.
			if(result1 > 0 && result2 >0) {
				System.out.println("신규 카테고리와 메뉴 등록 성공!");
				commit(con);
			} else {
				System.out.println("신규 카테고리와 메뉴 등록 실패!");
				rollback(con);
			}
			
			close(con);
		}
		
	}

}
