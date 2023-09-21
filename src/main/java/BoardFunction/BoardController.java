package BoardFunction;

import commentFunction.CommendController;

import java.util.List;
import java.util.Scanner;

public class BoardController {
    Article article = new Article();
    Scanner scanner = new Scanner(System.in);
    BoardQueryManager boardQueryManager;
    Board_Print board_print;
    CommendController commendController = new CommendController();
    int number = 1;
    public BoardController() {
        boardQueryManager = new BoardQueryManager();
        board_print = new Board_Print();
    }
    public int add(String nickname){
        System.out.print("게시글 제목 : ");
        String title = scanner.nextLine();
        System.out.print("게시글 내용 : ");
        String content = scanner.nextLine();

        int insertArticle = boardQueryManager.insertArticle(title, content, nickname);
        number++;
        if (insertArticle > 0) {
            System.out.println("게시물이 등록되었습니다.");
        } else {
            System.out.println("게시물 등록에 실패했습니다.");
        }
        return insertArticle;
    }
    public Article detail(String nickname) {
        System.out.print("상세보기 할 게시물 번호를 입력해주세요 : ");
        int num = Integer.parseInt(scanner.nextLine());
        Article article = boardQueryManager.getArticleById(num);
        Outter:
        while (true){
            board_print.board_print(article);
            commendController.Commendlist(num);
            System.out.print("상세보기 기능을 선택해주세요(1. 댓글 등록, 2. 추천, 3. 수정, 4. 삭제, 5. 목록으로) : ");
            int number = Integer.parseInt(scanner.nextLine());
            switch (number){
                case 1:
                    commendController.CommendAdd(num, nickname);
                    break;
                case 2:
                    commendController.ReComment(num, nickname);
                    break;
                case 3:
                    commendController.CommentUodate(nickname);
                    break;
                case 4:
                    commendController.CommentDelete(nickname);
                    break;
                case 5:
                    break Outter;
            }
        }
        return article;
    }
    public List<Article> list() {
        List<Article> articleList = boardQueryManager.getAllArticle();
        board_print.board_print(articleList);
        return articleList;
    }
    public List<Article> search() {
        System.out.print("검색 키워드를 입력해주세요: ");
        String keyword = scanner.nextLine();
        List<Article> searchedArticleList = boardQueryManager.getArticle(keyword);
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

        int updatedArticle = boardQueryManager.updateArticle(newTitle, newContent, num);
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

        int deletedArticle = boardQueryManager.deleteArticle(num);
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
