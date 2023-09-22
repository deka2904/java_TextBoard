package BoardFunction;

import commentFunction.CommendController;
import commentFunction.CommendQueryManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardController {
    Article article = new Article();
    Scanner scanner = new Scanner(System.in);
    BoardQueryManager boardQueryManager;
    CommendQueryManager commendQueryManager;
    Board_Print board_print;
    CommendController commendController = new CommendController();
    int number = 1;
    public BoardController() {
        boardQueryManager = new BoardQueryManager();
        commendQueryManager = new CommendQueryManager();
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
        boardQueryManager.view_count(num);
        Outter:
        while (true){
            Article article = boardQueryManager.getArticleById(num);
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
            System.out.printf("%d번 게시물이 수정 되었습니다.\n", num);
            foundResults = true;
        } else {
            System.out.println("게시물 수정에 실패했습니다.");
        }

        if (!foundResults) {
            System.out.println("해당 번호를 찾을 수 없습니다.");
        }
        return updatedArticle;
    }
    public int delete() {
        System.out.print("삭제 할 게시물 번호 : ");
        int num = Integer.parseInt(scanner.nextLine());

        int deletedArticle = boardQueryManager.deleteArticle(num);
        boolean foundResults = false;

        if (deletedArticle > 0) {
            System.out.printf("%d번 게시물이 삭제 되었습니다.\n", num);
            foundResults = true;
        } else {
            System.out.println("게시물 삭제에 실패했습니다.");
        }
        if (!foundResults) {
            System.out.println("해당 번호를 찾을 수 없습니다.");
        }
        return deletedArticle;
    }
    public void sort(){
        String column = "";
        String sortOrder = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("=============================================================================");
        System.out.print("정렬 대상을 선택해주세요. (1. 번호,  2. 조회수) : ");
        int sort1_num = Integer.parseInt(scanner.nextLine());
        if (sort1_num == 1){
            column = "number"; // 번호를 선택한 경우
        } else if(sort1_num == 2){
            column = "view_count"; // 조회수를 선택한 경우
        }
        System.out.println("=============================================================================");
        System.out.print("정렬 방법을 선택해주세요. (1. 오름차순,  2. 내림차순) : ");
        int sort2_num = Integer.parseInt(scanner.nextLine());
        System.out.println("=============================================================================");
        if (sort2_num == 1){
            sortOrder = "ASC"; // 오름차순을 선택한 경우
        } else if(sort2_num == 2){
            sortOrder = "DESC"; // 내림차순을 선택한 경우
        }
        List<Article> sort = boardQueryManager.sortArticle(column, sortOrder);
        if(sort != null){
            board_print.board_print(sort);
        }else{
            System.out.println("게시글이 없습니다.");
        }
    }
    public void page() {
        final int PAGE_SIZE = 3;
        int pageNumber = 1;
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            int offset = (pageNumber - 1) * PAGE_SIZE;
            List<Article> page = boardQueryManager.pageArticle(PAGE_SIZE, offset);

            if (page.isEmpty()) {
                System.out.println("해당 페이지에 게시글이 없습니다.");
            } else {
                board_print.board_print(page);
            }
            int totalPages = getTotalPages(PAGE_SIZE); // 전체 페이지 수 계산

            // 페이지 번호 출력
            for (int i = 1; i <= totalPages; i++) {
                if (i == pageNumber) {
                    System.out.printf("[%d] ", i);
                } else {
                    System.out.printf("%d ", i);
                }
            }
            System.out.println(">> ");

            boolean innerExit = false;
            while (!innerExit) {
                System.out.print("페이징 명령어를 입력해주세요 (1. 이전, 2. 다음, 3. 선택, 4. 뒤로가기) : ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        if (pageNumber > 1) {
                            pageNumber--;
                            innerExit = true;
                        } else {
                            System.out.println("첫 번째 페이지입니다.");
                        }
                        break;
                    case 2:
                        if (pageNumber < totalPages) {
                            pageNumber++;
                            innerExit = true;
                        } else {
                            System.out.println("마지막 페이지입니다.");
                        }
                        break;
                    case 3:
                        System.out.print("이동할 페이지 번호를 입력하세요: ");
                        int selectedPage = Integer.parseInt(scanner.nextLine());
                        if (selectedPage >= 1 && selectedPage <= totalPages) {
                            pageNumber = selectedPage;
                            innerExit = true;
                        } else {
                            System.out.println("유효하지 않은 페이지 번호입니다.");
                        }
                        break;
                    case 4:
                        exit = true;
                        innerExit = true;
                        System.out.println("페이지 검색 종료합니다.");
                        break;
                    default:
                        System.out.println("유효하지 않은 선택입니다.");
                }
            }
        }
    }
    public int getTotalPages(int pageSize) {
        List<Article> totalArticles = boardQueryManager.getAllArticle();
        int totalArticleCount = totalArticles.size();
        if (totalArticleCount % pageSize == 0) {
            return totalArticleCount / pageSize;
        } else {
            return (totalArticleCount / pageSize) + 1;
        }
    }
}


