package prototype;

import prototype.anonymous.MessageBox;
import prototype.anonymous.UnderlinePen;
import prototype.framework.Manager;
import prototype.framework.Product;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        Manager manager = new Manager();
        UnderlinePen underlinePen = new UnderlinePen('~');
        MessageBox mBox = new MessageBox('*');
        MessageBox sBox = new MessageBox('/');

        manager.register("string message", underlinePen);
        manager.register("warning box", mBox);
        manager.register("slash box", sBox);

        Product p1 = manager.create("string message");
        p1.use("hello world!");

        Product p2 = manager.create("warning box");
        p2.use("hello world!");

        Product p3 = manager.create("slash box");
        p3.use("hello world!");
    }
}
