package tgpr.tuto.controller;

import tgpr.framework.Controller;
import tgpr.tuto.model.Member;
import tgpr.tuto.model.Security;
import tgpr.tuto.view.DisplayMemberView;

public class DisplayMemberController extends Controller<DisplayMemberView> {
    private final DisplayMemberView view;
    private final Member member;

    public DisplayMemberController(Member member) {
        this.member = member;
        view = new DisplayMemberView(this, member);
    }

    public void delete() {
        if (Security.isLoggedUser(member))
            showError("You may not delete yourself!");
        else if (askConfirmation("You are about to delete this member. Please confirm.", "Delete member")) {
            member.delete();
            view.close();
        }
    }

    public Member update() {
        var controller = new EditMemberController(member);
        navigateTo(controller);
        return controller.getMember();
    }

    public void toggleFollow() {
        Security.getLoggedUser().toggleFollowUnfollow(member);
    }

    @Override
    public DisplayMemberView getView() {
        return view;
    }
}