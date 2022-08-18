package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationTests {
    LocalDateProvider provider;
    @Test //tests a reservation based on a sequence
    void totalFeeBasedOnSequence() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                1,
                LocalDateTime.now()
        );

        int partySize = 3;
        double expectedFee = (12.5-3)*partySize; //math to test against       
        double calculatedFee =  new Reservation(customer, showing, partySize).totalFee(); 

        assertTrue(calculatedFee == expectedFee);
    }

    @Test //tests a reservation based on a show time
    void totalFeeBasedOnStartTime() {
        this.provider = LocalDateProvider.singleton();
        var customer = new Customer("John Doe", "unused-id");
        ArrayList<LocalDateTime> spiderManShowtimes = new ArrayList<LocalDateTime>(); 
        spiderManShowtimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0)));
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1, spiderManShowtimes),
                LocalDateTime.now()
        );

        int partySize = 3;
        double expectedFee = (0.8*12.5)*partySize; //math to test against       
        double calculatedFee =  new Reservation(customer, showing, partySize).totalFee(); 

        assertTrue(calculatedFee == expectedFee);
    }
}
