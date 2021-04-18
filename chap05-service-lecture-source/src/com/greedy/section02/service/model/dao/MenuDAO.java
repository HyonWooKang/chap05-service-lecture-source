package com.greedy.section02.service.model.dao;

import static com.greedy.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.greedy.section02.service.model.dto.CategoryDTO;
import com.greedy.section02.service.model.dto.MenuDTO;

public class MenuDAO {
	
	// 기능을 호출해주기
	
	private Properties prop = new Properties();
	
	public MenuDAO() {
		try {
			prop.loadFromXML(new FileInputStream("mapper/menu-query.xml"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
}

	/**
	 * 신규 카테고리 등록용 메소드
	 * @param con 연결정보
	 * @param newCategory 등록카테고리 정보
	 * @return
	 */
	public int insertNewCategory(Connection con, CategoryDTO newCategory) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertCategory");
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, newCategory.getName());
			pstmt.setObject(2, newCategory.getRefCategoryCode());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			// con은 최종적으로 rollback, commit 후 닫아야 해서 제외함
		}
		
		return result;

	}

	/**
	 * <pre>
	 *   마지막 등록코드 조회용
	 * </pre>
	 * @param con
	 * @return
	 */
	public int selectLastCategoryCode(Connection con) {
		
		Statement stmt = null;
		// int형이어도 ResultSet이 붙어야 한다. 조회, 수정 등 모든 결과는 무조건 ResultSet에 결과 담아야 함
		ResultSet rset = null;
		
		// SELECT문 중 1개의 COLUMN값, 특정 값을 가져올 때만 int 반환형 사용 가능
		int newCategoryCode = 0;
		
		String query = prop.getProperty("getCurrentSequence");		
		
		try {
			stmt = con.createStatement();
			
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				newCategoryCode = rset.getInt("CURRVAL");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
			// con은 최종적으로 rollback, commit 후 닫아야 해서 제외함

		}
		
		return newCategoryCode;
	}

	public int insertNewMenu(Connection con, MenuDTO newMenu) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertMenu");
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, newMenu.getName());
			pstmt.setInt(2, newMenu.getPrice());
			pstmt.setInt(3, newMenu.getCategoryCode());
			pstmt.setString(4, newMenu.getOrderableStatus());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	
	}

}