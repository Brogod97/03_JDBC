package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Employee;

public class JDBCExample5 {

	public static void main(String[] args) {

		// 입사일을 입력("2022-09-06") 받아
		// 입력받은 값보다 먼저 입사한 사람의
		// 이름, 입사일, 성별(M, F) 조회

		Scanner sc = new Scanner(System.in);

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			System.out.print("입사일 입력 : ");
			String input = sc.nextLine();

			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pw = "kh1234";

			conn = DriverManager.getConnection(url, user, pw);

			String sql = "SELECT EMP_NAME, TO_CHAR(HIRE_DATE, 'YYYY\"년\" MM\"월\" DD\"일\"') AS HIRE_DATE, "
					+ "DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') AS GENDER " + "FROM EMPLOYEE "
					+ "WHERE HIRE_DATE < TO_DATE( '" + input + "')";

			// 문자열 내부에 쌍따옴표 작성시 \" 로 작성해야 한다 (Escape 문자)

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			List<Employee> list = new ArrayList<>();

			while (rs.next()) {
				/*
				 * 기본 생성자를 이용해 set 하는 방법 Employee emp = new Employee();
				 * 
				 * emp.setEmpName(rs.getString("EMP_NAME"));
				 * emp.setHireDate(rs.getString("HIRE_DATE")); // 현재 행의 "입사일" 컬럼의 문자열 값을 얻어와
				 * emp가 참조하는 객체의 hireDate 필드에 세팅 emp.setGender(rs.getString("GENDER")); // -> 만약
				 * gender를 char 타입으로 설정했을 경우 // Java의 char : 문자 1개를 의미 // DB의 char : 고정 길이
				 * 문자열(==String) // DB 컬럼 값을 char 자료형으로 사용하려면 String.charAt(index)을 사용해야 함
				 * 
				 * // list에 emp 객체 추가 list.add(emp);
				 */

				String empName = rs.getString("EMP_NAME");
				String hireDate = rs.getString("HIRE_DATE");
				String gender = rs.getString("GENDER");

				Employee emp = new Employee(empName, hireDate, gender);

				list.add(emp);
			}

			// 조회 결과가 없는 경우
			if (list.size() == 0) {
				System.out.println("조회 결과가 없습니다.");
			} else {
				for (Employee emp : list) {
					System.out.println(emp.toString());
					
					/*
					 향상된 for문을 사용하지 않는 경우
					 for(int i = 0; i < list.size(); i++){
					 	System.out.printf("%02d) / %s / %s / %s\n"
					 					, i + 1
					 	 				,list.get(i).getEmpName()
					 	 				, list.get(i).getHireDate()
					 	 				, list.get(i).getGender())
					 }
					*/
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
