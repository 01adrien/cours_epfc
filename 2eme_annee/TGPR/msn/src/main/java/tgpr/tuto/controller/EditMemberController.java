package tgpr.tuto.controller;

import tgpr.framework.Controller;
import tgpr.framework.ErrorList;
import tgpr.tuto.model.Member;
import tgpr.tuto.model.MemberValidator;
import tgpr.tuto.model.Security;
import tgpr.tuto.view.EditMemberView;

import static tgpr.framework.Tools.*;

public class EditMemberController extends Controller<EditMemberView> {
    private final EditMemberView view;
    public enum Fields {
        PasswordConfirm
    }    private Member member;
    private final boolean isNew;

    public EditMemberController() {
        this(null);
    }

    public EditMemberController(Member member) {
        this.member = member;
        isNew = member == null;
        view = new EditMemberView(this, member);
        }

    @Override
    public EditMemberView getView() {
        return view;
    }

    public Member getMember() {
        return member;
    }

    public void save(String pseudo, String profile, String password, String confirmPassword, String birthDate, String role) {
        var errors = validate(pseudo, profile, password, confirmPassword, birthDate, role);
        if (errors.isEmpty()) {
            var hashedPassword = password.isBlank() ? password : hash(password);
            member = new Member(pseudo, hashedPassword, profile, toDate(birthDate), role.contentEquals("Admin"));
            member.save();
            view.close();
        } else
            showErrors(errors);
    }

    public ErrorList validate(String pseudo, String profile, String password, String confirmPassword, String birthDate, String role) {
        var errors = new ErrorList();

        if (isNew) {
            errors.add(MemberValidator.isValidAvailablePseudo(pseudo));
            errors.add(MemberValidator.isValidPassword(password));
        }

        var isAdmin = role.contentEquals("Admin");
        if (pseudo.equals(Security.getLoggedUser().getPseudo()) && Security.isAdmin() != isAdmin)
            errors.add("you may not change your role", Member.Fields.Admin);

        if (!birthDate.isBlank() && !isValidDate(birthDate))
            errors.add("invalid birth date", Member.Fields.BirthDate);
        if (!password.equals(confirmPassword))
            errors.add("must match password", Fields.PasswordConfirm);

        var hashedPassword = password.isBlank() ? password : hash(password);
        var member = new Member(pseudo, hashedPassword, profile, toDate(birthDate), role.contentEquals("Admin"));
        errors.addAll(MemberValidator.validate(member));

        return errors;
    }
}



