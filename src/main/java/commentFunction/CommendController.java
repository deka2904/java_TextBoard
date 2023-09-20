package commentFunction;

import java.util.List;

public class CommendController {
    CommendQueryManager commendQueryManager= new CommendQueryManager();
    CommendPrint commendPrint = new CommendPrint();
    public List<Commend> Commendlist(int number) {
        List<Commend> CommendList = commendQueryManager.getAllCommend(number);
        commendPrint.commendPrint(CommendList);
        return CommendList;
    }
}
