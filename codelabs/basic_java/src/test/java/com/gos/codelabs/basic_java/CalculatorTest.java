package com.gos.codelabs.basic_java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    @Test
    public void testAddition() {
        Calculator myCalculator = new Calculator();
        double result = myCalculator.add(3, 4);
        assertEquals(7, result);
    }

    @Test
    public void testSubtraction() {
    }

    //TODO add tests for
    //  - Multiplication
    //  - Division
    //  - Square root
    //  - Powers


}
