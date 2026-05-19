package com.alerts.decorator;

import com.alerts.alert.Alert;

public class RepeatedAlertDecorator extends AlertDecorator {
    private int checkIntervalSeconds;

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