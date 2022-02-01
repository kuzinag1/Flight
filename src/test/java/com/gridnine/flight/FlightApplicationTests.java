package com.gridnine.flight;

import com.gridnine.flight.entity.Flight;
import com.gridnine.flight.entity.FlightCondition;
import com.gridnine.flight.entity.utils.Field;
import com.gridnine.flight.entity.utils.Operation;
import com.gridnine.flight.entity.utils.Segment;
import com.gridnine.flight.service.Filter;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FlightApplicationTests {


    private static final Flight FLIGHT = new Flight(List.of(new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(2))));


    @Test
    void getSegmentForFirstConditinalFromTZ() {
        FlightCondition condition = new FlightCondition(LocalDateTime.of(2021, 12, 25, 19, 01), Field.START, Operation.BEFORE);
        List<FlightCondition> flightConditionList = List.of(condition);

        List<Segment> resultFlightList = new Filter().filterSegments(prepareFlights(), flightConditionList);

        List<Segment> listExpected = expectedSegmentsForFirstTest();

        Assertions.assertEquals(listExpected, resultFlightList);
    }

    @Test
    void getSegmentForTwoConditionalFromTZ() {
        FlightCondition condition = new FlightCondition(LocalDateTime.of(2021, 12, 25, 22, 51), Field.START, Operation.AFTER);
        List<FlightCondition> flightConditionList = List.of(condition);

        List<Segment> resultFlightList = new Filter().filterSegments(prepareFlights(), flightConditionList);

        List<Segment> listExpected = expectedForSecondTest();
        Assertions.assertEquals(listExpected, resultFlightList);
    }

    @Test
    void getSegmentsForThirdConditionalFromTZ() {
        List<Flight> prepareSegment = prepareFlights();

        FlightCondition condition = new FlightCondition(LocalDateTime.of(2021, 12, 25, 21, 0).plusHours(2), Field.START, Operation.AFTER);
        List<FlightCondition> flightConditionList = List.of(condition);

        List<Segment> resultFlightList = (new Filter().filterSegments(prepareSegment, flightConditionList));

        List<Segment> expectedList = expectedSegmentsForThirdTest();

        Assert.assertEquals(expectedList, resultFlightList);


    }


    List<Flight> prepareFlights() {
        Segment segment1 = new Segment(LocalDateTime.of(2021, 12, 25, 18, 51), LocalDateTime.of(2021, 12, 25, 20, 0));
        Segment segment2 = new Segment(LocalDateTime.of(2021, 12, 25, 19, 0), LocalDateTime.of(2021, 12, 25, 21, 0));
        Segment segment3 = new Segment(LocalDateTime.of(2021, 12, 25, 19, 50), LocalDateTime.of(2021, 12, 25, 20, 0));

        Segment segment4 = new Segment(LocalDateTime.of(2021, 12, 25, 19, 25), LocalDateTime.of(2021, 12, 25, 21, 25));
        Segment segment5 = new Segment(LocalDateTime.of(2021, 12, 25, 22, 0), LocalDateTime.of(2021, 12, 25, 23, 0));
        Segment segment6 = new Segment(LocalDateTime.of(2021, 12, 26, 0, 50), LocalDateTime.of(2021, 12, 26, 1, 50));


        Flight flight1 = new Flight(List.of(segment1, segment2, segment3));
        Flight moreThanTwoHours = new Flight(List.of(segment4, segment5, segment6));
        Flight flight3 = new Flight(List.of(segment4));
        Flight flight4 = new Flight(List.of(segment5));

        List<Flight> flights = List.of(flight1, moreThanTwoHours, flight3, flight4);
        return flights;
    }

    List<Segment> expectedForSecondTest() {
        List<Segment> expectedList = new ArrayList<>();
        expectedList.add(new Segment(LocalDateTime.of(2021, 12, 26, 0, 50), LocalDateTime.of(2021, 12, 26, 1, 50)));
        return expectedList;
    }

    List<Segment> expectedSegmentsForThirdTest() {
        List<Segment> expectedList = new ArrayList<>();

        expectedList.add(new Segment(LocalDateTime.of(2021, 12, 26, 0, 50), LocalDateTime.of(2021, 12, 26, 1, 50)));

        return expectedList;
    }

    List<Segment> expectedSegmentsForFirstTest() {
        List<Segment> expectedList = new ArrayList<>();

        expectedList.add(new Segment(LocalDateTime.of(2021, 12, 25, 18, 51), LocalDateTime.of(2021, 12, 25, 20, 0)));
        expectedList.add(new Segment(LocalDateTime.of(2021, 12, 25, 19, 0), LocalDateTime.of(2021, 12, 25, 21, 0)));

        return expectedList;
    }
}
