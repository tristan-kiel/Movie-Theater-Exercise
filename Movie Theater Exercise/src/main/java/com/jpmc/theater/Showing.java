package com.jpmc.theater;

import java.time.LocalDateTime;

public class Showing {
    private Movie movie;
    private LocalDateTime showStartTime;
    public int SequenceOfTheDay; 
    
    //constructor used when determining sequence of the day dynamically based off of the date
    public Showing(Movie movie, LocalDateTime showStartTime) {
        this.movie = movie;
        this.showStartTime = showStartTime;
    }
    //constructor used when specifying sequence of the day manually
    public Showing(Movie movie, int SequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.showStartTime = showStartTime;
        this.SequenceOfTheDay = SequenceOfTheDay;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getStartTime() {
        return showStartTime;
    }

    public double getBasePrice() {
        return movie.baseTicketPrice();
    }

    public double getMovieFee() {
        return movie.calculateTicketPrice(this);
    }

    public int getSequenceOfTheDay() {
        return this.SequenceOfTheDay;
    }

    public void setSequenceOfTheDay(int sequence){
        this.SequenceOfTheDay = sequence;
    }
}
