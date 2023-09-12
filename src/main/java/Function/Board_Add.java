package Function;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static Function.Main_textboard.connection;

public class Board_Add {
    Scanner scanner = new Scanner(System.in);
    public void Add(){
        System.out.print("게시물 제목을 입력해주세요 : ");
        String title = scanner.nextLine();
        System.out.print("게시물 내용을 입력해주세요 : ");
        String contents = scanner.nextLine();
        Article new_Board = new Article(Main_textboard.number, title, contents);
        Main_textboard.boardList.add(new_Board);

        // JDBC 연결 설정
        if (connection != null) {
            try {
                // SQL 쿼리를 사용하여 데이터베이스에 게시물 추가
                String insertQuery = "INSERT INTO text_board (title, contents, time) VALUES (?, ?, NOW())";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, contents);

                Main_textboard.number++;

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("게시물이 등록되었습니다.");
                } else {
                    System.out.println("게시물 등록에 실패했습니다.");
                }

                preparedStatement.close();
                // Do not close the connection here.
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
