package com.alerts;

public class PriorityAlertDecorator extends AlertDecorator {
    private String priorityLevel;

    public PriorityAlertDecorator(Alert alert, String priorityLevel) {
        super(alert);
        this.priorityLevel = priorityLevel;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    @Override
    public String getCondition() {
        return "[" + priorityLevel.toUpperCase() + "] " + super.getCondition();
    }
}