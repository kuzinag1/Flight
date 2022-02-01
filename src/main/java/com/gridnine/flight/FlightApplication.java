package com.gridnine.flight;

import com.gridnine.flight.entity.FlightCondition;
import com.gridnine.flight.entity.utils.Field;
import com.gridnine.flight.entity.Flight;
import com.gridnine.flight.entity.utils.Operation;
import com.gridnine.flight.entity.utils.Segment;
import com.gridnine.flight.service.Filter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FlightApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FlightApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //create condition(s)
        FlightCondition condition = new FlightCondition(LocalDateTime.now().plusDays(3), Field.START, Operation.BEFORE);
        List<FlightCondition> flightConditionList = List.of(condition);
        //create flight
        List<Flight> listCreatedFlights = createFlights();
        //filter segments of flight
        List<Segment> segments = new Filter().filterSegments(listCreatedFlights, flightConditionList);

        //show segments of flight
        for(Segment segment : segments) {
            System.out.println(segment);
        }

    }


    static List<com.gridnine.flight.entity.Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        return Arrays.asList(
                //A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //A flight departing in the past
                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                //A flight that departs before it arrives
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
                //A flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                //Another flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2), threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
    }

    private static com.gridnine.flight.entity.Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<com.gridnine.flight.entity.utils.Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new com.gridnine.flight.entity.utils.Segment(dates[i], dates[i + 1]));
        }
        return new com.gridnine.flight.entity.Flight(segments);
    }
}




