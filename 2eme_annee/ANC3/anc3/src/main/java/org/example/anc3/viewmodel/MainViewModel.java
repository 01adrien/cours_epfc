package org.example.anc3.viewmodel;

import org.example.anc3.model.Counter;

public class MainViewModel {
    public CounterViewModel counterViewModel ;
    public MainViewModel(Counter counter) {
        counterViewModel = new CounterViewModel(counter);
    }
}
