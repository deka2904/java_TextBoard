package Function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BoardController {

    Board_Action board_action;
    Board_Print board_print;

    public BoardController() {
        board_action = new Board_Action();
        board_print = new Board_Print();
    }

    public Article detail() {
        Article article = board_action.getArticleById(5);
        board_print.board_print(article);
        return article;
    }

    public List<Article> list() {
        List<Article> articleList = board_action.getAllArticles();
        board_print.board_print(articleList);
        return articleList;
    }

    public List<Article> search(String keyword) {
        List<Article> searchedArticleList = board_action.getSearchedArticles(keyword);
        board_print.board_print(searchedArticleList);
        return searchedArticleList;
    }

    public void update(Article article) {
        boolean resultCode = board_action.updateArticle(article);
        System.out.println(resultCode); // true, false
    }
}
