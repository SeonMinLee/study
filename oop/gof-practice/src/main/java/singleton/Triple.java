package singleton;

public class Triple {
    private static Triple[] triple = new Triple[]{
            new Triple(0),
            new Triple(1),
            new Triple(2)
    };
    private final int id;

    public Triple(int id) {
        this.id = id;
    }

    public static Triple getInstance(int id) {
        return triple[id];
    }

    public int getId() {
        return id;
    }
}
