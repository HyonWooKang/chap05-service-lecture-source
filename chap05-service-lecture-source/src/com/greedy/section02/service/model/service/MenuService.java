package com.greedy.section02.service.model.service;

import static com.greedy.common.JDBCTemplate.close;
import static com.greedy.common.JDBCTemplate.commit;
import static com.greedy.common.JDBCTemplate.getConnection;
import static com.greedy.common.JDBCTemplate.rollback;

import java.sql.Connection;

import com.greedy.section02.service.model.dao.MenuDAO;
import com.greedy.section02.service.model.dto.CategoryDTO;
import com.greedy.section02.service.model.dto.MenuDTO;

public class MenuService {

	// DAO가 호출하는 것을 해줌
	// 여기서 작성하고 create하여 메소드 생성

	/** (Alt + Shift + J 로 추가)
	 * <pre>
	 *   신규 메뉴 등록용 서비스 메소드
	 * </pre>
	 */
	public void registNewMenu() {
		/* 1. Connection 생성 */
		Connection con = getConnection();
		
		/* 2. DAO 메소드 호출 */
		MenuDAO menuDAO = new MenuDAO();
		
		/* 2-1. 카테고리 등록  */
		CategoryDTO newCategory = new CategoryDTO();
		newCategory.setName("기타");
		newCategory.setRefCategoryCode(null);
		// object 타입이기 때문에 정수형을 Wrapper Class로 변경함 (기본자료형+객체형 모두 사용 가능)
		
		int result1 = menuDAO.insertNewCategory(con, newCategory);
		// MenuDAO 작성 (insertNewCategory)
		
		/* 방금 입력한 마지막 카테고리 번호 조회 */
		int newCategoryCode = menuDAO.selectLastCategoryCode(con);
		
		/*2-2. 메뉴 등록 */
		MenuDTO newMenu = new MenuDTO();
		newMenu.setName("메롱메롱스튜");
		newMenu.setPrice(4000);
		newMenu.setCategoryCode(newCategoryCode);
		newMenu.setOrderableStatus("Y");
		
		int result2 = menuDAO.insertNewMenu(con, newMenu);
		
		/* 3. Transaction 제어 */
		if(result1 > 0 && result2 > 0) {
			System.out.println("신규 카테고리와 메뉴를 추가하였습니다.");
			commit(con);
		} else {
			System.out.println("신규 카테고리와 메뉴를 추가하지 못했습니다.");
			rollback(con);
		}
		
		/* 4. Connection 반납 */
		close(con);

	}
	
}
