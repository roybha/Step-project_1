package org.example;

import java.io.Serializable;
import java.util.Objects;

public class Passenger implements Serializable,HasID{
    @Override
    public String toString() {
        return "ID = "+id + " | "+firstName+" "+lastName+" ";
    }

    private static final long serialVersionUID = 1L;
    private int id;
    @Override
    public int getID() {
        return id;
    }
    @Override
    public void setID(int id) {
        this.id = id;
    }
    private String firstName;
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    private String lastName;
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Passenger(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return id == passenger.id &&
                Objects.equals(firstName, passenger.firstName) &&
                Objects.equals(lastName, passenger.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
