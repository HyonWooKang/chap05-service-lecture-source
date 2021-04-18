package com.greedy.section02.service.run;

import com.greedy.section02.service.model.service.MenuService;

public class Application {
	
	public static void main(String[] args) {
		
		/*
		 * Service의 역할 (DAO -> Service 호출)
		 * 1. Connection 생성 - 내용: close, commit, rollback
		 * 2. DAO의 메소드 호출
		 * 3. Transaction 제어 - commit, rollback (insert, update, delete에 대해서만 // select은 제외)
		 * 4. Connection 닫기
		 */
		
		new MenuService().registNewMenu();
	}

}
