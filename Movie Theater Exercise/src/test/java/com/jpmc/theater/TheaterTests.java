package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TheaterTests {
    @Test
    void totalFeeForCustomer() {              
        //build a basic theater schedule here
        ArrayList<Movie> todaysMovies = new ArrayList<Movie>(); 
        LocalDateProvider provider = new LocalDateProvider();
        List<Showing> schedule;

        //list of showtimes for spiderMan
        ArrayList<LocalDateTime> spiderManShowtimes = new ArrayList<LocalDateTime>(); 
        spiderManShowtimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0)));
        spiderManShowtimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10)));
        spiderManShowtimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10)));       
        //create Movie object to hold film details/runtimes
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1, spiderManShowtimes);
        todaysMovies.add(spiderMan);

        //build schedule using our movies and list of showtimes
        schedule = Theater.buildSchedule(todaysMovies);

        //initialize our theater object with today's schedule
        Theater theater = new Theater(schedule);

        //test 
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 2, 4);
        
        double actualFee = reservation.totalFee(); 
        double expectedFee = 40; //fee to test against
        assertEquals(actualFee, expectedFee);
    }

    //prints a basic schedule in plain text format
    @Test
    void printMovieSchedulePlainText() {   
        //build a basic theater schedule here
        ArrayList<Movie> todaysMovies = new ArrayList<Movie>(); 
        LocalDateProvider provider = new LocalDateProvider();
        List<Showing> schedule;

        //list of showtimes for spiderMan
        ArrayList<LocalDateTime> spiderManShowtimes = new ArrayList<LocalDateTime>(); 
        spiderManShowtimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0)));
        spiderManShowtimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10)));
        spiderManShowtimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10)));       
        //create Movie object to hold film details/runtimes
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1, spiderManShowtimes);
        todaysMovies.add(spiderMan);

        //build schedule using our movies and list of showtimes
        schedule = Theater.buildSchedule(todaysMovies);

        //initialize our theater object with today's schedule
        Theater theater = new Theater(schedule);     
        theater.printSchedulePlainText();
    }

    //prints the schedule in JSON format
    @Test
    void printMovieScheduleJSON() {     
        //build a basic theater schedule here
        ArrayList<Movie> todaysMovies = new ArrayList<Movie>(); 
        LocalDateProvider provider = new LocalDateProvider();
        List<Showing> schedule;

        //list of showtimes for spiderMan
        ArrayList<LocalDateTime> spiderManShowtimes = new ArrayList<LocalDateTime>(); 
        spiderManShowtimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0)));
        spiderManShowtimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10)));
        spiderManShowtimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10)));       
        //create Movie object to hold film details/runtimes
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1, spiderManShowtimes);
        todaysMovies.add(spiderMan);

        //build schedule using our movies and list of showtimes
        schedule = Theater.buildSchedule(todaysMovies);

        //initialize our theater object with today's schedule
        Theater theater = new Theater(schedule);   
        theater.printScheduleJSON(provider);
    }
}
