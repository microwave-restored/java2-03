package org.donstu.domain;

import java.io.Serializable;
import java.util.List;

public class Reservation implements Serializable {
    private Movie movie;

    private List<Seat> seats;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
