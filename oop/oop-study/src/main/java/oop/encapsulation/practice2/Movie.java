package oop.encapsulation.practice2;

public class Movie {
    public static int REGULAR = 0;
    public static int NEW_RELEASE = 1;
    private int priceCode;

    public int getPriceCode() {
        return priceCode;
    }

    public boolean isNewReleaseAndRentedMoreOneDays(int daysRented) {
        return getPriceCode() == Movie.NEW_RELEASE && daysRented > 1;
    }
}
