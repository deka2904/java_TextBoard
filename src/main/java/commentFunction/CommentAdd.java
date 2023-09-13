package commentFunction;

import SQL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CommentAdd {
    Connection connection = DatabaseConnection.getConnection();

    public void commentadd(int num){
        Scanner scanner = new Scanner(System.in);
        try {
            // 댓글 입력 받기
            System.out.print("댓글 내용을 입력하세요: ");
            String commentText = scanner.nextLine();

            // SQL INSERT 쿼리 실행
            String commentQuery = "INSERT INTO text_board_comment (comment, comment_time, board_number, member_id) VALUES (?, NOW(), ?, ?)";
            PreparedStatement commentStatement = connection.prepareStatement(commentQuery);
            commentStatement.setString(1, commentText);
            commentStatement.setInt(2, num); // 게시물 번호를 외래 키로 설정

            // INSERT 실행
            commentStatement.executeUpdate();

            System.out.println("댓글이 성공적으로 등록되었습니다.");

            // 댓글 등록 후 게시글 내용 다시 불러오기
            String selectContentsQuery = "SELECT * " +
                    "FROM text_board " +
                    "LEFT JOIN text_board_comment ON text_board.number = text_board_comment.board_number " +
                    "WHERE text_board.number = ?";
            PreparedStatement selectContentsStatement = connection.prepareStatement(selectContentsQuery);
            selectContentsStatement.setInt(1, num);
            ResultSet resultSet = selectContentsStatement.executeQuery();

            if (resultSet.next()) {
                // 게시글 내용 출력
                int number = resultSet.getInt("number");
                String title = resultSet.getString("title");
                String contents = resultSet.getString("contents");
                String time = resultSet.getString("time");
                int viewCount = resultSet.getInt("view_count");

                // 가져온 결과를 출력
                System.out.println("게시글 번호: " + number);
                System.out.println("게시글 제목: " + title);
                System.out.println("게시글 내용: " + contents);
                System.out.println("시간: " + time);
                System.out.println("조회수: " + viewCount);
                System.out.println("==================");
                System.out.println("======= 댓글 =======");

                // 댓글 출력을 위한 루프
                do {
                    String comment = resultSet.getString("comment");
                    String comment_time = resultSet.getString("comment_time");
                    String nickname = resultSet.getString("member_id");

                    System.out.println("[작성자 : " + nickname + "]");
                    System.out.println("댓글 내용: " + comment);
                    System.out.println("댓글 작성일: " + comment_time);
                } while (resultSet.next());

                // 자원 해제
                resultSet.close();
                selectContentsStatement.close();
            } else {
                System.out.println("해당 번호의 게시물을 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            System.out.println("댓글 등록 중 오류가 발생했습니다.");
        }
    }
}
