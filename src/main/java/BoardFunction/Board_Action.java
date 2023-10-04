package BoardFunction;

import SQL.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board_Action{
    Article article = new Article();
    int number = 1;
    private static final int PAGE_SIZE = 3; // 페이지 상수
    Scanner scanner = new Scanner(System.in);
    // list / search
    public List<Article> selectList(String sql) {
        ArrayList<Article> articles = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        boolean foundResults = false; // 결과가 있으면 true로 설정
        try {
            // SQL 쿼리를 사용하여 데이터베이스에서 게시물 목록을 가져옴
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                foundResults = true;
                Article article = new Article();
                article.setNumber(resultSet.getInt("number"));
                article.setTitle(resultSet.getString("title"));
                article.setContents(resultSet.getString("contents"));
                article.setText_board_member_nickname(resultSet.getString("text_board_member_nickname"));
                article.setTime(resultSet.getString("time"));
                article.setView_count(resultSet.getInt("view_count"));
                article.setText_board_suggestion(resultSet.getInt("text_board_suggestion"));

                articles.add(article);
            }
            if (!foundResults) {
                System.out.println("찾을 수 없습니다.");
            }
            // 자원 해제
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            // 발생할 수 있는 SQLException 처리
        }
        return articles;
    }
    // detail
    public Article selectOne(String sql) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                article.setNumber(resultSet.getInt("number"));
                article.setTitle(resultSet.getString("title"));
                article.setContents(resultSet.getString("contents"));
                article.setText_board_member_nickname(resultSet.getString("text_board_member_nickname"));
                article.setTime(resultSet.getString("time"));
                article.setView_count(resultSet.getInt("view_count"));
                article.setText_board_suggestion(resultSet.getInt("text_board_suggestion"));
            }
            resultSet.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // 닫을 때 발생할 수 있는 SQLException 처리
            }
        }
        return article;
    }
    // update / delete
    public int updateArticle(String sql, Object... article) {
        Connection connection = DatabaseConnection.getConnection();
        int affectedRows;
        int i = 0;
        try {
            //반복문 PrepareStatement 완성
            PreparedStatement statement = connection.prepareStatement(sql);
            for(Object str : article){
                if (str instanceof Integer){
                    i++;
                    statement.setInt(i, (Integer)str);
                }else if(str instanceof Boolean){
                    i++;
                    statement.setBoolean(i, (Boolean)str);
                }else{
                    i++;
                    statement.setString(i, (String)str);
                }
            }
            affectedRows = statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return affectedRows;
    }
    public List<Article> ArticleList(String sql){
        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Article> articles = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Article article1 = new Article();
                article1.setNumber(resultSet.getInt("number"));
                article1.setTitle(resultSet.getString("title"));
                article1.setContents(resultSet.getString("contents"));
                article1.setText_board_member_nickname(resultSet.getString("text_board_member_nickname"));
                article1.setTime(resultSet.getString("time"));
                article1.setView_count(resultSet.getInt("view_count"));
                article1.setText_board_suggestion(resultSet.getInt("text_board_suggestion"));
                articles.add(article1);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }
}