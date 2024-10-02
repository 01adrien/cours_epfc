package tgpr.tuto.controller;

import tgpr.framework.Controller;
import tgpr.framework.Error;
import tgpr.framework.ErrorList;
import tgpr.framework.Model;
import tgpr.tuto.Main;
import tgpr.tuto.model.Member;
import tgpr.tuto.model.MemberValidator;
import tgpr.tuto.model.Security;
import tgpr.tuto.view.LoginView;

import java.util.List;

public class LoginController extends Controller<LoginView> {
    public void exit() {
        System.exit(0);
    }

    public List<Error> login(String pseudo, String password) {
        var errors = new ErrorList();
        errors.add(MemberValidator.isValidPseudo(pseudo));
        errors.add(MemberValidator.isValidPassword(password));

        if (errors.isEmpty()) {
            var member = Member.checkCredentials(pseudo, password);
            if (member != null) {
                Security.login(member);
                navigateTo(new MemberListController());
            } else
                showError(new Error("invalid credentials"));
        } else
            showErrors(errors);

        return errors;
    }

    public void seedData() {
        Model.seedData(Main.DATABASE_SCRIPT_FILE);
    }

    @Override
    public LoginView getView() {
        return new LoginView(this);
    }
}