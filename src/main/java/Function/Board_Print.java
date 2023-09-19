package Function;


import java.util.List;

public class Board_Print {
    public void board_print(List<Article> articleList){
        System.out.println("=============================================================================");
        for(Article article : articleList){
            int number = article.getNumber();
            String title = article.getTitle();
            String contents = article.getContents();
            String text_board_member_nickname = article.getText_board_member_nickname();
            String time = article.setTime(article.time);
            int view_count = article.setView_count(article.view_count);
            int text_board_suggestion = article.setText_board_suggestion(article.text_board_suggestion);

            // 가져온 결과를 출력
            System.out.println("[게시글 번호] : " + article.setNumber(number));
            System.out.println("[게시글 제목] : " + article.setTitle(title));
            System.out.println("[게시글 내용] : " + article.setContents(contents));
            System.out.println("[작성자] : "+ article.setText_board_member_nickname(text_board_member_nickname));
            System.out.println("[시간] : " + article.setTime(time));
            System.out.println("[조회수] : " + article.setView_count(view_count));
            System.out.println("[추천수] : " + article.setText_board_suggestion(text_board_suggestion));
            System.out.println("=============================================================================");
        }
    }

    public void board_print(Article article){
        int number = article.getNumber();
        String title = article.getTitle();
        String contents = article.getContents();
        String text_board_member_nickname = article.getText_board_member_nickname();
        String time = article.setTime(article.time);
        int view_count = article.setView_count(article.view_count);
        int text_board_suggestion = article.setText_board_suggestion(article.text_board_suggestion);

        // 가져온 결과를 출력
        System.out.println("[게시글 번호] : " + article.setNumber(number));
        System.out.println("[게시글 제목] : " + article.setTitle(title));
        System.out.println("[게시글 내용] : " + article.setContents(contents));
        System.out.println("[작성자] : "+ article.setText_board_member_nickname(text_board_member_nickname));
        System.out.println("[시간] : " + article.setTime(time));
        System.out.println("[조회수] : " + article.setView_count(view_count));
        System.out.println("[추천수] : " + article.setText_board_suggestion(text_board_suggestion));
    }
}
