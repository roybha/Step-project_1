package org.example;

import java.util.List;

public interface DAO <A extends HasID>{
    public List<A> getAll();
    public A getByID(int id);
    public void save(A a);
    public void delete(A a);
    public boolean deleteByID(int id);
    public void saveToFile(String filename);
    public void loadFromFile(String filename);
}
