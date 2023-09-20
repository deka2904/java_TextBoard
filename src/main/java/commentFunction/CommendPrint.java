package commentFunction;

import BoardFunction.Article;

import java.util.List;

public class CommendPrint {
    public void commendPrint(List<Commend> CommendList){
        System.out.println("=============================================================================");
        for(Commend commends : CommendList){
            String commend = commends.getCommend();
            String comment_member_nickname = commends.getComment_member_nickname();
            String time = commends.getTime();

            // 가져온 결과를 출력
            System.out.println("[댓글] : " + commend);
            System.out.println("[작성자] : " + comment_member_nickname);
            System.out.println("[시간] : " +  time);
            System.out.println("=============================================================================");
        }
    }
}
