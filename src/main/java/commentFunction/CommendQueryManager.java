package commentFunction;

import java.util.List;

public class CommendQueryManager {
    Commend_Action commend_action = new Commend_Action();
    // 해당 게시글 닉네임으로 댓글 조회
    public List<Commend> getAllCommend(int number) {
        String sql = "SELECT comment, comment_time, comment_member_nickname FROM text_board LEFT JOIN text_board_comment ON text_board.number = text_board_comment.board_number WHERE text_board.number = " + number;
        List<Commend> articleList = commend_action.selectOne(sql);

        return articleList;
    }
}
