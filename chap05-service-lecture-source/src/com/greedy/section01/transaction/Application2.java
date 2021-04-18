package com.greedy.section01.transaction;

import static com.greedy.common.JDBCTemplate.close;
import static com.greedy.common.JDBCTemplate.getConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class Application2 {
	
	public static void main(String[] args) {
		
		Connection con = getConnection();
		
		try {
			System.out.println("autoCommit의 현재 설정 값 : " + con.getAutoCommit());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		PreparedStatement pstmt = null;
		int result = 0;
		Properties prop = new Properties();
		
		try {
			prop.loadFromXML(new FileInputStream("mapper/menu-query.xml"));
			
			String query = prop.getProperty("insertMenu");
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "프로틴샌드위치");
			pstmt.setInt(2, 6000);
			pstmt.setInt(3, 3);
			pstmt.setString(4, "Y");
			
			result = pstmt.executeUpdate();

		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			close(pstmt);
			close(con);
		}
		
		if(result > 0) {
			System.out.println("메뉴 추가에 성공하였습니다.");
		} else {
			System.out.println("메뉴 추가에 실패하였습니다.");
		}
	}

}
