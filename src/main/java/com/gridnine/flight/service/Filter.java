package com.gridnine.flight.service;

import com.gridnine.flight.entity.Flight;
import com.gridnine.flight.entity.FlightCondition;
import com.gridnine.flight.entity.utils.Field;
import com.gridnine.flight.entity.utils.Operation;
import com.gridnine.flight.entity.utils.Segment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Filter {

    public List<Segment> filterSegments(List<Flight> f, List<FlightCondition> filters) {
        List<Segment> segments = new ArrayList<>();

        for (Flight fl : f) {
            for (Segment sl : fl.getSegments()) {
                segments.add(sl);
            }
        }
        for (FlightCondition filter : filters) {
            segments = filter(segments, filter);
        }
        return segments;
    }

    private List<Segment> filter(List<Segment> segments, FlightCondition transitCondition) {
        return segments.stream()
                .filter(t -> processCondition(t, transitCondition))
                .collect(Collectors.toList());
    }


    private boolean processCondition(Segment segment, FlightCondition transitCondition) {
        LocalDateTime processingField;
        if (transitCondition.getField() == Field.START) {
            processingField = segment.getDeparture();
        } else {
            processingField = segment.getArrival();
        }

        if (transitCondition.getOperation() == Operation.AFTER) {
            return processingField.truncatedTo(ChronoUnit.MINUTES).isAfter(transitCondition.getLocalDateTime().truncatedTo(ChronoUnit.MINUTES));
        } else {
            return processingField.truncatedTo(ChronoUnit.MINUTES).isBefore(transitCondition.getLocalDateTime().truncatedTo(ChronoUnit.MINUTES));
        }

    }
}
