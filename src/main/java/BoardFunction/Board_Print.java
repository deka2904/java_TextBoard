package BoardFunction;


import java.util.List;

public class Board_Print {
    public void board_print(List<Article> articleList){
        System.out.println("=============================================================================");
        for(Article article : articleList){
            int number = article.getNumber();
            String title = article.getTitle();
            String contents = article.getContents();
            String text_board_member_nickname = article.getText_board_member_nickname();
            String time = article.getTime();
            int view_count = article.getView_count();
            int text_board_suggestion = article.getText_board_suggestion();

            // 가져온 결과를 출력
            System.out.println("[게시글 번호] : " + number);
            System.out.println("[게시글 제목] : " + title);
            System.out.println("[게시글 내용] : " + contents);
            System.out.println("[작성자] : "+ text_board_member_nickname);
            System.out.println("[시간] : " + time);
            System.out.println("[조회수] : " + view_count);
            System.out.println("[추천수] : " +text_board_suggestion);
            System.out.println("=============================================================================");
        }
    }

    public void board_print(Article article){
        int number = article.getNumber();
        String title = article.getTitle();
        String contents = article.getContents();
        String text_board_member_nickname = article.getText_board_member_nickname();
        String time = article.getTime();
        int view_count = article.getView_count();
        int text_board_suggestion = article.getText_board_suggestion();

        // 가져온 결과를 출력
        System.out.println("[게시글 번호] : " + number);
        System.out.println("[게시글 제목] : " + title);
        System.out.println("[게시글 내용] : " + contents);
        System.out.println("[작성자] : "+ text_board_member_nickname);
        System.out.println("[시간] : " + time);
        System.out.println("[조회수] : " + view_count);
        System.out.println("[추천수] : " +text_board_suggestion);
    }
}
