package factorymethod;

import factorymethod.framework.Factory;
import factorymethod.framework.Product;
import factorymethod.idcard.IdCardFactory;

public class Main {
    public static void main(String[] args) {
        Factory factory = new IdCardFactory();
        Product card1 = factory.create("홍길동");
        Product card2 = factory.create("제이든");
        Product card3 = factory.create("이순신");
        card1.use();
        card2.use();
        card3.use();
    }
}
