package com.example.train_management_system.Models;

public class TrainDetails {
    private String id;
    private String trainname;
    private String date;
    private String starttime;
    private String endtime;
    private String description;
    private String noofsheet;
    private String ticketprice;
    private String status;

    // Constructors, getters, and setters

    public TrainDetails() {
    }

    public TrainDetails(String id, String trainname, String date, String starttime, String endtime, String description, String noofsheet, String ticketprice, String status) {
        this.id = id;
        this.trainname = trainname;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.description = description;
        this.noofsheet = noofsheet;
        this.ticketprice = ticketprice;
        this.status = status;
    }

    // Getters and Setters for each field

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainname() {
        return trainname;
    }

    public void setTrainname(String trainname) {
        this.trainname = trainname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNoofsheet() {
        return noofsheet;
    }

    public void setNoofsheet(String noofsheet) {
        this.noofsheet = noofsheet;
    }

    public String getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(String ticketprice) {
        this.ticketprice = ticketprice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


