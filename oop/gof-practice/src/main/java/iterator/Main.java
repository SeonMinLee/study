package iterator;

public class Main {

    public static void main(String[] args) {

        BookShelf bookShelf = new BookShelf();
        Iterator iterator = bookShelf.create();
        bookShelf.appendBook(new Book("Jayden"));
        bookShelf.appendBook(new Book("OOP"));
        bookShelf.appendBook(new Book("Design Pattern"));
        bookShelf.appendBook(new Book("Master"));

        while(iterator.hasNext()) {
            System.out.println(iterator.next().getName());
        }
    }

}
