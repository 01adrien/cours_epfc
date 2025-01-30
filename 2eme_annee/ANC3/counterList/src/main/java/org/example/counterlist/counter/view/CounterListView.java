package org.example.counterlist.counter.view;

import org.example.counterlist.counter.model.CounterInList;
import org.example.counterlist.counter.model.CounterList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class CounterListView extends ListView<CounterView> {
    private final CounterList counterList;
    private final Runnable refreshGlobals;
    private final ObservableList<CounterView> counterViews = FXCollections.observableArrayList();

    public CounterListView(CounterList counterList, Runnable refreshGlobals) {
        this.counterList = counterList;
        this.refreshGlobals = refreshGlobals;

        setSelectionModel(null);
        setFocusModel(null);
        setFocusTraversable(false);
        setItems(counterViews);

        // quand la liste des compteurs du modèle change, il faut reconstruire la liste des CounterView
        counterList.getCounters().addListener((ListChangeListener<CounterInList>) change -> refreshChildren());

        refreshChildren();
    }

    private void refreshChildren() {
        var counters = this.counterList.getCounters();
        counterViews.clear();
        counterViews.addAll(counters.stream()
                .map(c -> new CounterView(c, refreshGlobals, this::checkDuplicates))
                .toList());
    }

    private void checkDuplicates() {
        counterList.checkDuplicates();
        for (var c : counterViews)
            c.refreshDuplicate();
    }
}
