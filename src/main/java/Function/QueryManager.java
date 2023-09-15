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
    public List<Article> getAllArticle() {
        String sql = "SELECT * FROM text_board WHERE title LIKE %?%";
        List<Article> articleList = board_action.selectList(sql);

        return articleList;
    }

    // 아이디로 하나 조회
    public Article getArticleById() {
        String sql = "SELECT * FROM text_board WHERE number = ?";
        Article article = board_action.selectOne(sql);

        return article;
    }

    // 수정

    // 삭제

}
