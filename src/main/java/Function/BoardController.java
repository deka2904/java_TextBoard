package Function;

import java.util.List;

public class BoardController {

    QueryManager queryManager;
    Board_Print board_print;

    public BoardController() {
        queryManager = new QueryManager();
        board_print = new Board_Print();
    }

    public Article detail(int num) {
        Article article = queryManager.getArticleById(num);
        board_print.board_print(article);
        return article;
    }

    public List<Article> list() {
        List<Article> articleList = queryManager.getAllArticle();
        board_print.board_print(articleList);
        return articleList;
    }

    public List<Article> search(String keyword) {
        List<Article> searchedArticleList = queryManager.getArticle(keyword);
        board_print.board_print(searchedArticleList);
        return searchedArticleList;
    }

//    public void update(Article article) {
//        boolean resultCode = queryManager.updateArticle(article);
//        System.out.println(resultCode); // true, false
//    }
}
