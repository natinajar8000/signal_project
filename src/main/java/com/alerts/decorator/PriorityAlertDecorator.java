package com.alerts.decorator;

import com.alerts.alert.Alert;

/* This class wraps an existing alert and enhances its condition string
 * by prepending a priority level
*/
public class PriorityAlertDecorator extends AlertDecorator {
    private String priorityLevel;

    /**
     * Constructs a new PriorityAlertDecorator.
     *
     * @param alert         the base to be decorated
     * @param priorityLevel the priority level to be assigned (high/low)
     */
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