package org.example.anc3.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.StringProperty;
import org.example.anc3.model.Counter;

public class CounterViewModel {

    Counter counter;

    public CounterViewModel(Counter counter) {
        this.counter = counter;
    }

    public BooleanBinding minusDisabled(){
        return  counter.isValidNameProperty().not().or(counter.isMinProperty());
    }

    public BooleanBinding plusDisabled(){
        return  counter.isValidNameProperty().not().or(counter.isMaxProperty());
    }

    public int getValue(){
        return counter.getValue();
    }

    public String getName(){
        return counter.getName();
    }

    public void increment(){
        counter.increment();
    }

    public void decrement(){
        counter.decrement();
    }

    public StringProperty nameProperty(){
        return counter.nameProperty();
    }

    public ReadOnlyIntegerProperty valueProperty(){
        return counter.valueProperty();
    }

    public BooleanBinding isValidNameProperty(){
        return counter.isValidNameProperty();
    }



}
