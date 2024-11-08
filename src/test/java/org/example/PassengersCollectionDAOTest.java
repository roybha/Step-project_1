package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PassengersCollectionDAOTest {

    private PassengersCollectionDAO dao;
    private List<Passenger> passengerList;

    @BeforeEach
    void setUp() {
        passengerList = new ArrayList<>();
        dao = new PassengersCollectionDAO(passengerList);
    }

    @Test
    void testGetAll() {
        Passenger p1 = new Passenger(1, "Сергій","Трохимчук");
        Passenger p2 = new Passenger(2, "Ілля","Мовчан");
        dao.save(p1);
        dao.save(p2);

        List<Passenger> result = dao.getAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(p1));
        assertTrue(result.contains(p2));
    }

    @Test
    void testGetByID_Valid() {
        Passenger p1 = new Passenger(1, "Сергій","Трохимчук");
        dao.save(p1);

        Passenger result = dao.getByID(1);

        assertNotNull(result);
        assertEquals(p1, result);
    }

    @Test
    void testGetByID_Invalid() {
        Passenger result = dao.getByID(0);

        assertNull(result);
    }

    @Test
    void testSave_NewPassenger() {
        Passenger p1 = new Passenger(1, "Сергій","Трохимчук");

        dao.save(p1);

        assertEquals(1, dao.getAll().size());
        assertTrue(dao.getAll().contains(p1));
    }

    @Test
    void testSave_ExistingPassenger() {
        Passenger p1 = new Passenger(1, "Сергій","Трохимчук");
        dao.save(p1);

        p1.setFirstName(" Сергій ");
        dao.save(p1);

        assertEquals(1, dao.getAll().size());
        assertEquals(" Сергій ", dao.getByID(1).getFirstName());
    }

    @Test
    void testDelete() {
        Passenger p1 = new Passenger(1, "Сергій","Олійник");
        dao.save(p1);

        dao.delete(p1);

        assertEquals(0, dao.getAll().size());
    }

    @Test
    void testDeleteByID_Valid() {
        Passenger p1 = new Passenger(1, "Сергій","Олійник");
        dao.save(p1);

        boolean result = dao.deleteByID(1);

        assertTrue(result);
        assertEquals(0, dao.getAll().size());
    }

    @Test
    void testDeleteByID_Invalid() {
        boolean result = dao.deleteByID(0);

        assertFalse(result);
    }

    @Test
    void testAddPassengers() {
        Passenger p1 = new Passenger(1, "Сергій","Олійник");
        Passenger p2 = new Passenger(2, "Сергій","Олійник");
        List<Passenger> newPassengers = new ArrayList<>();
        newPassengers.add(p1);
        newPassengers.add(p2);

        dao.addPassengers(newPassengers);

        assertEquals(2, dao.getAll().size());
        assertTrue(dao.getAll().contains(p1));
        assertTrue(dao.getAll().contains(p2));
    }

    @Test
    void testRemovePassengers() {
        Passenger p1 = new Passenger(1, "Сергій","Олійник");
        Passenger p2 = new Passenger(2, "Сергій","Олійник");
        dao.save(p1);
        dao.save(p2);

        List<Passenger> toRemove = new ArrayList<>();
        toRemove.add(p1);

        dao.removePassengers(toRemove);

        assertEquals(1, dao.getAll().size());
        assertFalse(dao.getAll().contains(p1));
        assertTrue(dao.getAll().contains(p2));
    }
}