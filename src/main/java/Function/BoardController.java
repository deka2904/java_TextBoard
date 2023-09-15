package Function;

public class BoardController {

    Board_Action board_action;
    Board_Print board_print;

    public Article detail() {

        Article article = board_action.getArticleById(1);
        board_print(article); // 게시물 출력

    }
}
