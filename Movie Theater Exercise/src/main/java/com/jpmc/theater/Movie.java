package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Collections;

public class Movie {
    private static int MOVIE_CODE_SPECIAL = 1;

    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;
    private ArrayList<LocalDateTime> showTimes;

    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode, ArrayList<LocalDateTime> showTimes) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
        this.showTimes = showTimes;
    }
    
    public Movie(String title, Duration runningTime, double baseTicketPrice, int specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = baseTicketPrice;
        this.specialCode = specialCode;
    }
    
    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double baseTicketPrice() {
        return ticketPrice;
    }    
    public ArrayList<LocalDateTime> getShowTimes() {
        return showTimes;
    }

    public double calculateTicketPrice(Showing showing) {
        //since we are dealing with percentages we need to round to two decimal places
        double unroundedPrice = ticketPrice - getDiscount(showing);
        double roundedPrice = (double)Math.round(unroundedPrice*100d)/100d;
        return roundedPrice;
    }

    private double getDiscount(Showing showing) {
        int showSequence = showing.SequenceOfTheDay;
        LocalDateTime startTime = showing.getStartTime();
        int dayOfMonth = startTime.getDayOfMonth();
        int startHour = startTime.getHour();

        ArrayList<Double> discountList = new ArrayList<Double>();

        double specialDiscount = 0;
        if (MOVIE_CODE_SPECIAL == specialCode) {
            specialDiscount = ticketPrice * 0.2;  // 20% discount for special movie            
        }        

        double sequenceDiscount = 0;
        if (showSequence == 1) {
            sequenceDiscount = 3; // $3 discount for 1st show
        } else if (showSequence == 2) {
            sequenceDiscount = 2; // $2 discount for 2nd show            
        } 
        
        double dateDiscount = 0;
        if (dayOfMonth == 7){
            dateDiscount = 1; //anything showing on the 7th gets a 1$ discount            
        } 
        
        double timeDiscount = 0;
        if (startHour >= 11 && startHour < 16){
            timeDiscount = ticketPrice * 0.25; //anything showing between 11am and 4pm gets 25% off            
        } 

        discountList.add(specialDiscount);
        discountList.add(sequenceDiscount);
        discountList.add(dateDiscount);
        discountList.add(timeDiscount);
    
        // biggest discount wins
        return Collections.max(discountList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}