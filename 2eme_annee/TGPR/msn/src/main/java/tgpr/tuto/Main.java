package tgpr.tuto;

import tgpr.framework.Controller;
import tgpr.framework.Model;
import tgpr.tuto.controller.LoginController;
import tgpr.tuto.controller.MemberListController;

public class Main {
    public final static String DATABASE_SCRIPT_FILE = "/msn.sql";

    public static void main(String[] args) {
        if (!Model.checkDb(DATABASE_SCRIPT_FILE))
            Controller.abort("Database is not available!");
        else
            Controller.navigateTo(new LoginController());
    }


}