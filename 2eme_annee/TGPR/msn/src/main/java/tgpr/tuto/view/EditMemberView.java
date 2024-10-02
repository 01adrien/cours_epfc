package tgpr.tuto.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.tuto.controller.EditMemberController;
import tgpr.tuto.model.Member;

import java.util.List;
import java.util.regex.Pattern;

import static tgpr.framework.Tools.asString;
import static tgpr.framework.Tools.ifNull;

public class EditMemberView extends DialogWindow {

    private final EditMemberController controller;

    private final TextBox txtPseudo;
    private final TextBox txtProfile;
    private final TextBox txtPassword;
    private final TextBox txtPasswordConfirm;
    private final TextBox txtBirthDate;
    private final ComboBox<String> cboRole;
    private final Label errPseudo;
    private final Label errProfile;
    private final Label errPassword;
    private final Label errPasswordConfirm;
    private final Label errBirthDate;
    private final Label errRole;
    private final Button btnAddUpdate;

    private final Member member;

    public EditMemberView(EditMemberController controller, Member member) {
        // définit le titre de la fenêtre
        super((member == null ? "Add " : "Update ") + "Member");

        this.member = member;
        this.controller = controller;

        setHints(List.of(Hint.CENTERED, Hint.FIXED_SIZE));
        // permet de fermer la fenêtre en pressant la touche Esc
        setCloseWindowWithEscape(true);
        // définit une taille fixe pour la fenêtre de 15 lignes et 70 colonnes
        setFixedSize(new TerminalSize(70, 15));

        Panel root = new Panel();
        root.setLayoutManager(new GridLayout(2).setTopMarginSize(1));

        new Label("Pseudo:").addTo(root);
        txtPseudo = new TextBox(new TerminalSize(11, 1)).addTo(root)
                .setValidationPattern(Pattern.compile("[a-z][a-z\\d]{0,7}"))
                .setTextChangeListener((txt, byUser) -> validate())
                .setReadOnly(member != null);
        new EmptySpace().addTo(root);
        errPseudo = new Label("").addTo(root)
                .setForegroundColor(TextColor.ANSI.RED);

        new Label("Profile:").addTo(root);
        txtProfile = new TextBox(new TerminalSize(21, 1)).addTo(root)
                .setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(root);
        errProfile = new Label("").addTo(root).setForegroundColor(TextColor.ANSI.RED);

        new Label("Password:").addTo(root);
        txtPassword = new TextBox(new TerminalSize(11, 1)).addTo(root)
                .setMask('*')
                .setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(root);
        errPassword = new Label("").addTo(root).setForegroundColor(TextColor.ANSI.RED);

        new Label("Confirm Password:").addTo(root);
        txtPasswordConfirm = new TextBox(new TerminalSize(11, 1)).addTo(root)
                .setMask('*')
                .setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(root);
        errPasswordConfirm = new Label("").addTo(root).setForegroundColor(TextColor.ANSI.RED);

        new Label("Birth Date:").addTo(root);
        txtBirthDate = new TextBox(new TerminalSize(11, 1)).addTo(root)
                .setValidationPattern(Pattern.compile("[/\\d]{0,10}"))
                .setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(root);
        errBirthDate = new Label("").addTo(root).setForegroundColor(TextColor.ANSI.RED);

        new Label("Role:").addTo(root);
        cboRole = new ComboBox<>("Admin", "Member").addTo(root)
                .setPreferredSize(new TerminalSize(11, 1));
        cboRole.setSelectedItem("Member");
        cboRole.addListener((selectedIndex, previousSelection, changedByUserInteraction) -> validate());
        new EmptySpace().addTo(root);
        errRole = new Label("").addTo(root).setForegroundColor(TextColor.ANSI.RED);

        new EmptySpace().addTo(root);
        new EmptySpace().addTo(root);

        var buttons = new Panel().addTo(root).setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        btnAddUpdate = new Button(member == null ? "Add" : "Update", this::add).addTo(buttons).setEnabled(false);
        new Button("Cancel", this::close).addTo(buttons);

        setComponent(root);

        if (member != null) {
            txtPseudo.setText(member.getPseudo());
            txtProfile.setText(ifNull(member.getProfile(), ""));
            txtBirthDate.setText(asString(member.getBirthdate()));
            cboRole.setSelectedItem(member.isAdmin() ? "Admin" : "Member");
        }
    }

    private void add() {
        controller.save(
                txtPseudo.getText(),
                txtProfile.getText(),
                txtPassword.getText(),
                txtPasswordConfirm.getText(),
                txtBirthDate.getText(),
                cboRole.getText()
        );
    }

    private void validate() {
        var errors = controller.validate(
                txtPseudo.getText(),
                txtProfile.getText(),
                txtPassword.getText(),
                txtPasswordConfirm.getText(),
                txtBirthDate.getText(),
                cboRole.getText()
        );
        errPseudo.setText(errors.getFirstErrorMessage(Member.Fields.Pseudo));
        errProfile.setText(errors.getFirstErrorMessage(Member.Fields.Profile));
        errPassword.setText(errors.getFirstErrorMessage(Member.Fields.Password));
        errPasswordConfirm.setText(errors.getFirstErrorMessage(EditMemberController.Fields.PasswordConfirm));
        errBirthDate.setText(errors.getFirstErrorMessage(Member.Fields.BirthDate));
        errRole.setText(errors.getFirstErrorMessage(Member.Fields.Admin));

        btnAddUpdate.setEnabled(errors.isEmpty());
    }
}
