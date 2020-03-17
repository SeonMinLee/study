package iterator;

public class BookShelfIterator implements Iterator {

    private BookShelf bookShelf;
    private int index;

    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return !bookShelf.isLast();
    }

    @Override
    public Book next() {
        Book bookAt = bookShelf.getBookAt(index++);
        return bookAt;
    }
}
