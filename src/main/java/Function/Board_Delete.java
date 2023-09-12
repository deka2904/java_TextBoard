package Function;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import static Function.Main_textboard.connection;

public class Board_Delete {
    public void delete(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 게시물 번호 : ");
        try {
            int num = Integer.parseInt(scanner.nextLine());

            // JDBC 연결 설정
            if (connection != null) {
                try {
                    // SQL DELETE 쿼리 실행
                    String deleteQuery = "DELETE FROM text_board WHERE number = ?";
                    PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                    deleteStatement.setInt(1, num);

                    int deletedRows = deleteStatement.executeUpdate();
                    if (deletedRows > 0) {
                        System.out.printf("%d번 게시물이 삭제되었습니다.\n", num);

                        // 삭제 후에 데이터 다시 가져와 출력
                        String selectQuery = "SELECT * FROM text_board";
                        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        // 결과를 순회하면서 출력
                        while (resultSet.next()) {
                            int number = resultSet.getInt("number");
                            String title = resultSet.getString("title");

                            // 결과를 출력
                            System.out.println("번호 : " + number);
                            System.out.println("제목 : " + title);
                            System.out.println("==================");
                        }
                    } else {
                        System.out.println("게시물 삭제에 실패했습니다. 해당 번호를 찾을 수 없습니다.");
                    }
                    deleteStatement.close();
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
