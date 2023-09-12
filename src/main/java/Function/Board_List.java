package Function;

import SQL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static Function.Main_Text.connection;

public class Board_List {
    public void list(){
        System.out.println("==================");
        // JDBC 연결 설정
        if (connection != null) {
            try {
                // SQL 쿼리를 사용하여 데이터베이스에서 게시물 목록을 가져옴
                String selectQuery = "SELECT * FROM text_board";
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                // 위 쿼리는 매개 변수가 없으므로 매개 변수 설정은 필요 없음

                // 결과셋을 가져오기 위해 executeQuery를 사용
                ResultSet resultSet = preparedStatement.executeQuery();

                // 결과를 순회하면서 출력
                while (resultSet.next()) {
                    int number = resultSet.getInt("number");
                    String title = resultSet.getString("title");
                    String time = resultSet.getString("time");
                    int viewCount = resultSet.getInt("view_count");
                    // 가져온 결과를 출력
                    System.out.println("게시글 번호: " + number);
                    System.out.println("게시글 제목: " + title);
                    System.out.println("시간: " + time);
                    System.out.println("조회수: " + viewCount);
                    System.out.println("==================");
                }

                // 자원 해제
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
