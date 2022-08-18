package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.Comparator;

public class Theater {
    private List<Showing> schedule;
    
    public Theater(List<Showing> schedule) {    
        this.schedule = schedule;
    }

    public List<Showing> getSchedule(){
        return this.schedule;
    }

    public void setSchedule(List<Showing> newSchedule){
        this.schedule = newSchedule;
    }

    //builds a schedule out of a list of movies
    public static List<Showing> buildSchedule(ArrayList<Movie> movieList){
        List<Showing> showingList = new ArrayList<Showing>();
        //iterate through and add each movie to the list
        for (Movie m : movieList){
            //list of showtimes for this movie
            for (LocalDateTime dt : m.getShowTimes()){
                //an individual showing
                showingList.add(new Showing(m, dt));
            }
        }
        //sort by start time
        showingList.sort(Comparator.comparing(Showing::getStartTime));

        //set sequence of the day var for each item in the sorted list
        for (int i = 0; i < showingList.size(); i++){
            //first item of the list must be first in the sequence (for human readability's sake lets begin iterating at 1)
            if(i == 0){                
                showingList.get(i).setSequenceOfTheDay(1);
            }
            //compare to previous item in list to see if the start times match (we will let items with the same start time have the same sequence number)
            else if (showingList.get(i).getStartTime().compareTo(showingList.get(i-1).getStartTime())==0){
                showingList.get(i).setSequenceOfTheDay(showingList.get(i-1).getSequenceOfTheDay());
            }
            //the sequence should be the position in the list iterated by 1
            else{
                showingList.get(i).setSequenceOfTheDay(i+1);
            }
        }

        return showingList;
    }

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        }
        return new Reservation(customer, showing, howManyTickets);
    }

    //prints human readable output
    public void printSchedulePlainText() {      
        try{  
            System.out.println("===================================================");         
            schedule.forEach(s ->
                System.out.println(s.getSequenceOfTheDay() + 
                ": " + s.getStartTime() + 
                " " + s.getMovie().getTitle() + 
                " " + humanReadableFormat(s.getMovie().getRunningTime()) + 
                " Cost: $" + s.getMovieFee())
            );            
            System.out.println("===================================================");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("No showings were found for the givent theater");            
        }
    }
    
    //prints JSON valid output 
    //to-do: Convert from simple string manipulation to a supported JSON library (there are none native to Java that I'm aware of)
    //to-do: write unit test using JSON library to check to ensure the output for a movie/theater are valid JSON.
    public void printScheduleJSON(LocalDateProvider provider) {
        try{
            String scheduleJSONString = "{\"Date\":"+"\""+ provider.currentDate()+"\",\"Schedule\":[";
            for (Showing s: schedule){ 
                scheduleJSONString = scheduleJSONString + "{" +
                "\"Sequence\":\"" + s.getSequenceOfTheDay() + "\"," +
                "\"Start Time\":\"" + s.getStartTime() + "\"," +
                "\"Name\":\"" + s.getMovie().getTitle() + "\"," +
                "\"Duration\":\"" + humanReadableFormat(s.getMovie().getRunningTime()) + "\"," +
                "\"Cost\":\"" + s.getMovieFee() + "\"" +
                "},";
            }                
            //this will remove the final unnecessary comma character
            scheduleJSONString = scheduleJSONString.substring(0, scheduleJSONString.length()-1);
            scheduleJSONString = scheduleJSONString + "]}"; 
            System.out.println(scheduleJSONString);
        } catch(RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("No showings were found for the givent theater"); 
        }
    }
    
    public String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }    

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }

    public static void main(String[] args) {
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

        //list of showtimes for turningRed
        ArrayList<LocalDateTime> turningRedShowtimes = new ArrayList<LocalDateTime>();
        turningRedShowtimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0)));
        turningRedShowtimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30)));
        turningRedShowtimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30)));
        //create Movie object to hold film details/runtimes
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0, turningRedShowtimes);
        todaysMovies.add(turningRed);

        //list of showtimes for theBatMan
        ArrayList<LocalDateTime> theBatManShowTimes = new ArrayList<LocalDateTime>(); 
        theBatManShowTimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50)));
        theBatManShowTimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50)));
        theBatManShowTimes.add(LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 30)));
        //create Movie object to hold film details/runtimes
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0, theBatManShowTimes);
        todaysMovies.add(theBatMan);

        //build schedule using our movies and list of showtimes
        schedule = buildSchedule(todaysMovies);

        //initialize our theater object with today's schedule
        Theater theater = new Theater(schedule);

        theater.printSchedulePlainText();
        theater.printScheduleJSON(provider);
    }

}
