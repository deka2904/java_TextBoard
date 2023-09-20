package BoardFunction;

public interface Action {
    void add(String nickname);
    void page(int pageNumber);
    void sort();
    void sign_in();
    String login();
    void logout();
}
