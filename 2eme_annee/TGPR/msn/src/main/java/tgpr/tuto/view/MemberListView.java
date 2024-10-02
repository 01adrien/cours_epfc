package tgpr.tuto.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.gui2.menu.MenuBar;
import com.googlecode.lanterna.gui2.menu.MenuItem;
import tgpr.framework.ViewManager;
import tgpr.tuto.controller.MemberListController;
import tgpr.tuto.model.Member;
import tgpr.tuto.model.Security;

import java.util.List;

import static tgpr.framework.Tools.asString;
import static tgpr.framework.Tools.ifNull;
public class MemberListView extends BasicWindow {

    private final MemberListController controller;
    private final ObjectTable<Member> table;
    private final Menu menuFile ;
    private  Button btnAddMember = null;
    public MemberListView(MemberListController controller) {
        this.controller = controller;

        setTitle(getTitleWithUser());
        setHints(List.of(Hint.EXPANDED));

        // Le panel 'root' est le composant racine de la fenêtre (il contiendra tous les autres composants)
        Panel root = new Panel();
        setComponent(root);

        MenuBar menuBar = new MenuBar().addTo(root);
        menuFile = new Menu("File");
        menuBar.add(menuFile);
        MenuItem menuLogout = new MenuItem("Logout", controller::logout);
        menuFile.add(menuLogout);
        MenuItem menuExit = new MenuItem("Exit", controller::exit);
        menuFile.add(menuExit);

        // ajoute une ligne vide
        new EmptySpace().addTo(root);

        // crée un tableau de données pour l'affichage des membres
        table = new ObjectTable<>(
                new ColumnSpec<>("Pseudo", Member::getPseudo),
                new ColumnSpec<>("Profile", m -> ifNull(m.getProfile(), "")),
                new ColumnSpec<Member>("Birth Date", m -> asString(m.getBirthdate())),
                new ColumnSpec<Member>("Role", m -> m.isAdmin() ? "Admin" : "Member"),
                new ColumnSpec<Member>("Relationship", m -> Security.getLoggedUser().getRelationshipType(m))
        );
        // ajoute le tableau au root panel
        root.addComponent(table);
        // spécifie que le tableau doit avoir la même largeur que le terminal et une hauteur de 15 lignes
        table.setPreferredSize(new TerminalSize(ViewManager.getTerminalColumns(), 15));
        // spécifie l'action a exécuter quand on presse Enter ou la barre d'espace
        table.setSelectAction(() -> {
            var member = table.getSelected();
            controller.editMember(member);
            reloadData();
            table.setSelected(member);
        });
        if (Security.isAdmin()) {
            new EmptySpace().addTo(root);

            // crée un bouton pour l'ajout d'un membre et lui associe une fonction lambda qui sera appelée
            // quand on clique sur le bouton
            var btnAddMember = new Button("Add Member", () -> {
                    Member m = controller.addMember();
                    if (m != null)
                        reloadData();
                }).addTo(root);
            } else
            btnAddMember = null;

        // charge les données dans la table
        reloadData();
    }

    public void reloadData() {
        // vide le tableau
        table.clear();
        // demande au contrôleur la liste des membres
        var members = controller.getMembers();
        // ajoute l'ensemble des membres au tableau
        table.add(members);
    }

    private String getTitleWithUser() {
        return "Welcome to MSN (" + Security.getLoggedUser().getPseudo() + " - " + (Security.isAdmin() ? "Admin" : "Member") + ")";
    }

}