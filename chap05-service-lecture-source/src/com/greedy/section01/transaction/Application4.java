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

public class Application4 {
	
	public static void main(String[] args) {
		
		// 테이블의 연관관계
		
		Connection con = getConnection();

		PreparedStatement pstmt1 = null; // 1, 2 등의 이름은 되도록 지향하세요
		PreparedStatement pstmt2 = null;
		
		Properties prop = new Properties();
		
		try {
			prop.loadFromXML(new FileInputStream("mapper/menu-query.xml"));
			String query1 = prop.getProperty("insertCategory");
			String query2 = prop.getProperty("insertMenu");
			
			pstmt1 = con.prepareStatement(query1);
			pstmt1.setString(1, "기타");
			pstmt1.setObject(2, null);
			
			int result1 = pstmt1.executeUpdate();

			System.out.println("result1 : " + result1);
			
			pstmt2 = con.prepareStatement(query2);
			pstmt2.setString(1, "오이파르페");
			pstmt2.setInt(2, 8000);
			pstmt2.setInt(3, 3);
			pstmt2.setString(4, "Y");
			
			int result2 = pstmt2.executeUpdate();
			
			System.out.println("result2 : " + result2);
			
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
			close(con);
		}
		
	}

}
