package commentFunction;

import SQL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Comment_Recommend {
    public void commentrecommend(int num, String nickname) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String suggestion_member_nickname = nickname;

                String checkSuggestionSQL = "SELECT * FROM text_board_suggestion WHERE board_number = ? AND suggestion_member_nickname = ?";
                PreparedStatement checkSuggestionStatement = connection.prepareStatement(checkSuggestionSQL);
                checkSuggestionStatement.setInt(1, num);
                checkSuggestionStatement.setString(2, suggestion_member_nickname);

                ResultSet checkSuggestionResultSet = checkSuggestionStatement.executeQuery();

                if (!checkSuggestionResultSet.next()) {
                    String text_board_suggestionCountSQL = "INSERT INTO text_board_suggestion (board_number, suggestion_member_nickname, check_suggestion) VALUES (?, ?, ?)";
                    PreparedStatement text_board_suggestionCountStatement = connection.prepareStatement(text_board_suggestionCountSQL);
                    text_board_suggestionCountStatement.setInt(1, num);
                    text_board_suggestionCountStatement.setString(2, suggestion_member_nickname);
                    text_board_suggestionCountStatement.setBoolean(3, true);

                    int insertedRowCount = text_board_suggestionCountStatement.executeUpdate();

                    if (insertedRowCount > 0) {
                        String updateSuggestionCountQuery = "UPDATE text_board SET text_board_suggestion = text_board_suggestion + 1 WHERE number = ?";
                        PreparedStatement updateSuggestionCountStatement = connection.prepareStatement(updateSuggestionCountQuery);
                        updateSuggestionCountStatement.setInt(1, num);
                        updateSuggestionCountStatement.executeUpdate();

                        System.out.println("추천 등록이 완료되었습니다.");
                    } else {
                        System.out.println("추천할 수 없습니다.");
                    }
                } else {
                    System.out.println("이미 추천한 댓글입니다.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
