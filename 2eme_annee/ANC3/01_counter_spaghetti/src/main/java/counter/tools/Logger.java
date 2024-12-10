package counter.tools;

import counter.CounterChangeType;
import counter.model.CounterModel;

import java.util.Observable;
import java.util.Observer;

public class Logger implements Observer {

    CounterModel model;

    public Logger(CounterModel model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        CounterChangeType type = (CounterChangeType) arg;
        System.out.println(switch (type) {
            case VALUE_CHANGED -> "VALUE CHANGED";
            case NAME_CHANGED -> "NAME CHANGED" ;
        });
        System.out.println("INFO: " + model.toString());
    }
}
