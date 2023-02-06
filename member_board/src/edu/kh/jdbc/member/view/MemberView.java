package edu.kh.jdbc.member.view;

import edu.kh.jdbc.member.vo.Member;

public class MemberView {
	
	// 로그인 회원 정보 저장용 변수
	private Member loginMember = null;

	public void memberMenu(Member loginMember) {
		// 전달 받은 로그인 회원 정보를 필드에 저장
		this.loginMember = loginMember;
		
		do {
			
		}while();
	}
}

/*
 * 회원기능 (Member View, Service, DAO, member-query.xml)
 * 
 * 1. 내 정보 조회 2. 회원 목록 조회(아이디, 이름, 성별) 3. 내 정보 수정(이름, 성별) 4. 비밀번호 변경(현재 비밀번호, 새
 * 비밀번호, 새 비밀번호 확인) 5. 회원 탈퇴
 * */

// member-query.xml