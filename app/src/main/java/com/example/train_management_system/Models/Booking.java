package com.example.train_management_system.Models;

public class Booking {

    private String nic;
    private String trainname;
    private String bookingdate;
    private String name;
    private String nooftickets;
    private String reservationdate;
    private String statusString;

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getTrainname() {
        return trainname;
    }

    public void setTrainname(String trainname) {
        this.trainname = trainname;
    }

    public String getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(String bookingdate) {
        this.bookingdate = bookingdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNooftickets() {
        return nooftickets;
    }

    public void setNooftickets(String nooftickets) {
        this.nooftickets = nooftickets;
    }

    public String getReservationdate() {
        return reservationdate;
    }

    public void setReservationdate(String reservationdate) {
        this.reservationdate = reservationdate;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }
}
