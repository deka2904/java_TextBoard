package Function;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class BoardController {
    Scanner scanner = new Scanner(System.in);
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

    public int update() {
        System.out.print("수정 할 게시물 번호 : ");
        int num = Integer.parseInt(scanner.nextLine());

        System.out.print("수정할 게시글 제목 : ");
        String newTitle = scanner.nextLine();
        System.out.print("수정할 게시글 내용 : ");
        String newContent = scanner.nextLine();

        int updatedArticle = queryManager.updateArticle(num, newTitle, newContent);
        boolean foundResults = false;

        if (updatedArticle > 0) {
            System.out.printf("%d번 게시물이 수정/삭제 되었습니다.\n", num);
            foundResults = true;
        } else {
            System.out.println("게시물 수정/삭제에 실패했습니다.");
        }

        if (!foundResults) {
            System.out.println("해당 번호를 찾을 수 없습니다.");
        }
        return updatedArticle;
    }

    public int delete() {
        System.out.print("수정 할 게시물 번호 : ");
        int num = Integer.parseInt(scanner.nextLine());

        int deletedArticle = queryManager.deleteArticle(num);
        boolean foundResults = false;

        if (deletedArticle > 0) {
            System.out.printf("%d번 게시물이 수정/삭제 되었습니다.\n", num);
            foundResults = true;
        } else {
            System.out.println("게시물 수정/삭제에 실패했습니다.");
        }
        if (!foundResults) {
            System.out.println("해당 번호를 찾을 수 없습니다.");
        }
        return deletedArticle;
    }
}
