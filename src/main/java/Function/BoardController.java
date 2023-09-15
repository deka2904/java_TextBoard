package Function;

import java.util.List;

public class BoardController {

    Board_Action board_action;
    Board_Print board_print;

    public BoardController(){
        board_action = new Board_Action();
        board_print = new Board_Print();
    }

    public void detail() {
        Article article = board_action.getArticleById(5);
        board_print.board_print(article);
    }
    public void list() {
        List<Article> articleList = (List<Article>) board_action.getAllArticles();
        board_print.board_print((Article) articleList);
    }
}
