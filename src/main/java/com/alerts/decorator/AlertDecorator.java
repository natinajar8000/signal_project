package com.alerts.decorator;

import com.alerts.alert.Alert;

public abstract class AlertDecorator implements Alert {
    protected Alert decoratedAlert;

    public AlertDecorator(Alert alert) {
        this.decoratedAlert = alert;
    }

    @Override
    public int getPatientId() {
        return decoratedAlert.getPatientId();
    }

    @Override
    public String getCondition() {
        return decoratedAlert.getCondition();
    }

    @Override
    public long getTimestamp() {
        return decoratedAlert.getTimestamp();
    }
}