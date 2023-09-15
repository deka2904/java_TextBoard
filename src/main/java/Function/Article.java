package Function;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Article {
    public void board_list(ResultSet resultSet){
        try {
            while (resultSet.next()) {
                int number = resultSet.getInt("number");
                String title = resultSet.getString("title");
                String contents = resultSet.getString("contents");
                String text_board_member_nickname = resultSet.getString("text_board_member_nickname");
                String time = resultSet.getString("time");
                int viewCount = resultSet.getInt("view_count");
                int text_board_suggestion = resultSet.getInt("text_board_suggestion");

                System.out.println("[게시글 번호] : " + number);
                System.out.println("[게시글 제목] : " + title);
                System.out.println("[게시글 내용] : " + contents);
                System.out.println("[작성자] : "+ text_board_member_nickname);
                System.out.println("[시간] : " + time);
                System.out.println("[조회수] : " + viewCount);
                System.out.println("[추천수] : " + text_board_suggestion);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
