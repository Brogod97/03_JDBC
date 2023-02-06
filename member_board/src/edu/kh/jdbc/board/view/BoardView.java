package edu.kh.jdbc.board.view;

public class BoardView {

	public void boardMenu() {
		// TODO Auto-generated method stub
		
	}

}

/*
 * 게시판 기능 (Board View, Service, DAO, board-query.xml)
 * 
 * 1. 게시글 목록 조회(작성일 내림차순) (게시글 번호, 제목[댓글 수], 작성자명, 작성일, 조회수 )
 * 
 * 2. 게시글 상세 조회(게시글 번호 입력 받음) (게시글 번호, 제목, 내용, 작성자명, 작성일, 조회수, 댓글 목록(작성일 오름차순 )
 * 
 * 2-1. 댓글 작성 2-2. 댓글 수정 (자신의 댓글만) 2-3. 댓글 삭제 (자신의 댓글만)
 * 
 * // 자신이 작성한 글 일때만 메뉴 노출 2-4. 게시글 수정 2-5. 게시글 삭제
 * 
 * 
 * 3. 게시글 작성(제목, 내용 INSERT) -> 작성 성공 시 상세 조회 수행
 * 
 * 4. 게시글 검색(제목, 내용, 제목+내용, 작성자)
 * */

// board-query.xml
// comment-query.xml

// 코멘트는 보드 패키지 안에서 처리
// Comment 클래스는 board.model.vo 에 위치 (Board와 동일 레벨)
// DAO, Service 다 같은 패키지