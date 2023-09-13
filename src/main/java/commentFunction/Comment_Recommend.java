package commentFunction;

import SQL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Comment_Recommend {
    public void commentrecommend(int num){
        Connection connection = DatabaseConnection.getConnection();
        if(connection != null){
            try {
                String text_board_suggestionCountSQL = "SELECT text_board_suggestion FROM text_board WHERE number = ?";
                PreparedStatement text_board_suggestionCountStatement = connection.prepareStatement(text_board_suggestionCountSQL);
                text_board_suggestionCountStatement.setInt(1, num);

                ResultSet resultSet = text_board_suggestionCountStatement.executeQuery();
                PreparedStatement update_text_board_suggestion_CountStatement = null;

                if(resultSet.next()){
                    int text_board_suggestionCount = resultSet.getInt("text_board_suggestion");

                    text_board_suggestionCount++;

                    // 조회수 업데이트
                    String updateViewCountQuery = "UPDATE text_board SET text_board_suggestion = ? WHERE number = ?";
                    update_text_board_suggestion_CountStatement = connection.prepareStatement(updateViewCountQuery);
                    update_text_board_suggestion_CountStatement.setInt(1, text_board_suggestionCount);
                    update_text_board_suggestion_CountStatement.setInt(2, num);
                    update_text_board_suggestion_CountStatement.executeUpdate();

                    System.out.println("추천 등록이 완료되었습니다.");
                }else{
                    System.out.println("추천할 수 없습니다.");
                }
            }catch (Exception e){

            }
        }
    }
}
