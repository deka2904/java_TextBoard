package commentFunction;

import java.util.List;

public class CommendQueryManager {
    Commend_Action commend_action = new Commend_Action();
    // 해당 게시글 닉네임으로 댓글 조회
    public List<Commend> getAllCommend(int number, String nickname) {
        String sql = "SELECT id, comment FROM text_board_comment WHERE board_number = " + number + " AND comment_member_nickname = " + nickname;
        List<Commend> articleList = commend_action.selectOne(sql);

        return articleList;
    }
}
