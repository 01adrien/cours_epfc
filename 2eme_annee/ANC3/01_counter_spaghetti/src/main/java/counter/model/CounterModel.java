package counter.model;

import counter.CounterChangeType;


import java.util.Random;

import javafx.beans.property.*;
;

public class CounterModel {
    private final int MAX_VALUE = 3 ;
    private final StringProperty name = new SimpleStringProperty("") ;
    private final IntegerProperty value = new SimpleIntegerProperty()  ;

    public CounterModel(String name) {
        setName(name);
        setValue(new Random().nextInt(-MAX_VALUE, MAX_VALUE + 1));
    }

    private int getValue() {
        return value.getValue();
    }


    private void setValue(int value) {
        this.value.setValue(value);
    }


    public void increment() {
        if (!isMax()){
            value.set(getValue() + 1);
        }
    }

    public void decrement() {
        if (!isMin()){
            value.set(getValue() - 1);
        }
    }

    public ReadOnlyIntegerProperty valueProperty() {
        return value;
    }

    public ReadOnlyStringProperty nameProperty() {
        return name;
    }

    public boolean isMin() {
        return value.get() == -MAX_VALUE;
    }

    public boolean isMax() {
        return value.get() == MAX_VALUE;
    }


    public void setName(String name) {
        this.name.set(name);
    }

    public boolean isValidName() {
        return name.getName().length() < 3;
    }

    @Override
    public String toString() {
        return name + " " + value;
    }
}
