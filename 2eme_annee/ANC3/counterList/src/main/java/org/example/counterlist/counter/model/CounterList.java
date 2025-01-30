package org.example.counterlist.counter.model;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CounterList {
    private final ObservableList<CounterInList> counters = FXCollections.observableArrayList();

    public CounterList() {
        counters.addListener((InvalidationListener) obs -> checkDuplicates());

        for (int i = 0; i < 3; ++i)
            addCounter();
    }

    public void checkDuplicates() {
        // brutal, mais simple : on compare chaque compteur avec tous les autres compteurs
        for (var counter : counters) {
            boolean duplicate = false;
            for (var other : counters)
                if (!counter.equals(other) && counter.getName().equals(other.getName())) {
                    duplicate = true;
                    break;
                }
            counter.setDuplicate(duplicate);
        }
    }

    public ObservableList<CounterInList> getCounters() {
        return counters;
    }

    public int getTotal() {
        return counters.stream()
                .mapToInt(Counter::getValue)
                .sum();
    }

    public void addCounter() {
        var counter = new CounterInList("Counter #" + (counters.size() + 1));
        counters.add(counter);
    }

    public void removeCounter() {
        if (counters.isEmpty()) return;
        counters.remove(counters.size() - 1);
    }
}
