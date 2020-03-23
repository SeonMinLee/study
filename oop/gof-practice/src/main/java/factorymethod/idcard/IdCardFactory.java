package factorymethod.idcard;

import factorymethod.framework.Factory;
import factorymethod.framework.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IdCardFactory extends Factory {
    public Map<String, String> database = new HashMap<>();

    @Override
    protected synchronized Product createProduct(String owner) {
        return new IdCard(owner, UUID.randomUUID().toString());
    }

    @Override
    protected void registerProduct(Product product) {
        database.put(((IdCard) product).getOwner(), ((IdCard) product).getAuthorizationCode());
    }

    public Map<String, String> getDatabase() {
        return database;
    }
}
