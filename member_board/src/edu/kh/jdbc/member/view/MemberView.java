package edu.kh.jdbc.member.view;

import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.member.model.service.MemberService;
import edu.kh.jdbc.member.vo.Member;

/*
 * 회원기능 (Member View, Service, DAO, member-query.xml)
 * 
 * 1. 내 정보 조회
 * 2. 회원 목록 조회(아이디, 이름, 성별)
 * 3. 내 정보 수정(이름, 성별)
 * 4. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)
 * 5. 회원 탈퇴
 * */

// member-query.xml

public class MemberView {
	
	// 로그인 회원 정보 저장용 변수
	private Member loginMember = null;
	
	private Scanner sc = new Scanner(System.in);
	
	private MemberService service = new MemberService();

	public void memberMenu(Member loginMember) {
		// 전달 받은 로그인 회원 정보를 필드에 저장
		this.loginMember = loginMember;
		int input = -1; 
		
		do {
			System.out.println("\n ******* 회원기능 *******");
			System.out.println("1. 내 정보 조회 ");
			System.out.println("2. 회원 목록 조회");
			System.out.println("3. 내 정보 수정");
			System.out.println("4. 비밀번호 변경");
			System.out.println("5. 회원 탈퇴");
			System.out.println("0. 메인 메뉴로 돌아가기");

			System.out.print("\n메뉴 선택 : ");
			input = sc.nextInt();
			sc.nextLine(); // 입력버퍼 개행문자 제거
			
			switch (input) {
			case 1:
				selectMyInfo();
				break;
			case 2:
				selectAll();
				break;
			case 3:
				updateMember();
				break;
			case 4:
				updatePw();
				break;
			case 5:
				secession();
				loginMember = null;
				return;
			case 0:
				return;
			default:
				System.out.println("메뉴에 작성된 번호만 입력해주세요.");
			}
			
		}while(input != 0);
		
	}

	/** 정보 1명 출력 메소드
	 * @param mem
	 */
	private void printOne(Member mem) {
		Member member = mem;
		
		System.out.printf("\n회원번호 = %d" 
				+ "\n아이디 = %s"
				+ "\n이름 = %s"
				+ "\n성별 = %s"
				+ "\n가입일시 = %s\n"
				, member.getMemberNo(), member.getMemberId()
				, member.getMemberName(), member.getMemberGender(),
				member.getEnrollDate());
	}

	/** 현재 로그인 된 유저 정보 조회 메소드
	 * 
	 */
	private void selectMyInfo() {
		System.out.println("******* 내 정보 조회 *******");
		
		Member member = service.selectMyInfo(loginMember);
		
		printOne(member);
	}
	
	/** 전체 회원 조회 메소드
	 * 
	 */
	private void selectAll() {
		System.out.println("******* 회원 목록 조회 *******");
		
		List<Member> list = service.selectAll();
		
		if(list.isEmpty()) {
			System.out.println("조회된 회원이 없습니다.");
		}else {
			for(Member member :list) {
				printOne(member);
				System.out.println();
			}
		}
	}

	/** 현재 로그인 된 유저 정보 수정 메소드
	 * 
	 */
	private void updateMember() {
		System.out.println("******* 내 정보 수정 *******");
		
		System.out.print("수정할 이름 입력: ");
		String inputName = sc.next();
		
		System.out.print("수정할 성별 입력(M/F): ");
		String inputGender = sc.next().toUpperCase();
		
		if(inputGender.equals("M") || inputGender.equals("F")){
			int result = service.updateMember(loginMember, inputName, inputGender);
			
			if(result > 0) {
				System.out.println("정보 수정 성공");
			}else {
				System.out.println("정보 수정 실패");
			}
		}else {
			System.out.println("잘못 입력하셨습니다.");
		}
	}
	
	/** 현재 로그인 된 유저 비밀번호 변경 메소드
	 * 
	 */
	private void updatePw() {
		System.out.println("******* 비밀번호 변경 *******");
		
		System.out.print("현재 비밀번호를 입력하세요 : ");
		String currPw = sc.next();
		
		int result = 0;
		
		if(currPw.equals(loginMember.getMemberPw())){ 
			System.out.print("새 비밀번호를 입력하세요 : ");
			String newPw1 = sc.next();
			
			System.out.print("새 비밀번호 확인 : ");
			String newPw2 = sc.next();
			
			if(newPw1.equals(newPw2)) {
				result = service.updatePw(loginMember, newPw2);
			}
		}
		
		if(result > 0) {
			System.out.println("비밀번호 변경 성공");
		}else {
			System.out.println("비밀번호 변경 실패");
		}
	}
	
	/** 회원 탈퇴 메소드
	 * 
	 */
	private void secession() {
		System.out.println("******* 회원 탈퇴 *******");
		
		System.out.print("정말 탈퇴하시겠습니까? (Y/N) : ");
		String input = sc.next().toUpperCase();
		
		int result = 0;
		
		if(input.equals("Y")) {
			System.out.print("현재 비밀번호를 입력해주세요 : ");
			String currPw = sc.next();
			
			if(currPw.equals(loginMember.getMemberPw())) {
				result = service.secession(loginMember);
			}
		}else if(input.equals("N")) {
			System.out.println("취소 되었습니다.");
			return;
		}else {
			System.out.println("잘못 입력하셨습니다.");
		}
		
		if(result > 0) {
			System.out.println("회원 탈퇴 성공");
		}else {
			System.out.println("회원 탈퇴 실패");
		}
	}
}