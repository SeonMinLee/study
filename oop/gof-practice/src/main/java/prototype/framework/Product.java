package prototype.framework;

public interface Product extends Cloneable {
    void use(String s);
    Product clone() throws CloneNotSupportedException;
    Product createClone();

    java.lang.Object
}
