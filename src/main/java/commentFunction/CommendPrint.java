package commentFunction;

import BoardFunction.Article;

import java.util.List;

public class CommendPrint {
    public void commendPrint(List<Commend> CommendList) {
        boolean hasValidCommends = false;
        System.out.println("=============================================================================");
        for (Commend comments : CommendList) {
            int comment_number = comments.getId();
            String comment = comments.getCommend();
            String comment_member_nickname = comments.getComment_member_nickname();
            String time = comments.getTime();

            if (comment != null && comment_member_nickname != null && time != null) {
                System.out.println("[댓글 번호] : " + comment_number);
                System.out.println("[댓글 내용] : " + comment);
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




