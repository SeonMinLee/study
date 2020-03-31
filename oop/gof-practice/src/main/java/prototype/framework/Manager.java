package prototype.framework;

import java.util.HashMap;

public class Manager {
    private HashMap<String, Product> showcase = new HashMap<>();

    public void register(String name, Product proto) {
        showcase.put(name, proto);
    }

    public Product create(String protoname) throws CloneNotSupportedException {
        Product p = showcase.get(protoname);
        return p.clone();
    }
}
