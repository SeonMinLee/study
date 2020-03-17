package iterator;

import java.util.ArrayList;
import java.util.List;

public class BookShelf implements Aggregate {

    private List<Book> books;
    private boolean last;

    public BookShelf() {
        this.books = new ArrayList<>();
        this.last = false;
    }

    @Override
    public Iterator create() {
        return new BookShelfIterator(this);
    }

    public Book getBookAt(int index) {
        if (index >= getLength() - 1) {
            last = true;
        }
        return books.get(index);
    }

    public void appendBook(Book book) {
        books.add(book);
    }

    public int getLength() {
        return books.size();
    }

    public boolean isLast() {
        return last;
    }

}
