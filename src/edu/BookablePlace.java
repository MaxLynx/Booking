package edu;


import java.util.concurrent.Semaphore;

public class BookablePlace {
    private String name;

    private final Semaphore semaphore;

    public BookablePlace(String name){
        this.name = name;
        this.semaphore = new Semaphore(1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBooked() {
        if(semaphore.availablePermits() == 0)
            return true;
        else {
            return false;
        }
    }

    public void setBooked(boolean booked) {
        if(booked)
        try {
            semaphore.acquire();
        }
        catch(InterruptedException ex){
        }
    }
}
