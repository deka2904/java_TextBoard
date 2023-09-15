package Function;

import java.sql.ResultSet;

public class Article {
   int number;
   String title;
   String contents;
   String text_board_member_nickname;
   String time;
   int view_count;
   int text_board_suggestion;

   public Article() {

   }

   public Article(int number, String title, String contents, String text_board_member_nickname, String time, int view_count, int text_board_suggestion) {
      this.number = number;
      this.title = title;
      this.contents = contents;
      this.text_board_member_nickname = text_board_member_nickname;
      this.time = time;
      this.view_count = view_count;
      this.text_board_suggestion = text_board_suggestion;
   }


   // 게시글 번호
   public int getNumber() {
      return number;
   }
   public int setNumber(int number) {
      this.number = number;
      return number;
   }
   // 게시글 제목
   public String getTitle() {
      return title;
   }
   public String setTitle(String title) {
      this.title = title;
      return title;
   }
   // 게시글 내용
   public String getContents() {
      return contents;
   }
   public String setContents(String contents) {
      this.contents = contents;
      return contents;
   }
   // 작성자
   public String getText_board_member_nickname() {
      return text_board_member_nickname;
   }
   public String setText_board_member_nickname(String text_board_member_nickname) {
      this.text_board_member_nickname = text_board_member_nickname;
      return text_board_member_nickname;
   }
   // 시간
   public String getTime() {
      return time;
   }
   public String setTime(String time) {
      this.time = time;
      return time;
   }
   // 조회수
   public int getView_count() {
      return view_count;
   }
   public int setView_count(int view_count) {
      this.view_count = view_count;
      return view_count;
   }
   // 추천수
   public int getText_board_suggestion() {
      return text_board_suggestion;
   }
   public int setText_board_suggestion(int text_board_suggestion) {
      this.text_board_suggestion = text_board_suggestion;
      return text_board_suggestion;
   }
}