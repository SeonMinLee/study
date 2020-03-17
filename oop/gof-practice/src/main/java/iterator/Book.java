package iterator;

public class Book {
    private String name;

    public Book(String name) {
        if (name == null || "".equals(name)) {
            throw new IllegalArgumentException("책의 이름은 널값일 수 없습니다.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
