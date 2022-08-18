package com.jpmc.theater;

public class Reservation {
    private Customer customer;
    private Showing showing;
    private int partySize;

    public Reservation(Customer customer, Showing showing, int partySize) {
        this.customer = customer;
        this.showing = showing;
        this.partySize = partySize;
    }

    public Customer getCustomer(){
        return this.customer;
    }        

    public void setCustomer(Customer newCustomer){
         this.customer = newCustomer;
    }        

    public Showing getShowing(){
        return this.showing;
    }  

    public void setShowing(Showing newShowing){
        this.showing = newShowing;
   }     
    
    public int getPartySize(){
        return this.partySize;
    }    

    public void setPartySize(int newSize){
        this.partySize = newSize;
    }     

    public double totalFee() {
        return showing.getMovieFee() * partySize;
    }
}