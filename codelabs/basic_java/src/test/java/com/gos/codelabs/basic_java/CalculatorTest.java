package com.gos.codelabs.basic_java;

import org.junit.Test;

public class CalculatorTest {
    @Test
    public void testAddition() {
        Calculator myCalculator = new Calculator();
        double result = myCalculator.add(3, 4);
        assert result == 7;
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
