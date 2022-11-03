package Heizoeltaenke.test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;

import Heizoeltaenke.main.*;

class HeizöltankTests {
    Heizöltank tank;
    double testNum;
    
    @BeforeEach
    void setUp() {
        tank = new Heizöltank("one", 1, 100);
        testNum = 10;
    }

    @RepeatedTest(5)
    @Order(1)
    void testInputDeliveryMethod() throws InexistantItemException, InvalidDeliveryException {
        tank.Anlieferung(testNum);
        for (int i = 0; i < 1000; i++) {
            System.out.println("d");
        }
        assertEquals(testNum, tank.getFüllstand());
    }

    @RepeatedTest(5)
    @Order(2)
    void testOutputDeliveryMethod() throws InexistantItemException, InvalidDeliveryException {
        tank.Auslieferung(testNum);
        assertEquals(testNum - testNum, tank.getFüllstand());
    }
}