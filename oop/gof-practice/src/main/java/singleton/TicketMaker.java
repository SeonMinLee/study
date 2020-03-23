package singleton;

public class TicketMaker {
    private static TicketMaker ticketMaker = new TicketMaker();

    private int ticket;

    private TicketMaker() {
        ticket = 1000;
    }

    public static TicketMaker getInstance() {
        return ticketMaker;
    }

    public int getNextTicketNumber() {
        return ticket++;
    }
}
