package Function;

import java.util.List;

public class QueryManager {
    Board_Action board_action = new Board_Action();

    // 전체 조회
    public List<Article> getAllArticle() {
        String sql = "SELECT * FROM text_board";
        List<Article> articleList = board_action.selectList(sql);

        return articleList;
    }

    // 검색된 결과 조회
    public List<Article> getArticle(String keyword) {
        String sql = "SELECT * FROM text_board WHERE title LIKE \"%" + keyword + "%\"";
        List<Article> articleList = board_action.selectList(sql);

        return articleList;
    }

    // 아이디로 하나 조회
    public Article getArticleById(int num) {
        String sql = "SELECT * FROM text_board WHERE number = ?";
        Article article = board_action.selectOne(sql, num);

        return article;
    }

    // 수정
//    public Article updateArticle(String[] article, boolean isUpdate) {
    public int updateArticle(String title, String content, int number) {
        String sql = "UPDATE text_board SET title = ?, contents = ?, time = NOW() WHERE number = ?";
        int updatelistArticle = board_action.updateOrDeleteArticle(sql, title, content, number);

        return updatelistArticle;
    }

    // 삭제
//    public Article deleteArticle(String[] article, boolean isUpdate){
    public int deleteArticle(int number) {
        String sql = "DELETE FROM text_board WHERE number = ?";
        int deletelistArticle = board_action.updateOrDeleteArticle(sql, number);
        return deletelistArticle;
    }
}
