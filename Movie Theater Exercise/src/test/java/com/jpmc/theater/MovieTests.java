package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTests {
    //these are generic dates and times that will not satisfy the conditions for any of the discounts
    LocalDate genericDate = LocalDate.of(2000, 1, 1);
    LocalTime genericTime = LocalTime.of(1, 00);

    @Test 
    void noDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 0);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(genericDate, genericTime));
        assertEquals(12.5, spiderMan.calculateTicketPrice(showing));
    }   
     
    @Test 
    void specialMovieDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 1);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(genericDate, genericTime));
        assertEquals(10, spiderMan.calculateTicketPrice(showing));
    }

    @Test 
    void sequence1Discount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 0);
        Showing showing = new Showing(spiderMan, 1, LocalDateTime.of(genericDate, genericTime));
        assertEquals(9.5, spiderMan.calculateTicketPrice(showing));

    }

    @Test 
    void sequence2Discount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 0);
        Showing showing = new Showing(spiderMan, 2, LocalDateTime.of(genericDate, genericTime));
        assertEquals(10.5, spiderMan.calculateTicketPrice(showing));

    }

    //this time satisfies the special time discount
    LocalTime specialTime = LocalTime.of(11, 30);
    @Test 
    void specialTimeDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 0);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(genericDate, specialTime));
        assertEquals(9.38, spiderMan.calculateTicketPrice(showing));
    }

    //this time satisfies the special date discount
    LocalDate specialDate = LocalDate.of(2000, 1, 7);
    @Test 
    void specialDateDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 0);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(specialDate, genericTime));
        assertEquals(11.5, spiderMan.calculateTicketPrice(showing));
    }

    //this is a test for a case that satisfies every special case (except second sequence)
    @Test 
    void highestDiscountOnly() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 1);
        Showing showing = new Showing(spiderMan, 1, LocalDateTime.of(specialDate, specialTime));
        assertEquals(9.38, spiderMan.calculateTicketPrice(showing));
    }

}
