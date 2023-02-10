package edu.kh.jdbc.board.view;

import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.board.model.vo.Board;
import static edu.kh.jdbc.main.view.MainView.*;

public class BoardView {
	Scanner sc = new Scanner(System.in);
	
	BoardService bService = new BoardService();

	/*
	 * 게시판 기능 (Board View, Service, DAO, board-query.xml)
	 * 
	 * 1. 게시글 목록 조회(작성일 내림차순) (게시글 번호, 제목[댓글 수], 작성자명, 작성일, 조회수 ) 2. 게시글 상세 조회(게시글
	 * 번호 입력 받음) (게시글 번호, 제목, 내용, 작성자명, 작성일, 조회수, 댓글 목록(작성일 오름차순 ) 2-1. 댓글 작성 2-2.
	 * 댓글 수정 (자신의 댓글만) 2-3. 댓글 삭제 (자신의 댓글만) // 자신이 작성한 글 일때만 메뉴 노출 2-4. 게시글 수정 2-5.
	 * 게시글 삭제 3. 게시글 작성(제목, 내용 INSERT) -> 작성 성공 시 상세 조회 수행 4. 게시글 검색(제목, 내용, 제목+내용,
	 * 작성자)
	 */

	// board-query.xml
	// comment-query.xml

	public void boardMenu() {
		int input = -1;
		
		do {
			try {
				System.out.println("\n******* 게시판 기능 *******\n");
				System.out.println("1. 게시글 목록 조회 ");
				System.out.println("2. 게시글 상세 조회(+댓글 기능)");
				System.out.println("3. 게시글 작성");
				System.out.println("4. 게시글 검색");
				System.out.println("0. 메인 메뉴로 돌아가기");

				System.out.print("\n메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine(); // 입력버퍼 개행문자 제거

				switch (input) {
				case 1:selectAllBoard(); break; // 게시글 목록 조회
				case 2:selectBoard(); break; // 게시글 상세 조회
				case 3:insertBoard(); break; // 게시글 등록(삽입)
				
				case 4:searchBoard(); break;
				
				case 0:System.out.println("[로그인 메뉴로 이동합니다.]"); break;
				default:System.out.println("메뉴에 작성된 번호만 입력해주세요.");
				}
				
				System.out.println();
				
			}catch(Exception e) {
				e.printStackTrace();
			}

		} while (input != 0);

	}
	
	/**
	 * 게시글 목록 조회
	 */
	private void selectAllBoard() {
		System.out.println("\n[게시글 목록 조회]\n");
		
		try {
			List<Board> boardList = bService.selectAllBoard();
			
			if(boardList.isEmpty()) { // 조회 결과가 없을 경우
				System.out.println("게시글이 존재하지 않습니다.");
				
			} else {
				for(Board b : boardList) {
					// 3 | 샘플 제목3[4] | 유저삼 | 3시간 전 | 10
					System.out.printf("%d | %s[%d] | %s | %s | %d\n",
							b.getBoardNo(), 
							b.getBoardTitle(), 
							b.getCommentCount(),
							b.getMemberName(),
							b.getCreateDate(),
							b.getReadCount());
				}
			}
			
		}catch(Exception e) {
			System.out.println("\n<<게시글 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}

	private void searchBoard() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 게시글 등록(삽입)
	 */
	private void insertBoard() {
		System.out.println("\n[게시글 등록]\n");
		
		System.out.print("제목 : ");
		String boardTitle = sc.nextLine();
		
		System.out.println("내용 : ");
		String boardContent = inputContent();
		
		// Board 객체에 제목, 내용, 회원 번호를 담아서 서비스에 전달
		Board board = new Board();
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		board.setBoardNo(LoginMember.getMemberNo());
		
		int result = bService.insertBoard(board);
	}

	/** 내용 입력
	 * @return content
	 */
	private String inputContent() {
		String content = ""; // 빈 문자열
		String input = null; // 참조하는 객체가 없음
		
		System.out.println("입력 종료 시 ($exit) 입력");
		
		while(true) {
			input = sc.nextLine();
			
			if(input.equals("$exit")) {
				break;
			}
			
			// 입력된 내용을 content에 누적
			content += input + "\n";
		}
		
		return content;
	}

	private void selectBoard() {
		// TODO Auto-generated method stub
		
	}

	

}