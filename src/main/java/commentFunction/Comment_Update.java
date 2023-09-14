package commentFunction;

import SQL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Comment_Update {
    Connection connection = DatabaseConnection.getConnection();
    Scanner scanner = new Scanner(System.in);
    public void commentupdate(int num, String nickname) {
        // 해당 닉네임의 댓글, id 조회
        try {
            String comment_member_nickname = nickname;

            String id_comment_Select_Query = "SELECT id, comment FROM text_board_comment WHERE board_number = ? AND comment_member_nickname = ?";
            PreparedStatement id_comment_Select_Statement = connection.prepareStatement(id_comment_Select_Query);
            id_comment_Select_Statement.setInt(1, num);
            id_comment_Select_Statement.setString(2, comment_member_nickname);

            ResultSet resultSet = id_comment_Select_Statement.executeQuery();

            // 댓글의 번호
//            int commentNumber = 1;

            if (resultSet.next()) {
                do {
                    // 게시글 내용 출력
                    int id = resultSet.getInt("id");
                    String comment = resultSet.getString("comment");

                    // 가져온 결과를 출력
                    System.out.println("[댓글 번호] : " + id);
                    System.out.println("[댓글 내용] : " + comment);

//                    commentNumber++; // 댓글 번호 증가
                } while (resultSet.next());

                try {
                    System.out.print("수정할 댓글 번호를 입력하세요 : ");
                    int comment_number = Integer.parseInt(scanner.nextLine());

                    String CommentUpdateQuery = "SELECT comment, comment_time FROM text_board_comment WHERE id = ? AND comment_member_nickname = ?";

                    PreparedStatement CommentUpdateStatement = connection.prepareStatement(CommentUpdateQuery);
                    CommentUpdateStatement.setInt(1, comment_number);
                    CommentUpdateStatement.setString(2, comment_member_nickname);

                    resultSet = CommentUpdateStatement.executeQuery();
                    if(resultSet.next()){
                        System.out.print("수정하실 댓글을 입력해주세요 : ");
                        String newComment = scanner.nextLine();

                        String updateQuery = "UPDATE text_board_comment SET comment = ?, comment_time = NOW() WHERE id = ? AND comment_member_nickname = ?";
                        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                        updateStatement.setString(1, newComment);
                        updateStatement.setInt(2, comment_number);
                        updateStatement.setString(3, comment_member_nickname);

                        int updatedRows = updateStatement.executeUpdate();
                        if (updatedRows > 0) {
                            System.out.printf("%d번 댓글이 수정되었습니다.\n", comment_number);
                        } else {
                            System.out.println("댓글 수정에 실패했습니다.");
                        }
                        updateStatement.close();
                    }
                    resultSet.close();
                    connection.close();
                    CommentUpdateStatement.close();
                }catch (Exception e){
                    System.out.println(e);
                }
            } else {
                System.out.println("해당 닉네임의 댓글이 없습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}