package util;

public interface saveableFactoryAcceptor {
    public void addFactory(String name , SaveableFactory com);
    public void removeFactory(String name);
    public void init();
}
