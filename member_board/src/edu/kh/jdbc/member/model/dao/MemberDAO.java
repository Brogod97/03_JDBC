package edu.kh.jdbc.member.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.member.vo.Member;

public class MemberDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private Properties prop = null;
	
	public MemberDAO() {
		try{
			prop = new Properties();
			
			prop.loadFromXML(new FileInputStream("member-query.xml"));
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/** 내 정보 조회 DAO
	 * @param conn
	 * @param loginMember
	 * @return member
	 */
	public Member selectMyInfo(Connection conn, Member loginMember) {
		Member member = null;
		
		try{
			String sql = prop.getProperty("selectMyInfo");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginMember.getMemberId());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new Member(rs.getInt("MEMBER_NO"),
						loginMember.getMemberId(),
						loginMember.getMemberPw(),
						rs.getString("MEMBER_NM"),
						rs.getString("MEMBER_GENDER"),
						rs.getString("ENROLL_DATE"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return member;
	}

	/** 회원 목록 조회 DAO
	 * @param conn
	 * @return list
	 */
	public List<Member> selectAll(Connection conn) {
		List<Member> list = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectAll");
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Member member = new Member(rs.getInt("MEMBER_NO"),
						rs.getString("MEMBER_ID"),
						rs.getString("MEMBER_PW"),
						rs.getString("MEMBER_NM"),
						rs.getString("MEMBER_GENDER"),
						rs.getString("ENROLL_DATE"));
				list.add(member);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(stmt);
		}
		
		return list;
	}

	/** 내 정보 수정 DAO
	 * @param conn
	 * @param loginMember
	 * @param inputName
	 * @param inputGender
	 * @return result
	 */
	public int updateMember(Connection conn, Member loginMember, String inputName, String inputGender) {
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateMember");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputName);
			pstmt.setString(2, inputGender);
			pstmt.setString(3, loginMember.getMemberId());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	
	/** 비밀번호 변경 DAO
	 * @param conn
	 * @param loginMember
	 * @param newPw2
	 * @return result
	 */
	public int updatePw(Connection conn, Member loginMember, String newPw2) {
		int result = 0;
		
		try {
			String sql = prop.getProperty("updatePw");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPw2);
			pstmt.setString(2, loginMember.getMemberPw());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	
	/** 회원 탈퇴 DAO
	 * @param conn
	 * @param loginMember
	 * @return result
	 */
	public int secession(Connection conn, Member loginMember) {
		int result = 0;
		
		try {
			String sql = prop.getProperty("secession");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, loginMember.getMemberId());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}




}
