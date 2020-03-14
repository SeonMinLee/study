package oop.encapsulation.practice2;

public class Rental {
    private Movie movie;
    private int daysRented;

    public int getFrequentRenterPoints() {
        if (movie.isNewReleaseAndRentedMoreOneDays(daysRented)) {
            return 2;
        } else {
            return 1;
        }
    }

}
