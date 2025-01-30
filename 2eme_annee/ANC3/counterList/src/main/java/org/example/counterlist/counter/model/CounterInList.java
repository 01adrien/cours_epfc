package org.example.counterlist.counter.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CounterInList extends Counter {
    private final BooleanProperty isDuplicate = new SimpleBooleanProperty(false);

    public CounterInList(String name) {
        super(name);
    }
    public ReadOnlyBooleanProperty isDuplicateProperty() {
        return isDuplicate;
    }


    public void setIsDuplicate(boolean value) {
        isDuplicate.set(value);
    }


    public boolean isDuplicate() {
        return isDuplicate.get();
    }


    public BooleanProperty isValidProperty() {
        // Création d'un binding logique: isValid dépend de isValidName() et de isDuplicate
        return new SimpleBooleanProperty(isValidName() && !isDuplicate.get());
    }


    public boolean isValid() {
        return isValidName() && !isDuplicate.get();
    }
}
