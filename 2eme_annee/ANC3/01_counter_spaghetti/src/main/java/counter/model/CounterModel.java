package counter.model;

import counter.CounterChangeType;

import java.util.Observable;
import java.util.Random;

 ;

public class CounterModel extends Observable {
    private final int MAX_VALUE ;
    private String name ;
    private int value  ;

    public CounterModel(String name) {
        this.MAX_VALUE = 3;
        this.name = name;
        this.value = new Random().nextInt(-MAX_VALUE, MAX_VALUE + 1);
    }

    public int getValue() {
        return value;
    }

    private void changed (CounterChangeType type) {
        setChanged();
        notifyObservers(type);
    }

    public void increment() {
        if (!isMax()){
            ++value;
            changed(CounterChangeType.VALUE_CHANGED);
        }
    }

    public void decrement() {
        if (!isMin()){
            --value;
            changed(CounterChangeType.VALUE_CHANGED);
        }
    }

    public boolean isMin() {
        return value == -MAX_VALUE;
    }

    public boolean isMax() {
        return value == MAX_VALUE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        changed(CounterChangeType.NAME_CHANGED);
    }

    public boolean isValidName() {
        return name.length() < 3;
    }

    @Override
    public String toString() {
        return name + " " + value;
    }
}
