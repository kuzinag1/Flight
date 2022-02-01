package com.gridnine.flight.entity;

import com.gridnine.flight.entity.utils.Field;
import com.gridnine.flight.entity.utils.Operation;

import java.time.LocalDateTime;

public class FlightCondition {
    LocalDateTime localDateTime;
    Operation operation;
    Field field;

    public FlightCondition(LocalDateTime localDateTime, Field field, Operation operation) {
        this.localDateTime = localDateTime;
        this.operation = operation;
        this.field = field;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
