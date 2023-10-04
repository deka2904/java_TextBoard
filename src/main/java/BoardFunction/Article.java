package BoardFunction;

public class Article {
   int number;
   String title;
   String contents;
   String text_board_member_nickname;
   String time;
   int view_count;
   int text_board_suggestion;
   int page;
   int perPageNum;

   public Article() {

   }

   @Override
   public String toString() {
      return "현재 페이지 [page=" + page + ", perPageNum=" + perPageNum + "]";
   }

   public Article(int number, String title, String contents, String text_board_member_nickname, String time, int view_count, int text_board_suggestion, int page, int perPageNum) {
      this.number = number;
      this.title = title;
      this.contents = contents;
      this.text_board_member_nickname = text_board_member_nickname;
      this.time = time;
      this.view_count = view_count;
      this.text_board_suggestion = text_board_suggestion;
      this.page = page;
      this.perPageNum = perPageNum;
   }
   public int getPage() {
      return page;
   }

   public void setPage(int page) {
      this.page = page;
   }

   public int getPerPageNum() {
      return perPageNum;
   }

   public void setPerPageNum(int perPageNum) {
      this.perPageNum = perPageNum;
   }

   public int getNumber() {
      return number;
   }

   public void setNumber(int number) {
      this.number = number;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getContents() {
      return contents;
   }

   public void setContents(String contents) {
      this.contents = contents;
   }

   public String getText_board_member_nickname() {
      return text_board_member_nickname;
   }

   public void setText_board_member_nickname(String text_board_member_nickname) {
      this.text_board_member_nickname = text_board_member_nickname;
   }

   public String getTime() {
      return time;
   }

   public void setTime(String time) {
      this.time = time;
   }

   public int getView_count() {
      return view_count;
   }

   public void setView_count(int view_count) {
      this.view_count = view_count;
   }

   public int getText_board_suggestion() {
      return text_board_suggestion;
   }

   public void setText_board_suggestion(int text_board_suggestion) {
      this.text_board_suggestion = text_board_suggestion;
   }
}