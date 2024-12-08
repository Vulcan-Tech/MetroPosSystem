import Controller.Linker;
import Model.ImplementDB;
import Model.ImplementOnlineDB;
import View.*;
public class Main {
    public static void main(String[] args) {
        ImplementDB dbObj = new ImplementDB();
        ImplementOnlineDB onlineDB = new ImplementOnlineDB();
        dbObj.setOnlineDB(onlineDB);
        Linker linkerObj = new Linker(dbObj,onlineDB);
        SuperAdminGUI superAdminGUI = new SuperAdminGUI(linkerObj);
        BranchManagerGUI branchManagerGUI = new BranchManagerGUI(linkerObj);
        CashierGUI cashierGUI = new CashierGUI(linkerObj);
        DataEntryOpGUI dataEntryOpGUI = new DataEntryOpGUI(linkerObj);
        GUI gui = new GUI(linkerObj,superAdminGUI,branchManagerGUI,cashierGUI,dataEntryOpGUI);
        linkerObj.setGUI(gui);
    }
}