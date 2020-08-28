package models.environment;

public interface Destructible {

    void contactObject(Object obj);

    void breakDown();

    boolean isDestroyed();
}
