package edu.kh.jdbc.member.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.close;
import static edu.kh.jdbc.common.JDBCTemplate.commit;
import static edu.kh.jdbc.common.JDBCTemplate.getConnection;
import static edu.kh.jdbc.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.member.model.dao.MemberDAO;
import edu.kh.jdbc.member.vo.Member;

public class MemberService {
	
	private MemberDAO dao = new MemberDAO();

	/** 내 정보 조회 서비스
	 * @param loginMember
	 * @return member
	 */
	public Member selectMyInfo(Member loginMember) {
		Connection conn = getConnection();
		
		Member member = dao.selectMyInfo(conn, loginMember);
		
		close(conn);
		
		return member;
	}
	
	/** 회원 목록 조회 서비스
	 * @return list
	 */
	public List<Member> selectAll() {
		Connection conn = getConnection();
		
		List<Member> list = dao.selectAll(conn);
		
		close(conn);
		
		return list;
	}

	/** 회원 정보 수정 서비스
	 * @param loginMember
	 * @param inputName
	 * @param inputGender
	 * @return result
	 */
	public int updateMember(Member loginMember, String inputName, String inputGender) {
		Connection conn = getConnection();
		
		int result = dao.updateMember(conn, loginMember, inputName, inputGender);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	/** 비밀번호 변경 서비스
	 * @param loginMember
	 * @param newPw2
	 * @return result
	 */
	public int updatePw(Member loginMember, String newPw2) {
		Connection conn = getConnection();
		
		int result = dao.updatePw(conn, loginMember, newPw2);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	/** 회원 탈퇴 서비스
	 * @param loginMember
	 * @return result
	 */
	public int secession(Member loginMember) {
		Connection conn = getConnection();
		
		int result = dao.secession(conn, loginMember);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}



}
