package tgpr.tuto.controller;

import tgpr.framework.Controller;
import tgpr.tuto.model.Member;
import tgpr.tuto.model.Security;
import tgpr.tuto.view.MemberListView;

import java.util.List;

public class MemberListController extends Controller<MemberListView> {

    private List<Member> members;

    @Override
    public MemberListView getView() {
        return new MemberListView(this);
    }

    public void logout() {
        Security.logout();
        navigateTo(new LoginController());
    }

    public void exit() {
        System.exit(0);
    }

    public List<Member> getMembers() {
        return Member.getAll();
    }

    public Member addMember() {
        var controller = new EditMemberController();
        navigateTo(controller);
        return controller.getMember();
    }

    public void editMember(Member member) {
        navigateTo(new DisplayMemberController(member));
    }
}