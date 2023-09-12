package Function;

import SQL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import static Function.Main_Text.connection;

public class Board_Search {
    public void search(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("검색 키워드를 입력해주세요: ");
        String keyword = scanner.nextLine();

        // JDBC 연결 설정
        if (connection != null) {
            try {
                // SQL 쿼리를 사용하여 키워드 검색
                String selectQuery = "SELECT * FROM text_board WHERE title LIKE ?";
                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                selectStatement.setString(1, "%" + keyword + "%");

                ResultSet resultSet = selectStatement.executeQuery();
                // 결과를 순회하면서 출력
                while (resultSet.next()) {
                    int number = resultSet.getInt("number");
                    String title = resultSet.getString("title");
                    String time = resultSet.getString("time");
                    // 가져온 결과를 출력
                    System.out.println("번호: " + number);
                    System.out.println("제목: " + title);
                    System.out.println("시간: " + time);
                    System.out.println("==================");
                }

                // 자원 해제
                resultSet.close();
                selectStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
