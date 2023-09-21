package commentFunction;

import BoardFunction.Article;

import java.util.List;

public class CommendPrint {
    public void commendPrint(List<Commend> CommendList) {
        boolean hasValidCommends = false;
        System.out.println("=============================================================================");
        for (Commend commends : CommendList) {
            String commend = commends.getCommend();
            String comment_member_nickname = commends.getComment_member_nickname();
            String time = commends.getTime();

            // commend, comment_member_nickname, time 중 하나라도 null이면 해당 댓글을 출력하지 않음
            if (commend != null && comment_member_nickname != null && time != null) {
                // 가져온 결과를 출력
                System.out.println("[댓글] : " + commend);
                System.out.println("[작성자] : " + comment_member_nickname);
                System.out.println("[시간] : " + time);
                System.out.println("=============================================================================");
                hasValidCommends = true;
            }
        }
        if (!hasValidCommends) {
            System.out.println("해당 게시글에 유효한 댓글이 없습니다.");
        }
    }
}




