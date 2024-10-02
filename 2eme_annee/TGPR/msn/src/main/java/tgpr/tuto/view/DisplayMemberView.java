package tgpr.tuto.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.TextColor;
import tgpr.tuto.controller.DisplayMemberController;
import tgpr.tuto.model.Member;
import tgpr.tuto.model.Security;

import java.util.List;

import static tgpr.framework.Tools.asString;
import static tgpr.framework.Tools.ifNull;

public class DisplayMemberView extends DialogWindow {

    private final DisplayMemberController controller;
    private Member member;
    private final Label lblPseudo;
    private final Label lblProfile;
    private final Label lblBirthDate;
    private final Label lblRole;

    private final Label lblRelationship;
    private Button btnToggleFollow = null;

    public DisplayMemberView(DisplayMemberController controller, Member member) {
        super("View Member");

        this.controller = controller;
        this.member = member;

        setHints(List.of(Hint.CENTERED));
        setCloseWindowWithEscape(true);

        Panel root = new Panel();
        setComponent(root);

        Panel fields = new Panel().setLayoutManager(new GridLayout(2).setTopMarginSize(1)).addTo(root);

        fields.addComponent(new Label("Pseudo:"));
        lblPseudo = new Label("").addTo(fields).addStyle(SGR.BOLD);

        fields.addComponent(new Label("Profile:"));
        lblProfile = new Label("").addTo(fields).addStyle(SGR.BOLD);

        fields.addComponent(new Label("Birth Date:"));
        lblBirthDate = new Label("").addTo(fields).addStyle(SGR.BOLD);

        fields.addComponent(new Label("Role:"));
        lblRole = new Label("").addTo(fields).addStyle(SGR.BOLD);

        new EmptySpace().addTo(root);

        lblRelationship = new Label("");
        lblRelationship.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        root.addComponent(lblRelationship, LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        new EmptySpace().addTo(root);

        var buttons = new Panel().setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        if (!Security.isLoggedUser(member)) {
            btnToggleFollow = new Button("", this::toggleFollow).addTo(buttons);
        }
        // Le membre connecté ne peut modifier que ses données ou celles des autres s'il est admin
        if (Security.isAdmin() || Security.isLoggedUser(member))
            new Button("Update", this::update).addTo(buttons);
        // Seul un admin peut supprimer un membre
        if (Security.isAdmin())
            new Button("Delete", this::delete).addTo(buttons);
        new Button("Close", this::close).addTo(buttons);
        root.addComponent(buttons, LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        refresh();
    }

    private void refresh() {
        if (member != null) {
            lblPseudo.setText(member.getPseudo());
            lblProfile.setText(ifNull(member.getProfile(), ""));
            lblBirthDate.setText(asString(member.getBirthdate()));
            lblRole.setText(member.isAdmin() ? "Admin" : "Member");
        }
    }

    private void update() {
        member = controller.update();
        refresh();
        var rel = Security.getLoggedUser().getRelationshipType(member);
        lblRelationship.setText(rel.toText());
        if (btnToggleFollow != null)
            btnToggleFollow.setLabel(rel == Member.RelationshipType.Unrelated || rel == Member.RelationshipType.Follower ? "Follow" : "Unfollow");
    }

    private void delete() {
        controller.delete();
    }

    private void toggleFollow() {
        controller.toggleFollow();
        refresh();
    }
}