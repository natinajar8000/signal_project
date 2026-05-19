package com.alerts.decorator;

import com.alerts.alert.Alert;

// A decorator that adds repetition logic 
public class RepeatedAlertDecorator extends AlertDecorator {
    private int checkIntervalSeconds;

    /**
     * Constructs a new RepeatedAlertDecorator.
     *
     * @param alert                the base alert to be decorated
     * @param checkIntervalSeconds the interval in seconds between alert repetitions
     */
    public RepeatedAlertDecorator(Alert alert, int checkIntervalSeconds) {
        super(alert);
        this.checkIntervalSeconds = checkIntervalSeconds;
    }

    public int getCheckIntervalSeconds() {
        return checkIntervalSeconds;
    }

    @Override
    public String getCondition() {
        return super.getCondition() + " [repeated every " + checkIntervalSeconds + "s]";
    }
}