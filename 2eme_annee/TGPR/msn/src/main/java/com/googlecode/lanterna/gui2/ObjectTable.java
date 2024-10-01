package com.googlecode.lanterna.gui2;

import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * <p>Ce composant présente le même comportement que le composant {@code Table<T>} de <b>lanterna</b>, dont il hérite.
 * Il permet cependant de présenter de manière tabulaire des objets de type {@code T}.</p>
 *
 * <p>Chaque ligne correspond à un objet et les colonnes correspondent à des données de l'objet courant récupérées à
 * partir de fonctions anonymes.</p>
 *
 * <p>Lors de l'instanciation du composant, on doit lui passer une liste de {@link ColumnSpec} qui vont permettre de
 * définir les caractéristiques des différentes colonnes : titre, largeur, référence vers une méthode permettant de
 * récupérer la valeur, ...</p>
 *
 * <p>En interne, le composant utilise un {@code Table<String>}. Cela signifie que chaque cellule affiche le contenu
 * d'un {@code String} dont la valeur est récupérée en appelant la méthode référencée dans la spécification de la
 * colonne correspondante et en lui passant l'objet courant en paramètre.</p>
 *
 * <p>Voici un exemple d'utilisation :</p>
 * <pre>{@code
 * var tbl = ObjectTable<Person>(
 *     new ColumnSpec<>("Id", p -> p.getId()).format("%04d"),
 *     new ColumnSpec<>("Name", Person::getName).setWidth(20)
 * );
 * ...
 * tbl.clear();
 * tbl.add(new Person(1, "John"));
 * tbl.add(new Person(2, "Tom"));
 * }</pre>
 *
 * @param <T> le type d'objets que l'on veut représenter dans ce tableau
 */
public class ObjectTable<T> extends Table<String> {

    private final ColumnSpec<T>[] columnSpecs;
    private final List<T> objects = new ArrayList<>();
    private Consumer<KeyStroke> keyStrokeHandler;

    /**
     * Le constructeur permet de spécifier les caractéristiques des différentes colonnes à afficher.
     *
     * @param columns la liste des spécifications des colonnes
     */
    @SafeVarargs
    public ObjectTable(ColumnSpec<T>... columns) {
        super(Arrays.stream(columns).map(ColumnSpec::formattedHeader).toArray(String[]::new));
        columnSpecs = columns;
    }

    public ObjectTable<T> setKeyStrokeHandler(Consumer<KeyStroke> handler) {
        keyStrokeHandler = handler;
        return this;
    }

    @Override
    public Result handleKeyStroke(KeyStroke keyStroke) {
        if (keyStrokeHandler != null)
            keyStrokeHandler.accept(keyStroke);
        int row = getSelectedRow();
        var res = super.handleKeyStroke(keyStroke);
        if (listener != null && getSelectedRow() != row) {
            setDirty();
            listener.onSelectionChanged(row, getSelectedRow(), true);
        }
        return res;
    }

    public interface SelectionChangeListener {
        void onSelectionChanged(int oldRow, int newRow, boolean byUser);
    }

    protected SelectionChangeListener listener;

    public synchronized ObjectTable<T> addSelectionChangeListener(SelectionChangeListener listener) {
        this.listener = listener;
        return this;
    }

    private boolean busy = false;

    @Override
    public synchronized Table<String> setSelectedRow(int selectedRow) {
        int row = getSelectedRow();
        var res = super.setSelectedRow(selectedRow);
        if (!busy && listener != null && getSelectedRow() != row) {
            busy = true;
            listener.onSelectionChanged(row, getSelectedRow(), false);
            busy = false;
            setDirty();
        }
        return res;
    }

    /**
     * Permet de supprimer tout le contenu de la table. Il est nécessaire d'appeler cette méthode plutôt que celle
     * du {@code TableModel<T>} car le composant garde en interne une liste des objets affichés et cette liste doit
     * être vidée également.
     */
    public void clear() {
        getTableModel().clear();
        objects.clear();
    }

    private List<String> getValues(T obj) {
        var values = new ArrayList<String>();
        for (ColumnSpec<T> spec : columnSpecs) {
            var value = spec.computeValue(String.format(spec.getFormat(), spec.getGetter().apply(obj)));
            values.add(value);
        }
        return values;
    }

    /**
     * Permet d'ajouter un objet à afficher dans la table sous la forme d'une nouvelle ligne.
     *
     * @param obj l'objet à ajouter
     */
    public void add(T obj) {
        objects.add(obj);
        getTableModel().addRow(getValues(obj));
    }

    /**
     * Permet d'ajouter une liste d'objets à afficher dans la table.
     *
     * @param objects la liste d'objets à ajouter
     */
    public void add(List<T> objects) {
        for (var obj : objects)
            add(obj);
    }

    /**
     * Permet d'ajouter une liste explicite d'objets à afficher dans la table.
     *
     * @param objects la liste d'objets à ajouter
     */
    @SafeVarargs
    public final void add(T... objects) {
        for (var obj : objects)
            add(obj);
    }

    /**
     * Permet d'insérer objet à une position (ligne) donnée dans la table.
     *
     * @param index la position (ligne) d'insertion (indicée à partir de zéro)
     * @param obj   l'objet à insérer
     */
    public void insert(int index, T obj) {
        objects.add(index, obj);
        getTableModel().insertRow(index, getValues(obj));
    }

    /**
     * Permet de récupèrer l'objet couramment sélectionné.
     *
     * @return l'objet en question
     */
    public T getSelected() {
        int row = getSelectedRow();
        return row >= 0 && row < objects.size() ? objects.get(row) : null;
    }

    /**
     * Permet de sélectionner la ligne correspondant à un objet donné.
     *
     * @param obj l'objet en question
     */
    public void setSelected(T obj) {
        int row = objects.indexOf(obj);
        if (row >= 0)
            setSelectedRow(row);
    }

    /**
     * Permet de récupérer l'élément stocké à la ligne donnée.
     *
     * @param row l'indice de la ligne
     * @return l'élément stocké à cette ligne
     */
    public T getItem(int row) {
        if (row < 0 || row >= objects.size()) return null;
        return objects.get(row);
    }

    /**
     * Permet de changer l'élément stocké à la ligne donnée.
     *
     * @param row l'indice de la ligne
     * @param obj l'élément à stocker
     */
    public void setItem(int row, T obj) {
        if (row < 0 || row >= objects.size()) return;
        objects.set(row, obj);
    }

    public void setItems(List<T> items) {
        clear();
        add(items);
    }

    /**
     * Permet de récupérer la liste des objets stockés dans la table sous la forme d'un stream.
     * @return le stream des objets
     */
    public Stream<T> stream() {
        return objects.stream();
    }

    /**
     * Permet d'ajouter le composant à un {@link Panel} existant.
     *
     * @param panel le {@link Panel} dans lequel on veut ajouter la table
     * @return l'instance courante du composant
     */
    public ObjectTable<T> addTo(Panel panel) {
        super.addTo(panel);
        return this;
    }

    /**
     * Permet de définir l'action à exécuter quand on actionne la ligne courante (via "Enter" ou la barre d'espace).
     *
     * @param selectAction l'action à exécuter
     * @return l'instance courante du composant
     */
    public ObjectTable<T> setSelectAction(Runnable selectAction) {
        super.setSelectAction(selectAction);
        return this;
    }

    /**
     * Force le rafraîchissement. Ceci est nécessaire quand les données des objets ont changé.
     */
    public void refresh() {
        objects.forEach(o -> {
            var values = getValues(o);
            var index = objects.indexOf(o);
            for (int i = 0; i < values.size(); ++i)
                getTableModel().setCell(i, index, values.get(i));
        });
        super.invalidate();
    }

    public ObjectTable<T> center() {
        super.center();
        return this;
    }

    public ObjectTable<T> begin() {
        super.begin();
        return this;
    }

    public ObjectTable<T> end() {
        super.end();
        return this;
    }

    public ObjectTable<T> fill() {
        super.fill();
        return this;
    }

    public ObjectTable<T> grow(boolean grow) {
        super.grow(grow);
        return this;
    }

    public ObjectTable<T> grow() {
        super.grow();
        return this;
    }

    public ObjectTable<T> topLeft() {
        super.topLeft();
        return this;
    }

    public ObjectTable<T> topCenter() {
        super.topCenter();
        return this;
    }

    public ObjectTable<T> topRight() {
        super.topRight();
        return this;
    }

    public ObjectTable<T> centerLeft() {
        super.centerLeft();
        return this;
    }

    public ObjectTable<T> centerCenter() {
        super.centerCenter();
        return this;
    }

    public ObjectTable<T> centerRight() {
        super.centerRight();
        return this;
    }

    public ObjectTable<T> bottomLeft() {
        super.bottomLeft();
        return this;
    }

    public ObjectTable<T> bottomCenter() {
        super.bottomCenter();
        return this;
    }

    public ObjectTable<T> bottomRight() {
        super.bottomRight();
        return this;
    }

    public ObjectTable<T> sizeTo(int columns) {
        super.sizeTo(columns);
        return this;
    }

    public ObjectTable<T> sizeTo(int columns, int rows) {
        super.sizeTo(columns, rows);
        return this;
    }

    public ObjectTable<T> centerFill() {
        super.centerFill();
        return this;
    }

    public ObjectTable<T> topFill() {
        super.topFill();
        return this;
    }

    public ObjectTable<T> bottomFill() {
        super.bottomFill();
        return this;
    }

    public ObjectTable<T> fillLeft() {
        super.fillLeft();
        return this;
    }

    public ObjectTable<T> fillCenter() {
        super.fillCenter();
        return this;
    }

    public ObjectTable<T> fillRight() {
        super.fillRight();
        return this;
    }

    public ObjectTable<T> fillFill() {
        super.fillFill();
        return this;
    }

    public ObjectTable<T> colSpan(int span) {
        super.colSpan(span);
        return this;
    }

    public ObjectTable<T> rowSpan(int span) {
        super.rowSpan(span);
        return this;
    }

    public ObjectTable<T> span(int colSpan, int rowSpan) {
        super.span(colSpan, rowSpan);
        return this;
    }

    public ObjectTable<T> hGrow(boolean grow) {
        super.hGrow(grow);
        return this;
    }

    public ObjectTable<T> hGrow() {
        super.hGrow();
        return this;
    }

    public ObjectTable<T> vGrow(boolean grow) {
        super.vGrow(grow);
        return this;
    }

    public ObjectTable<T> vGrow() {
        super.vGrow();
        return this;
    }

    public ObjectTable<T> grow(boolean hGrow, boolean vGrow) {
        super.grow(hGrow, vGrow);
        return this;
    }

    public ObjectTable<T> top() {
        super.top();
        return this;
    }

    public ObjectTable<T> bottom() {
        super.bottom();
        return this;
    }

    public ObjectTable<T> left() {
        super.left();
        return this;
    }

    public ObjectTable<T> right() {
        super.right();
        return this;
    }

    public ObjectTable<T> middle() {
        super.middle();
        return this;
    }

    public List<T> getItems() {
        return objects;
    }

    public void remove(T selected) {
        int index = objects.indexOf(selected);
        if (index >= 0) {
            objects.remove(index);
            getTableModel().removeRow(index);
        }
    }
}
