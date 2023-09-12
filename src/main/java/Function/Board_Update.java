package Function;

import SQL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import static Function.Main_Text.connection;

public class Board_Update {
    public void update(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("수정할 게시물 번호 : ");
        try {
            int num = Integer.parseInt(scanner.nextLine());

            // JDBC 연결 설정
            if (connection != null) {
                try {
                    // SQL 쿼리를 사용하여 게시물을 가져옴
                    String selectQuery = "SELECT * FROM text_board WHERE number = ?";
                    PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                    selectStatement.setInt(1, num);

                    ResultSet resultSet = selectStatement.executeQuery();

                    if (resultSet.next()) {
                        System.out.print("새로운 게시물 제목을 입력해주세요 : ");
                        String newTitle = scanner.nextLine();
                        System.out.print("새로운 게시물 내용을 입력해주세요 : ");
                        String newContents = scanner.nextLine();

                        // SQL UPDATE 쿼리 실행
                        String updateQuery = "UPDATE text_board SET title = ?, contents = ?, time = NOW() WHERE number = ?";
                        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                        updateStatement.setString(1, newTitle);
                        updateStatement.setString(2, newContents);
                        updateStatement.setInt(3, num);

                        int updatedRows = updateStatement.executeUpdate();
                        if (updatedRows > 0) {
                            System.out.printf("%d번 게시물이 수정되었습니다.\n", num);
                        } else {
                            System.out.println("게시물 수정에 실패했습니다.");
                        }

                        updateStatement.close();
                    } else {
                        System.out.println("없는 게시물 번호입니다.");
                    }

                    resultSet.close();
                    selectStatement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("올바른 번호를 입력해 주세요.");
        }
    }
}
