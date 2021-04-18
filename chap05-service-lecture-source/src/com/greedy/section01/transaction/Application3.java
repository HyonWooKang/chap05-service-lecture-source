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

public class Application3 {
	
	public static void main(String[] args) {
		// JDBCTemplate에 commit & rollback 추가
		
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
			// 여기서부터 result까지는 지난 dao에 들어갈 수 잇는 부분
			prop.loadFromXML(new FileInputStream("mapper/menu-query.xml"));
			String query = prop.getProperty("insertMenu");
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "트러플광어회");
			pstmt.setInt(2, 80000);
			pstmt.setInt(3, 4);
			pstmt.setString(4, "Y");
			
			result = pstmt.executeUpdate();
			
			// if - else 여기에 추가
			if (result > 0) {
				System.out.println("메뉴 등록 성공!");
				con.commit();
			} else {
				System.out.println("메뉴 등록 실패!");
				con.rollback();
			}
			
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
			close(pstmt);
		}
	
	}

}
