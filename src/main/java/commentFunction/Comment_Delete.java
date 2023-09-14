package commentFunction;

import SQL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Comment_Delete {
    Connection connection = DatabaseConnection.getConnection();
    Scanner scanner = new Scanner(System.in);
    public void commentdelete(int num, String nickname){
        try {
            String comment_member_nickname = nickname;

            String id_comment_Select_Query = "SELECT id, comment FROM text_board_comment WHERE board_number = ? AND comment_member_nickname = ?";
            PreparedStatement id_comment_Select_Statement = connection.prepareStatement(id_comment_Select_Query);
            id_comment_Select_Statement.setInt(1, num);
            id_comment_Select_Statement.setString(2, comment_member_nickname);

            ResultSet resultSet = id_comment_Select_Statement.executeQuery();

            if (resultSet.next()) {
                do {
                    // 게시글 내용 출력
                    int id = resultSet.getInt("id");
                    String comment = resultSet.getString("comment");

                    // 가져온 결과를 출력
                    System.out.println("[댓글 번호] : " + id);
                    System.out.println("[댓글 내용] : " + comment);

                } while (resultSet.next());

                try{
                    System.out.print("삭제할 댓글 번호를 입력하세요 : ");
                    int comment_number = Integer.parseInt(scanner.nextLine());

                    String DeletecommentQuery = "DELETE FROM text_board_comment WHERE id = ?";
                    PreparedStatement deletecommentStatement = connection.prepareStatement(DeletecommentQuery);
                    deletecommentStatement.setInt(1, comment_number);

                    int deletedRows = deletecommentStatement.executeUpdate();
                    if(deletedRows > 0){
                        System.out.printf("%d번 댓글이 삭제되었습니다.\n", comment_number);
                    }else {
                    System.out.println("댓글 삭제에 실패했습니다. 해당 번호를 찾을 수 없습니다.");
                     }
                    deletecommentStatement.close();
                    connection.close();
                    resultSet.close();
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }catch(Exception e){
            System.out.println("올바른 번호를 입력해 주세요.");
        }
    }
}
