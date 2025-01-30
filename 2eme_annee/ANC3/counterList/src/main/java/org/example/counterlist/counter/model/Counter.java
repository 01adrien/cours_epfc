package org.example.counterlist.counter.model;


import javafx.beans.binding.*;
import javafx.beans.property.*;

import java.util.Random;

public class Counter {
    private final int MAX_VALUE = 3;

    private final IntegerProperty value = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();

    private final BooleanBinding isMin = value.isEqualTo(-MAX_VALUE);
    private final BooleanBinding isMax = value.isEqualTo(MAX_VALUE);

    public Counter(String name) {
        setName(name);
        setValue(new Random().nextInt(-MAX_VALUE, MAX_VALUE + 1));
    }

    public int getValue() {

        return value.getValue();
    }

    private void setValue(int value) {

        this.value.setValue(value);
    }

    public ReadOnlyIntegerProperty valueProperty() {
        return value;
    }

    public void increment() {
        int val = getValue();
        setValue(Math.min(MAX_VALUE, val + 1));
    }

    public void decrement() {
        int val = getValue();
        setValue(Math.max(-MAX_VALUE, val - 1));
    }

    public boolean isMin() {
        return value.getValue() == -MAX_VALUE;
    }

    public boolean isMax() {
        return value.getValue() == MAX_VALUE;
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public boolean isValidName() {
        return getName().trim().length() >= 3;
    }

    public BooleanBinding isValidNameProperty() {
        return Bindings.createBooleanBinding(this::isValidName, name);
    }

    public BooleanBinding isMinProperty() {
        return isMin;
    }

    public BooleanBinding isMaxProperty() {
        return isMax;
    }

}

