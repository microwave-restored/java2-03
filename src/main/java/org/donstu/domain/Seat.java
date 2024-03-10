package org.donstu.domain;

import java.io.Serializable;

public class Seat implements Serializable {
    private String hall;
    private int row;
    private int seat;

    public Seat() {
    }

    public Seat(String hall, int row, int seat) {
        this.hall = hall;
        this.row = row;
        this.seat = seat;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "hall='" + hall + '\'' +
                ", row=" + row +
                ", seat=" + seat +
                '}';
    }
}
