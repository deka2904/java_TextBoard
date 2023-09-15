package Function;

import java.util.HashMap;

public class Print {
    public void print(HashMap<String, Object> resultHashMap){
        int number = Integer.parseInt(String.valueOf(resultHashMap.get("게시글 번호")));
        String title = String.valueOf(resultHashMap.get("게시글 제목"));
        String text_board_member_nickname = String.valueOf(resultHashMap.get("작성자"));
        String time = String.valueOf(resultHashMap.get("시간"));
        int viewCount = Integer.parseInt(String.valueOf(resultHashMap.get("조회수")));
        int text_board_suggestion = Integer.parseInt(String.valueOf(resultHashMap.get("추천수")));

        // 가져온 결과를 출력
        System.out.println("[게시글 번호] : " + number);
        System.out.println("[게시글 제목] : " + title);
        System.out.println("[작성자] : "+ text_board_member_nickname);
        System.out.println("[시간] : " + time);
        System.out.println("[조회수] : " + viewCount);
        System.out.println("[추천수] : " + text_board_suggestion);
        System.out.println("=============================================================================");
    }
}
