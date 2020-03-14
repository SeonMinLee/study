package oop.encapsulation.practice2;

public class Rental {
    private Movie movie;
    private int daysRented;

    public int getFrequentRenterPoints() {
        return movie.getFrequentedRenterPoints(daysRented);
    }

}
